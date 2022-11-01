package com.example.controledeprodutos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FormProdutoActivity extends AppCompatActivity {

    private EditText edit_produto;
    private EditText edit_quantidade;
    private EditText edit_valor;
    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produto);

        edit_produto = findViewById(R.id.ed_produto);
        edit_quantidade = findViewById(R.id.ed_quantidade);
        edit_valor = findViewById(R.id.ed_valor);
        produtoDAO = new ProdutoDAO(this);
    }

    public void salvarProduto(View view) {
        String nome = edit_produto.getText().toString();
        String quantidade = edit_quantidade.getText().toString();
        String valor = edit_valor.getText().toString();

        if (!nome.isEmpty()) {
            if (!quantidade.isEmpty()) {
                int qtd = Integer.parseInt(quantidade);
                if (qtd >= 1) {
                    if (!valor.isEmpty()) {
                        double valorProduto = Double.parseDouble(valor);
                        if (valorProduto > 0) {
                            Produto produto = new Produto();
                            produto.setNome(nome);
                            produto.setEstoque(qtd);
                            produto.setValor(valorProduto);
                            produtoDAO.SalvarProduto(produto);
                            Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            edit_valor.requestFocus();
                            edit_valor.setError("Informe um valor maior que 0.");
                        }
                    } else {
                        edit_valor.requestFocus();
                        edit_valor.setError("Informe o valor");
                    }
                } else {
                    edit_quantidade.requestFocus();
                    edit_quantidade.setError("Informe um valor maior que 0.");
                }
            } else {
                edit_quantidade.requestFocus();
                edit_quantidade.setError("Informe a quantidade.");
            }
        } else {
            edit_produto.requestFocus();
            edit_produto.setError("Informe o nome do produto.");
        }
    }
}