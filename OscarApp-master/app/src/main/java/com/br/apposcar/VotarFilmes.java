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

public class VotarFilmes extends AppCompatActivity {
    private ProgressDialog pDialog;
    private String  tipoVotacao;
    private static String url;
    private ListView lv;
    private ArrayList<Candidato> listaCandidato = new ArrayList<>();
    private CandidatoAdapter candidatoAdapter;
    private Candidato candidato, filme;
    private Diretor diretor;
    private Usuario usuario;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votar_filmes);
        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            diretor = (Diretor) bundle.getSerializable("diretor");
            if (bundle != null) {
                usuario = (Usuario) bundle.getSerializable("usuario");
                if (bundle.getSerializable("filme") != null)
                    filme = (Candidato) bundle.getSerializable("filme");
                tipoVotacao = "filme";
            }
        }

        url = "https://dl.dropboxusercontent.com/s/luags6sv8uxdoj1/filme.json";
        lv = (ListView) findViewById(R.id.list);

        new GetTeams().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                candidato = (Candidato) parent.getItemAtPosition(position);
                intent = new Intent(VotarFilmes.this, DetalhesCandidato.class);
                filme = (Candidato) parent.getItemAtPosition(position);

                intent.putExtra("objetoCandidato", candidato);
                intent.putExtra("filme", filme);
                intent.putExtra("diretor", diretor);
                intent.putExtra("usuario", usuario);
                intent.putExtra("tipoVotacao",tipoVotacao);
                startActivity(intent);
            }
        });
    }

    public class GetTeams extends AsyncTask<Void, Void, Bitmap> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    pDialog = new ProgressDialog(VotarFilmes.this);
                    pDialog.setMessage("Aguarde...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }

                @Override
                protected Bitmap doInBackground(Void... voids) {
                    HttpHandler sh = new HttpHandler();

                    String jsonStr = sh.makeServiceCall(url);
                    Bitmap imagemBitmap = null;

                    if (jsonStr != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            JSONArray candidatoArray = jsonObj.getJSONArray(tipoVotacao);

                            for (int i = 0; i < candidatoArray.length(); i++) {
                                JSONObject c = candidatoArray.getJSONObject(i);


                                listaCandidato.add(new Candidato( c.getInt("id"),c.getString("nome"), c.getString("genero"),
                                        c.getString("foto")));
                            }
                        } catch (final JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Json parsing error " + e.getMessage(),
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Couldn't get json from server.",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                    return imagemBitmap;
                }

                @Override
                protected void onPostExecute(Bitmap aVoid) {
                    super.onPostExecute(aVoid);

                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    candidatoAdapter = new CandidatoAdapter(listaCandidato, VotarFilmes.this);
                    lv.setAdapter(candidatoAdapter);
                }
            }
       }

