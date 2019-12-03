package com.br.apposcar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity  extends AppCompatActivity implements Response.Listener,Response.ErrorListener, View.OnClickListener {
    public static final String REQUEST_TAG = "UserAutentication";
    private EditText login;
    private EditText password;
    private Button lButton;
    private Usuario usu;
    private Intent intent;
    private ProgressDialog pDialog;
    private RequestQueue mQueue;
    String usuarioTxt,senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.senha);
        lButton = (Button) findViewById(R.id.bLogin);
    }

    public void login() {
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        final Response.Listener<JSONObject> list = this;
        final Response.ErrorListener errorListener = this;

        usuarioTxt = login.getText().toString();
        senha = password.getText().toString();
        if (usuarioTxt.isEmpty()) {
            Toast.makeText(this, "Usuário em branco!", Toast.LENGTH_LONG).show();
        } else if (senha.isEmpty()) {
            Toast.makeText(this, "Senha em branco!", Toast.LENGTH_LONG).show();
        } else {
            String url = getString(R.string.server_path) + "UserValidator?login=" + usuarioTxt + "&senha=" + senha;

            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST, url, new JSONObject(), list, errorListener);
            jsonRequest.setTag(REQUEST_TAG);

            mQueue.add(jsonRequest);
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Login em progresso...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }
    }

    public void Registrar(View view) {
        Intent intent = new Intent(getApplicationContext(), Registrar.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        login();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        pDialog.dismiss();
    }

    @Override
    public void onResponse(Object response) {
        String responseWs = ("Resposta: " + response);
        System.out.println(responseWs);
        pDialog.dismiss();
        JSONObject responseJson = (JSONObject) response;
        try{
            if ( (((JSONObject) response).getString("message")).equalsIgnoreCase("Login correto")){
                intent = new Intent(MainActivity.this,DashboardActivity.class);
                usu = new Usuario(responseJson.getString("nome"), responseJson.getString("senha"), responseJson.getString("filme")
                        , responseJson.getString("diretor"), responseJson.getInt("usuario")
                        , responseJson.getInt("votou"), responseJson.getInt("token"));
                intent.putExtra("usuario",usu);
                startActivity(intent);
            }else
                Toast.makeText(this,"Login incorreto",Toast.LENGTH_LONG).show();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mQueue != null ) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }


}
