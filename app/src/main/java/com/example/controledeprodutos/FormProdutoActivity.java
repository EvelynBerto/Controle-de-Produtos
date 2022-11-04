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

    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produto);

        produtoDAO = new ProdutoDAO(this);

        edit_produto = findViewById(R.id.ed_produto);
        edit_quantidade = findViewById(R.id.ed_quantidade);
        edit_valor = findViewById(R.id.ed_valor);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            /*passei informação pra tela? pq na hora de inserir um produto eu não to passando nada,
            quando eu clico no produto eu passo ele como parametro*/
            produto = (Produto) bundle.getSerializable("produto");
            //chave que usei na main
            editProduto();
        }

    }

    private void editProduto() {
        edit_produto.setText(produto.getNome());
        edit_quantidade.setText(String.valueOf(produto.getEstoque()));
        edit_valor.setText(String.valueOf(produto.getValor()));
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

                            if (produto == null ) produto = new Produto();
                            /*se o produto for nulo eu instancio um produto novo e ele vai ser nulo
                            quando eu clicar no add produto e ele nao vai passar nenhum produto de parametro*/
                            produto.setNome(nome);
                            produto.setEstoque(qtd);
                            produto.setValor(valorProduto);
                            if (produto.getId() != 0){
                                produtoDAO.atualizaProduto(produto);
                            }else {
                                Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                                produtoDAO.salvarProduto(produto);
                            }
                            finish();

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