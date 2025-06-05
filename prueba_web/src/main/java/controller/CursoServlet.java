/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CursoDAO;
import model.Curso;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HOME
 */
@WebServlet(name = "CursoServlet", urlPatterns = {"/CursoServlet"})
public class CursoServlet extends HttpServlet {
    private final CursoDAO cursoDAO = new CursoDAO();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            String codigo = req.getParameter("codigo");
            if (codigo != null) {
                Curso curso = cursoDAO.buscarPorCodigo(codigo);
                if (curso != null) {
                    resp.getWriter().write(gson.toJson(curso));
                } else {
                    resp.getWriter().write("{\"success\":false,\"error\":\"Curso no encontrado\"}");
                }
            } else {
                List<Curso> cursos = cursoDAO.getAll();
                resp.getWriter().write(gson.toJson(cursos));
            }
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception ex) {
            Logger.getLogger(CursoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Curso curso = gson.fromJson(req.getReader(), Curso.class);
        try {
            boolean success = cursoDAO.crear(curso);
            resp.getWriter().write("{\"success\":" + success + "}");
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception ex) {
            Logger.getLogger(CursoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Curso curso = gson.fromJson(req.getReader(), Curso.class);
        try {
            boolean success = cursoDAO.actualizar(curso);
            resp.getWriter().write("{\"success\":" + success + "}");
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception ex) {
            Logger.getLogger(CursoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Curso curso = gson.fromJson(req.getReader(), Curso.class);
        try {
            boolean success = cursoDAO.eliminar(curso.getCodigo());
            resp.getWriter().write("{\"success\":" + success + "}");
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception ex) {
            Logger.getLogger(CursoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}