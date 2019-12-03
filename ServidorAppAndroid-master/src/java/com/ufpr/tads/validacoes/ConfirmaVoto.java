package com.ufpr.tads.validacoes;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ufpr.tads.bean.Login;
import com.ufpr.tads.dao.UserDao;

import net.sf.json.JSONObject;

@WebServlet("/ConfirmaVoto")
public class ConfirmaVoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ConfirmaVoto() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> hm = new HashMap<String, String>();
		String nome = request.getParameter("nome");
                System.out.println(nome);
		String filme = request.getParameter("filme");
                System.out.println(filme);
		String diretor = request.getParameter("diretor");
                System.out.println(diretor);
		String tokenStr = request.getParameter("token");
                System.out.println(tokenStr);
		String msg = "";
		
		Login confirmaVotoBD = new Login();
		confirmaVotoBD.setNome(nome);
		confirmaVotoBD.setFilme(filme);
		confirmaVotoBD.setDiretor(diretor);

		UserDao userdao;
		userdao = new UserDao();
		try {
                    int checkVoto = userdao.checkToken(nome);
                    System.out.println(checkVoto);			
                    Integer token = Integer.valueOf(tokenStr);
                     System.out.println(token);
			
			if(checkVoto == 1){
				msg = "Usuario ja votou";
			} else if(token != userdao.checkToken(nome)) {
				msg = "Token incorreto";
			} else {
				Login confirmado = userdao.confirmaVoto(confirmaVotoBD);
			
				if(confirmado.getVotou() == 1){
					msg = "Voto confirmado";
				}else{
					msg = "Erro ao confirmar voto";
				}
			}
			hm.put("message", msg);
			//Cada chave do HashMap vira uma chave do JSON
			//JSONObject json = JSONObject.fromObject(hm);
			JSONObject json = JSONObject.fromObject(hm);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
