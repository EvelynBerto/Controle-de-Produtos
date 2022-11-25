package com.example.controledeprodutos.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.controledeprodutos.R;

public class LoginActivity extends AppCompatActivity {
//Launch Activity = assim que meu app for iniciado, inicia nessa activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}