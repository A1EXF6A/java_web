/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;
import com.google.gson.Gson;
import dao.RelacionDAO;
import model.Relacion;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "RelacionServlet", urlPatterns = {"/RelacionServlet"})
public class RelacionServlet extends HttpServlet {
    private final RelacionDAO relacionDAO = new RelacionDAO();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            List<Relacion> relaciones = relacionDAO.getAll();
            resp.getWriter().write(gson.toJson(relaciones));
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception ex) {
            Logger.getLogger(RelacionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Map<String, Object> data = gson.fromJson(req.getReader(), Map.class);
            int estudianteId = ((Double) data.get("estudiante_id")).intValue();
            int cursoId = ((Double) data.get("curso_id")).intValue();

            boolean success = relacionDAO.asignar(estudianteId, cursoId);
            resp.getWriter().write("{\"success\":" + success + "}");
        } catch (SQLException e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            resp.getWriter().write("{\"success\":false,\"error\":\"Datos inválidos\"}");
        }
    }
}