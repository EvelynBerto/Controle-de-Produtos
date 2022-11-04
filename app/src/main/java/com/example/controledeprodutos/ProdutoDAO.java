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

    public void salvarProduto(Produto produto) {
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
        /*Método para recuperar os produtos do banco de dados, o retorno dele é uma lista de produtos
        dentro criei a lista e instanciei, passando uma string sql e meu comando para recuperar as infos
        meu cursor c que percorre todo meu banco de dados passando o read que é o objeto de leitura
        não temos argumentos(condição) então ele recupera todos
         */
        List<Produto> produtoList = new ArrayList<>();

        String sql = " SELECT * FROM " + DBHelper.TB_PRODUTO + ";";
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()){
        /*no while percorro todo o banco de dados com o c.moveToNext até que nao tenha mais registros
        e enquanto isso recupero essas informações em variáveis pegando pelo nome da coluna criando
        e declarando um produto novo e guardando as infos recuperadas*/
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nome = c.getString(c.getColumnIndex("nome"));
            @SuppressLint("Range") int estoque = c.getInt(c.getColumnIndex("estoque"));
            @SuppressLint("Range") double valor = c.getDouble(c.getColumnIndex("valor"));

            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome(nome);
            produto.setEstoque(estoque);
            produto.setValor(valor);


            produtoList.add(produto);
            //adiciono meu produto na lista
        }

        return produtoList;
        //retorno minha lista
    }

    public void atualizaProduto(Produto produto){
        ContentValues cv = new ContentValues();
        cv.put("nome", produto.getNome());
        cv.put("estoque", produto.getEstoque());
        cv.put("valor", produto.getValor());

        //vamos alterar usando o id do produto
        String where = "id=?";
        String[] args = {String.valueOf(produto.getId())};

        try {
            //metodo update atualiza as infos no banco de dados
            write.update(DBHelper.TB_PRODUTO, cv, where, args);
            //write.close();
        }catch (Exception e){
            Log.i("Error", "Erro ao atualizar produto " + e.getMessage());
        }
    }

}


