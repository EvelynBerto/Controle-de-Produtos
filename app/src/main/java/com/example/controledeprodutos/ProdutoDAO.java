package com.example.controledeprodutos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    /*DAO - PADRÃO USADO PARA A CLASSE QUE FAZ A PERSISTENCIA NO BANCO DE DADOS, SEPARANDO AS REGRAS
    DE NEGOCIO DO APP, TAMBÉM AS REGRAS DE ACESSO DO BANCO DE DADOS*/
    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public ProdutoDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.write = dbHelper.getWritableDatabase();
        this.read = dbHelper.getReadableDatabase();
    }

    public void SalvarProduto(Produto produto) {
        ContentValues contentValues = new ContentValues();
        /*ContentValues classe de persistencia de dados no banco de dados onde podemos mapear chaves e valor das
        informações que estamos salvando*/
        contentValues.put("nome", produto.getNome());
        //no metodo put coloco o nome da minha tabela onde estara o dado que preciso pegar e dou um get
        contentValues.put("estoque", produto.getEstoque());
        contentValues.put("valor", produto.getValor());

        try {
            write.insert(DBHelper.TB_PRODUTO, null, contentValues);
            //write.close();
            /*quando utilizamos o objeto write ele abre uma conexão com o banco de dados precisamos
            fechar essa conexão do banco de dados*/
        } catch (Exception e) {
            Log.i("ERROR", "Erro ao salvar produto: " + e.getMessage());
        }
    }

    public List<Produto> getListProdutos() {

        List<Produto> produtoList = new ArrayList<>();

        String sql = " SELECT * FROM " + DBHelper.TB_PRODUTO + ";";
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()){

            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nome = c.getString(c.getColumnIndex("nome"));
            @SuppressLint("Range") int estoque = c.getInt(c.getColumnIndex("estoque"));
            @SuppressLint("Range") double valor = c.getDouble(c.getColumnIndex("valor"));

            Produto produto = new Produto();
            produto.setId(id);
        }

        return produtoList;
    }

}


