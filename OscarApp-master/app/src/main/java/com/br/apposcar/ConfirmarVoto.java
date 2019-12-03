package com.br.apposcar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConfirmarVoto extends AppCompatActivity implements Response.Listener,Response.ErrorListener, View.OnClickListener {
public static final String REQUEST_TAG = "ConfirmarVoto";
private RequestQueue mQueue;
private Intent intent;
private ProgressDialog pDialog;
private Candidato filme;
private Diretor diretor;
private Usuario usuario, nome;
private TextView tvNomeFilme,tvGeneroFilme, tvNomeDiretor,tvGeneroDiretor;
private ImageView fotoFilme;
private Button btnAlterarVoto, btnConfirmarVoto;
private EditText edToken;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_voto_teste);
        tvNomeFilme = (TextView) findViewById(R.id.textViewNomeFilme);
        tvNomeDiretor = (TextView) findViewById(R.id.textViewNomeDiretor);
        tvGeneroFilme = (TextView) findViewById(R.id.textViewGeneroFilme);
        edToken = (EditText)findViewById(R.id.token);
        fotoFilme = (ImageView) findViewById(R.id.imageViewFilme);
        btnAlterarVoto = (Button) findViewById(R.id.btnAlterarVoto);
        btnConfirmarVoto = (Button) findViewById(R.id.btnConfirmarVoto);
        btnConfirmarVoto.setOnClickListener(this);

        intent = getIntent();
        if (intent != null) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
        usuario = (Usuario) bundle.getSerializable("usuario");
        filme = (Candidato) bundle.getSerializable("filme");
        diretor = (Diretor) bundle.getSerializable("diretor");
        }
        }

        tvNomeFilme.setText(filme.getNome());
        tvNomeDiretor.setText(diretor.getNome());
        tvGeneroFilme.setText(filme.getGenero());
        //tvGeneroDiretor.setText(diretor.getNome());

        try {
        fotoFilme.setImageBitmap(new Auxiliar().baixarImagem(filme.getLinkFoto()));
        } catch (IOException e) {
        e.printStackTrace();
        }

        btnAlterarVoto.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        intent = new Intent(ConfirmarVoto.this,DashboardActivity.class);
        intent.putExtra("usuario",usuario);
        intent.putExtra("filme",filme);
        intent.putExtra("diretor",diretor);
        startActivity(intent);
        }
        });
        }


private void confirmaVoto(){
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
        .getRequestQueue();

final Response.Listener<JSONObject> list=this;
final Response.ErrorListener errorListener =this;



        if (usuario.getVotou()!=1){

        String url = getString(R.string.server_path) + "ConfirmaVoto?nome=" + usuario.getNome() +   "&filme=" + filme.getNome() + "&diretor=" + diretor.getNome() + "&token=" + edToken.getText().toString() ;

final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST, url, new JSONObject(),
        list, errorListener);
        jsonRequest.setTag(REQUEST_TAG);

        mQueue.add(jsonRequest);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Confirmação em progresso...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        }else{
        Toast.makeText(ConfirmarVoto.this,"Você já votou! Ação não permitida...",Toast.LENGTH_SHORT).show();
        }
        }

@Override
public void onClick(View v){
        confirmaVoto();
        }

@Override
public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }

@Override
public void onResponse(Object response) {
        String responseWs = ("Resposta: " + response);
        System.out.println(responseWs);
        pDialog.dismiss();
        try{
        if ( (((JSONObject) response).getString("message")).equals("Voto confirmado")){
        Toast.makeText(this,"Voto confirmado com sucesso...",Toast.LENGTH_LONG).show();
        //     intent = new Intent(ConfirmarVoto.this,FinalActivity.class);
        startActivity(intent);
        }else if((((JSONObject) response).getString("message")).equals("Usuario ja votou")){
        Toast.makeText(this,"Voto já realizado.",Toast.LENGTH_LONG).show();
        }else if((((JSONObject) response).getString("message")).equals("Token incorreto")){
        Toast.makeText(this,"Token incorreto. Tente novamente.",Toast.LENGTH_LONG).show();
        }else
        Toast.makeText(this,"Falha ao registrar voto.",Toast.LENGTH_LONG).show();
        } catch (JSONException e){
        e.printStackTrace();
        }
        }

@Override
protected void onStart() {
        super.onStart();
        }

@Override
protected void onStop(){
        super.onStop();
        if (mQueue != null){
        mQueue.cancelAll(REQUEST_TAG);
        }
        }
        }
