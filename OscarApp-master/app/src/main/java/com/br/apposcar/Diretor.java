package com.br.apposcar;




import java.io.Serializable;

public class Diretor implements Serializable {
    private String nome;
    private int id;


    public Diretor(String nome, int id) /*throws IOException*/ {
        this.nome = nome;
        this.id = id;
        //this.imgBitmap = new AuxiliarImg().baixarImagem(linkFoto);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}