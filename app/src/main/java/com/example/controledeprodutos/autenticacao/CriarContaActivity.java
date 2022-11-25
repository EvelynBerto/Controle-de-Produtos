package com.example.controledeprodutos.autenticacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.NetworkStatsManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.activity.MainActivity;
import com.example.controledeprodutos.helper.FirebaseHelper;
import com.example.controledeprodutos.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CriarContaActivity extends AppCompatActivity {

    private EditText edit_nome, edit_email, edit_senha;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        iniciaComponentes();
        configCliques();

    }

    private void configCliques() {
        findViewById(R.id.voltar).setOnClickListener(view -> finish());
    }

    public void validaDados(View view) {
        String nome = edit_nome.getText().toString().trim();
        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString().trim();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {

                    progressBar.setVisibility(View.VISIBLE);

                    User user = new User();
                    user.setNome(nome);
                    user.setEmail(email);
                    user.setSenha(senha);

                    salvarCadastro(user);

                } else {
                    edit_senha.requestFocus();
                    edit_senha.setError("Informe sua senha");
                }
            } else {
                edit_email.requestFocus();
                edit_email.setError("Informe seu e-mail");
            }
        } else {
            edit_nome.requestFocus();
            edit_nome.setError("Informe seu nome");
        }
    }

    private void salvarCadastro(User user) {
//métodos herdados da classe do Firebase
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                user.getEmail(), user.getSenha()
        ).addOnCompleteListener(task -> {
            //depois que a operação for completada
            if (task.isSuccessful()) {
                String id = task.getResult().getUser().getUid();
                //task é o resultado das informações no firebase e com isso ele gera um id próprio
                //do firebase, unico de cada usuário
                user.setId(id);
                finish();
                //quando fizer cadastro ou login, temos que fechar a tela atual e levar até a tela principal
                startActivity(new Intent(this, MainActivity.class));
            }
        });

    }

    private void iniciaComponentes() {
        edit_nome = findViewById(R.id.et_nome);
        edit_email = findViewById(R.id.et_email);
        edit_senha = findViewById(R.id.et_senha);
        progressBar = findViewById(R.id.pb);

        TextView text_titulo = findViewById(R.id.tv_titulo);
        text_titulo.setText("Criar conta");
    }

}