package com.example.controledeprodutos.helper;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseHelper {

    private static FirebaseAuth auth;

    public static FirebaseAuth getAuth() {
        //método estático que toda vez que requisitar esse método instancio o atributo e se for nulo
        //faço a conexão do meu projeto com o firebase por meio do getInstance
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }//se não for nulo também retorno o atributo, esse método tem várias opçoes de retorno, se o
        //usuário está logado ou não, etc
        return auth;
    }//atributo auth static onde posso usar sem instanciar a classe
}
