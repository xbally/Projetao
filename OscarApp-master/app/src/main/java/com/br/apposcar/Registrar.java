package com.br.apposcar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class Registrar extends AppCompatActivity {
    public static final String REQUEST_TAG = "Registrar";
    private RequestQueue mQueue;
    private EditText edtName;
    private EditText edtSenha;
    private EditText edtConfiSenha;
    String nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        edtName = (EditText) findViewById(R.id.name);
        edtSenha= (EditText) findViewById(R.id.senha);
        edtConfiSenha= (EditText) findViewById(R.id.Confirsenha);


    }


    public void Registrar(View v){
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "http://192.168.15.5:8083//OscarAppServer/CadastraCliente";
        String name = edtName.getText().toString();
        String senha= edtSenha.getText().toString();
        String confirmaSenha = edtConfiSenha.getText().toString();
        if ( name == null || name.equals("") ) {
            Toast.makeText(this, "Por favor, preencha o nome", Toast.LENGTH_LONG).show();
        } else if ( senha.equals("") || confirmaSenha.equals("") ) {
            Toast.makeText(this, "Por favor, preenche os campos", Toast.LENGTH_LONG).show();
        } else  {
            try {
                JSONObject postparams = new JSONObject();
                postparams.put("name", name);
                postparams.put("senha", senha);
                final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                        .POST, url,
                        postparams, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String insert = ((JSONObject) response).getString("insert");
                            String message = ((JSONObject) response).getString("message");

                            if ( insert == null || insert.equals("false") ) {
                                AlertDialog alertDialog = new AlertDialog.Builder(Registrar.this).create();
                                alertDialog.setTitle("Erro ao inserir");
                                alertDialog.setMessage(message);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(Registrar.this).create();
                                alertDialog.setTitle("Sucesso ao inserir");
                                alertDialog.setMessage(message);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                alertDialog.show();
                            }

                        } catch ( JSONException e) {
                            AlertDialog alertDialog = new AlertDialog.Builder(Registrar.this).create();
                            alertDialog.setTitle("Erro transformar JSON");
                            alertDialog.setMessage(e.getMessage());
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog alertDialog = new AlertDialog.Builder(Registrar.this).create();
                        alertDialog.setTitle("Erro da requisição");
                        alertDialog.setMessage(error.getMessage());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });
                jsonRequest.setTag(REQUEST_TAG);
                mQueue.add(jsonRequest);
            } catch ( JSONException e ) {
                AlertDialog alertDialog = new AlertDialog.Builder(Registrar.this).create();
                alertDialog.setTitle("Erro do JSON");
                alertDialog.setMessage(e.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }
}
