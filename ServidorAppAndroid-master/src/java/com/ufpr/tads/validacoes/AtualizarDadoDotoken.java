/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.validacoes;

import com.ufpr.tads.bean.Login;
import com.ufpr.tads.dao.UserDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 *
 * @author Gabriel
 */
@WebServlet(name = "AtualizarDadoDotoken", urlPatterns = {"/AtualizarDadoDotoken"})
public class AtualizarDadoDotoken extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        if (request.getMethod().equals("POST")) {
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
              BufferedReader reader = request.getReader();
              while ((line = reader.readLine()) != null)
                jb.append(line);
            } catch (Exception ex) { 
                System.out.println(ex.getMessage());
            }

            try {
                JSONObject jsonObject =  JSONObject.fromObject(jb.toString());

                String name = (String) jsonObject.get("name");
                System.out.println(name);
                String filme = (String) jsonObject.get("filme");
                System.out.println(filme);
                
                String diretor = (String) jsonObject.get("diretor");
                System.out.println(diretor);
                Integer token = (Integer) jsonObject.get("token");
                System.out.println(token);
                
                
                UserDao user = new UserDao();
                int idFound = user.checkVoto(name);
                HashMap<String, String> hm = new HashMap<String, String>();
                
                if ( idFound == 0 ) {
                    Login log = new Login();
                    log.setNome(name);
                    log.setFilme(filme);
                    log.setDiretor(diretor);
                    log.setToken(token);
                    
                    user.confirmaVoto(log);
                    hm.put("insert", "true");
                    hm.put("message", "Usuario Inserido");
                } else {
                    hm.put("insert", "false");
                    hm.put("message", "Usuario com este email já existente");
                }
                JSONObject json = JSONObject.fromObject(hm);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(json);
                out.flush();
            } catch (SQLException | JSONException ex) {
              System.out.println(ex.getMessage());
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
