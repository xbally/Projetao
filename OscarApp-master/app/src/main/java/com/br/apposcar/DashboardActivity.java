package com.br.apposcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
    private DbConnector db = new DbConnector(this);
    private Usuario usuario;
    private Intent intent;
    private Candidato filme;
    private Diretor diretor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView tvToken = (TextView) findViewById(R.id.token);

        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {

                usuario = (Usuario) bundle.getSerializable("usuario");
                if (bundle.getSerializable("filme") != null)
                    filme = (Candidato) bundle.getSerializable("filme");
                if (bundle.getSerializable("diretor") != null)
                    diretor = (Diretor) bundle.getSerializable("diretor");

                if (usuario !=null){
                    tvToken.setText("TOKEN: "+ usuario.getToken());
                }

                if (diretor != null)
                    Log.v("log--------- ",diretor.getNome());
                if (filme != null)
                    Log.v("log--------- ",filme.getNome());
            }
        }
    }

    public void ListFilmes(View view){
        intent = new Intent(DashboardActivity.this, VotarFilmes.class);
        intent.putExtra("usuario",usuario);
        intent.putExtra("filme",filme);
        intent.putExtra("diretor", diretor);
        startActivity(intent);

    }

    public void ListDiretor(View view){
        intent = new Intent(DashboardActivity.this, VotarDiretor.class);
        intent.putExtra("usuario",usuario);
        intent.putExtra("diretor",diretor);
        intent.putExtra("filme", filme);
        startActivity(intent);

    }

    public void confirmaVoto(View view){
        intent = new Intent(DashboardActivity.this, ConfirmarVoto.class);
        intent.putExtra("usuario",usuario);
        intent.putExtra("diretor",diretor);
        intent.putExtra("filme", filme);
        startActivity(intent);
    }

    public void Sair(View view){
        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("SAIR", true);
        startActivity(intent);
    }
}
