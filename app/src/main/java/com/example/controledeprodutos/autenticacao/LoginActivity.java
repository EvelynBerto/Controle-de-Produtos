package com.example.controledeprodutos.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.activity.MainActivity;
import com.example.controledeprodutos.helper.FirebaseHelper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {
//Launch Activity = assim que meu app for iniciado, inicia nessa activity

    private TextView text_criar_conta;
    private EditText edit_email, edit_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciaComponentes();
        configCliques();
        //logar();
    }

    public void logar(View view){
//aqui confiro se as informações foram digitadas
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        if (!email.isEmpty()){
            if (!senha.isEmpty()){
                validaLogin(email, senha);
            } else {
                edit_senha.requestFocus();
                edit_senha.setError("Informe sua senha");
            }
        } else {
            edit_email.requestFocus();
            edit_email.setError("Informe seu email");
        }

    }

    private void validaLogin(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                //fazer login usando email e senha
                email, senha
        ).addOnCompleteListener(task -> {
            //esse método nos notifica quando essa ação for concluída
            if (task.isSuccessful()) {
                //se cair aqui é porque deu tudo certo
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configCliques(){
        text_criar_conta.setOnClickListener(
                view -> startActivity(new Intent(this, CriarContaActivity.class))
        );
    }//evento de clique 'Criar conta' para a activity onde estão os métodos para tratar

    private void iniciaComponentes(){
        text_criar_conta = findViewById(R.id.tv_criar_conta);
        edit_email = findViewById(R.id.et_email);
        edit_senha = findViewById(R.id.et_senha);
    }

}