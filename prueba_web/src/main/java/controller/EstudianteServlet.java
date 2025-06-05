/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;
import dao.EstudianteDAO;
import model.Estudiante;
import com.google.gson.Gson;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HOME
 */
@WebServlet(name = "EstudianteServlet", urlPatterns = {"/EstudianteServlet"})
public class EstudianteServlet extends HttpServlet {
    private final EstudianteDAO estudianteDAO = new EstudianteDAO();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            String cedula = req.getParameter("cedula");
            if (cedula != null) {
                Estudiante estudiante = estudianteDAO.buscarPorCedula(cedula);
                if (estudiante != null) {
                    resp.getWriter().write(gson.toJson(estudiante));
                } else {
                    resp.getWriter().write("{\"success\":false,\"error\":\"Estudiante no encontrado\"}");
                }
            } else {
                List<Estudiante> estudiantes = estudianteDAO.getAll();
                resp.getWriter().write(gson.toJson(estudiantes));
            }
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception ex) {
            Logger.getLogger(EstudianteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Estudiante estudiante = gson.fromJson(req.getReader(), Estudiante.class);
        try {
            boolean success = estudianteDAO.crear(estudiante);
            resp.getWriter().write("{\"success\":" + success + "}");
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception ex) {
            Logger.getLogger(EstudianteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Estudiante estudiante = gson.fromJson(req.getReader(), Estudiante.class);
        try {
            boolean success = estudianteDAO.actualizar(estudiante);
            resp.getWriter().write("{\"success\":" + success + "}");
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception ex) {
            Logger.getLogger(EstudianteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Estudiante estudiante = gson.fromJson(req.getReader(), Estudiante.class);
        try {
            boolean success = estudianteDAO.eliminar(estudiante.getCedula());
            resp.getWriter().write("{\"success\":" + success + "}");
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception ex) {
            Logger.getLogger(EstudianteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}