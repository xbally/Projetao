package com.br.apposcar;

import java.io.Serializable;

public class Candidato implements Serializable {
    private int id;
    private String nome;
    private String genero;
    private String foto;



    public Candidato(int id, String nome, String genero, String foto) /*throws IOException*/ {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.foto = foto;

        //this.imgBitmap = new AuxiliarImg().baixarImagem(linkFoto);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLinkFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}