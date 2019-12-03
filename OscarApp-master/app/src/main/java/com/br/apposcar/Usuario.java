package com.br.apposcar;



import java.io.Serializable;


public class Usuario implements Serializable {
    private String nome;
    private String senha;
    private String filme;
    private String diretor;
    private int usuario;
    private int votou;
    private int token;

    public Usuario(String nome, String senha, String filme, String diretor, int usuario, int votou, int token) {
        this.nome = nome;
        this.senha = senha;
        this.filme = filme;
        this.diretor = diretor;
        this.usuario = usuario;
        this.votou = votou;
        this.token = token;
    }

    public Usuario(String nome, String senha, String filme, String diretor, int usuario, int votou) {
        this.nome = nome;
        this.senha = senha;
        this.filme = filme;
        this.diretor = diretor;
        this.usuario = usuario;
        this.votou = votou;
        this.token = -1;
    }

    public String getFilme() {
        return filme;
    }

    public void setFilme(String filme) {
        this.filme = filme;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getVotou() {
        return votou;
    }

    public void setVotou(int votou) {
        this.votou = votou;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
