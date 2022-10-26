package com.example.controledeprodutos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduto.OnClick {


    private ImageButton ibAdd;
    private ImageButton ibVerMais;


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

        //essa opção da ao nosso app o poder de ter esse menu em versoes mais antigas do android

        ouvinteCliques();
        carregaLista();
        configRecyclerView();
    }


    private void ouvinteCliques() {
        ibAdd.setOnClickListener(view -> {
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
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
                produtoList.remove(position);
                adapterProduto.notifyItemRangeRemoved(position, 1);
            }

            @Override
            public void onSwipedRight(int position) {

            }
        });

    }

    private void carregaLista(){

        Produto produto1 = new Produto();
        produto1.setNome("Monitor LG 34");
        produto1.setEstoque(45);
        produto1.setValor(1349.99);

        Produto produto2 = new Produto();
        produto2.setNome("Caixa de Som C3 Tech");
        produto2.setEstoque(15);
        produto2.setValor(149.99);

        Produto produto3 = new Produto();
        produto3.setNome("Microfone Blue yeti");
        produto3.setEstoque(38);
        produto3.setValor(1699.99);

        Produto produto4 = new Produto();
        produto4.setNome("Gabinete NZXT H440");
        produto4.setEstoque(15);
        produto4.setValor(979.99);

        Produto produto5 = new Produto();
        produto5.setNome("Placa Mãe Asus");
        produto5.setEstoque(60);
        produto5.setValor(1199.99);

        Produto produto6 = new Produto();
        produto6.setNome("Memória Corsair 16GB");
        produto6.setEstoque(44);
        produto6.setValor(599.99);

        produtoList.add(produto1);
        produtoList.add(produto2);
        produtoList.add(produto3);
        produtoList.add(produto4);
        produtoList.add(produto5);
        produtoList.add(produto6);

    }

    @Override
    public void onClickListener(Produto produto) {
        Toast.makeText(this, produto.getNome(), Toast.LENGTH_SHORT).show();
    }


}
