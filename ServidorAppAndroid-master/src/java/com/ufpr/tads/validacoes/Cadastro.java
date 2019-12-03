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

@WebServlet("/Cadastro")
public class Cadastro extends HttpServlet {
	private static final long serialVersionUID = -3167223651212632418L;

	public Cadastro() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> hm = new HashMap<String, String>();
    	String login = request.getParameter("login");
		String password = request.getParameter("senha");
		String msg;
		
		UserDao userdao;
		userdao = new UserDao();
		try {
			Login usuarioRetornado = userdao.getUserByLoginAndPassword(login, password);
			if(usuarioRetornado == null) {
				userdao.insertUser(login, password);
				msg = "Usuario registrado com sucesso.";
			} else {
				msg = "Login existente";
			}
			hm.put("message", msg);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

