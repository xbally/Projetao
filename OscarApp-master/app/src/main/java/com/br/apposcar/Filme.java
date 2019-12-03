package com.br.apposcar;

public class Filme {
    private int id;
    private String nome;
    private String genero;
    private String foto;


    public Filme(int id, String nome, String genero, String foto){
        this.id=id;
        this.nome=nome;
        this.genero=genero;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
