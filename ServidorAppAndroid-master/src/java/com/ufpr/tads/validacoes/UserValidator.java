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
@WebServlet("/UserValidator")
public class UserValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserValidator() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> hm = new HashMap<String, String>();
		String login = request.getParameter("login");
		String password = request.getParameter("senha");
		String msg;
		
		Login loginBD = new Login();
		loginBD.setNome(login);
		loginBD.setSenha(password);
		
		UserDao userdao;
		userdao = new UserDao();
		try {
			Login usuarioRetornado = userdao.getLogin(loginBD);//vai mandar o usario e senha se encontrar devolve os mesmos...
			if(usuarioRetornado.getNome().equals(login) && usuarioRetornado.getSenha().equals(password)){
				msg = "Login correto";
			}else{
				msg = "Login errado";
			}
			hm.put("message", msg);
			hm.put("usuario", String.valueOf(usuarioRetornado.getUsuario()));
			hm.put("votou", String.valueOf(usuarioRetornado.getVotou()));
			hm.put("token", String.valueOf(usuarioRetornado.getToken()));
			hm.put("senha", usuarioRetornado.getSenha());
			hm.put("nome", usuarioRetornado.getNome());
			hm.put("filme", usuarioRetornado.getFilme());
			hm.put("diretor", usuarioRetornado.getDiretor());
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
