package com.example.controledeprodutos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //CLASSE PARA CONFIGURAÇÃO DO SQL - BANCO DE DADOS
    private static final String NOME_DB = "DB_APP";
    //CONSTANTE COM O NOME DO BANCO DE DADOS
    static final String TB_PRODUTO = "TB_PRODUTO";
    //CONSTANTE COM O NOME DA TABELA
    private static final int VERSAO = 1;
    //CONSTANTE COM A VERSAO DO APP QUE POR PADRÃO SE INICIA COM 1

    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Executado sempre que nosso app criar o banco de dados
        String sql = "CREATE TABLE IF NOT EXISTS " + TB_PRODUTO
                +"(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL, " +
                " estoque INTEGER NOT NULL, " +
                " valor DOUBLE NOT NULL);";
        try {
            sqLiteDatabase.execSQL(sql);
        }catch (Exception e){
            Log.i("ERROR", "Erro ao criar a tabela: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*será executado sempre que a versão ser atualizada e as tabelas serem atualizadas para nao
        ser necessária a exlusão das tabelas e somente atualização*/

    }
}
