package com.ufpr.tads.bean;

import java.io.Serializable;

public class Login implements Serializable {

	private static final long serialVersionUID = 2710279873671045237L;
	private int usuario;
	private int votou;
	private int token;
	private String senha;
	private String nome;
	private String filme;
	private String diretor;

	public Login(){
		super();
	}
	
	public Login(String nome){
		super();
		this.nome = nome;
	}
	
	public Login(String nome, String senha){
		super();
		this.nome = nome;
		this.senha = senha;
	}

	public Login(int usuario, int votou, int token, String senha, String nome, String filme, String diretor) {
		super();
		this.usuario = usuario;
		this.votou = votou;
		this.token = token;
		this.senha = senha;
		this.nome = nome;
		this.filme = filme;
		this.diretor = diretor;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
	
	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

}
