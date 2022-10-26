package com.example.controledeprodutos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
   /* o adaptador é um intermediador que recebe solicitações do cliente e converte essas solicitações
    num formato que o fornecedor entenda. O adaptador converte uma interface para outra.
    Se a interface do fornecedor mudar novamente apenas o Adaptador necessitará ser
    modificado sem alterar o resto do sistema. Link útil:
    https://www.devmedia.com.br/padrao-de-projeto-adapter-em-java/26467*/
public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder> {
       /*Esse adapter faz a ponte entre as informações do banco de dados com a visualização na tela do app
   por isso uso o Adapter do RecyclerView, e entre <> o nome da minha classe Adapter.inner class*/

       private List<Produto> produtoList;
        /*crio um construtor e quando eu declarar essa classe na main eu vou ter que passar a minha
       lista de produtos*/
       private OnClick onClick;
       //também declaro minha interface pois ela será passada como construtor
       public AdapterProduto(List<Produto> produtoList, OnClick onClick) {
           this.produtoList = produtoList;
           this.onClick = onClick;
           //lista de produtos como parametro do itemCount
           /*apos declarar pego a informação pelo construtor adc Onclick onclick que recebe o meu
           onClick passado via construtor, então eu tenho que receber no meu adapter onde
           vou implementar essa interface*/
       }

       @NonNull
       @Override
       public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /*nesse metodo eu declaro nossa linha de layout, o layout vai se repetir para a quantidade
        de registros que tivermos em nossa listagem, retorna uma nova instância do viewHolder*/
           View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produtos,
                   parent, false);
           //inflei meu layout
           return new MyViewHolder(itemView);
           /*instancio a classe MyViewHolder onde o parametro é um itemView que recebe os itens inflados
           do meu layout item_produtos que contem o xml dos itens da lista de produtos*/
       }

       @Override
       public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           //configuro as infos da linha, em cada linha ele altera as informações
           Produto produto = produtoList.get(position);
           //para cada produto que ele rodar eu pego as informações aqui
           //position já vem na minha classe Adapter
           //agora preciso exibir essas info pro usuário
           holder.text_produto.setText(produto.getNome());
           holder.text_estoque.setText("Estoque: " + produto.getEstoque());
           holder.text_valor.setText("R$ " + produto.getValor());

           holder.itemView.setOnClickListener(view -> onClick.onClickListener(produto));
           /*o lugar que eu implementar o adapter produto vou ter que passar lista de produtos via
           construtor e também o contexto da aplicação, ou seja, onde está sendo implementada essa
                   config do adapter*/
       }

       @Override
       public int getItemCount() {
       /*retorna o número de elementos presentes na lista de dados; Esse método precisa saber qtos
           itens terão no banco de dados para assim retornar o item em tela. Recebemos essa lista
           por meio do construtor declarando a lista e tipando com a classe Produto*/
           return produtoList.size();
       }

       public interface OnClick{
           void onClickListener(Produto produto);
       }

       static class MyViewHolder extends RecyclerView.ViewHolder {
           /*configuro quais elementos vou ter na listagem, e que vou precisar que exiba na tela.
            crio uma inner class (classe interna), uma classe estática que apresenta uma referência para
            as views presentes em cada linha da nossa lista.Declaro os componentes do layout*/
           //declaro os itens do meu layout
           TextView text_produto, text_estoque, text_valor;
           public MyViewHolder(@NonNull View itemView) {
               super(itemView);

               text_produto = itemView.findViewById(R.id.tv_produto);
               text_estoque = itemView.findViewById(R.id.estoque);
               text_valor = itemView.findViewById(R.id.preco);
                //esse itemView nada mais é que o layout que recebeu meu xml e foi declarado como =
           }
       }
}
