package com.br.apposcar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VotarDiretor extends AppCompatActivity {
    private ProgressDialog pDialog;
    private String tipoVotacao;
    private static String url;
    private ListView lv;
    private ArrayList<Diretor> listaDiretor = new ArrayList<>();
    private DiretorAdapter diretorAdapter;
    private Diretor diretor;
    private Candidato filme;
    private Usuario usuario;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votar_diretor);

        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            filme = (Candidato) bundle.getSerializable("filme");
            if (bundle != null) {
                usuario = (Usuario) bundle.getSerializable("usuario");
                if (bundle.getSerializable("diretor") != null)
                    diretor = (Diretor) bundle.getSerializable("diretor");
                tipoVotacao = "diretor";
            }
            url = "https://dl.dropboxusercontent.com/s/4scnaqzioi3ivxc/diretor.json";
            lv = (ListView) findViewById(R.id.list);

            new GetTeams().execute();


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    diretor = (Diretor) parent.getItemAtPosition(position);
                    intent = new Intent(VotarDiretor.this, DetalhesDiretor.class);

                    intent.putExtra("objetoDiretor", diretor);
                    intent.putExtra("filme", filme);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("tipoVotacao", tipoVotacao);
                    startActivity(intent);
                }
            });

        }
    }
            public class GetTeams extends AsyncTask<Void, Void, Bitmap> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //showing progress dialog
                    pDialog = new ProgressDialog(VotarDiretor.this);
                    pDialog.setMessage("Aguarde...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }

                @Override
                protected Bitmap doInBackground(Void... params) {
                    HttpHandler sh = new HttpHandler();

                    String jsonStr = sh.makeServiceCall(url);
                    Bitmap imagemBitmap = null;

                    if (jsonStr != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            JSONArray diretorArray = jsonObj.getJSONArray(tipoVotacao);

                            for (int i = 0; i < diretorArray.length(); i++) {
                                JSONObject c = diretorArray.getJSONObject(i);


                                listaDiretor.add(new Diretor(c.getString("nome"), c.getInt("id")));

                            }
                        } catch (final JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Couldn't get json from server.",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    return imagemBitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    diretorAdapter = new DiretorAdapter(listaDiretor, VotarDiretor.this);
                    lv.setAdapter(diretorAdapter);
                }
            }
        }

