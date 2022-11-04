package com.example.controledeprodutos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduto.OnClick {

    private ImageButton ibAdd;
    private ImageButton ibVerMais;
    private ProdutoDAO produtoDAO;
    private TextView text_info;


    private List<Produto> produtoList = new ArrayList<>();
    /*Criei uma lista do tipo List<Classe que criei contendo as infos dos produtos> List é da classe
    ArrayList<> e instancio*/
    private SwipeableRecyclerView rvProdutos;
    //declarei uma variavel do tipo SwipeRecyclerView para conseguir acessar o swipe do meu xml
    private AdapterProduto adapterProduto;
    //criei um Adapter para fazer a ponte das infos com a interface do usuário, declarei.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibAdd = findViewById(R.id.add);
        ibVerMais = findViewById(R.id.more);
        rvProdutos = findViewById(R.id.rv_produtos);
        text_info = findViewById(R.id.tv_info);

        produtoDAO = new ProdutoDAO(this);
        produtoList = produtoDAO.getListProdutos();
        ouvinteCliques();

        configRecyclerView();
        //essa opção da ao nosso app o poder de ter esse menu em versoes mais antigas do android
    }

    @Override
    protected void onStart() {
        super.onStart();
    /*quando clico em salvar chama-se o método onStart e no start limpa a lista e recupera os produtos
     novamente, atualizando a lista*/
        configRecyclerView();
    }

    private void ouvinteCliques() {
        ibAdd.setOnClickListener(view -> {
            startActivity(new Intent(this, FormProdutoActivity.class));
            /*quando eu clicar no botao add tenho que abrir a tela form_produto, e para isso
             crio o startActivity, pois criei uma nova activity, eu instancio um intent e passo da
             onde quero sair, this, para onde quero ir que é a activity FormProdutoActivity*/
        });

        ibVerMais.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, ibVerMais);
            popupMenu.getMenuInflater().inflate(R.menu.menu_toolbar, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_sobre){
                    Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show();
                }
                return true;
            });

            popupMenu.show();

        });

    }

    private void configRecyclerView(){

        produtoList.clear();
        produtoList = produtoDAO.getListProdutos();

        verificaQtdLista();

        //vou passar o padrão do meu layout
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setHasFixedSize(true);
        //deixamos o recyclerview com desempenho melhor
        adapterProduto = new AdapterProduto(produtoList, this);
        //instanciei o AdapterProdutuo
        rvProdutos.setAdapter(adapterProduto);

        rvProdutos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
                Produto produto = produtoList.get(position);
                produtoDAO.deleteProduto(produto);
                produtoList.remove(produto);
                adapterProduto.notifyItemRangeRemoved(position, 1);
                verificaQtdLista ();
            }

            @Override
            public void onSwipedRight(int position) {

            }
        });
    }

    private void verificaQtdLista(){

        if (produtoList.size() == 0){
            text_info.setVisibility(View.VISIBLE);
        }else {
            text_info.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClickListener(Produto produto) {
        Intent intent = new Intent(this, FormProdutoActivity.class);
        /*Abrir a classe e enviar o objeto de uma tela para outra, vou utilizar a mesma tela de salvar
        //para editar o produto*/
        intent.putExtra("produto", produto);
        startActivity(intent);

    }

}
