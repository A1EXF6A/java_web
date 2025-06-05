package dao;

import model.Estudiante;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    public List<Estudiante> getAll() throws SQLException, Exception {
        List<Estudiante> estudiantes = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM estudiantes");

        while (rs.next()) {
            Estudiante e = new Estudiante();
            e.setId(rs.getInt("id"));
            e.setNombre(rs.getString("nombre"));
            e.setCedula(rs.getString("cedula"));
            estudiantes.add(e);
        }

        conn.close();
        return estudiantes;
    }

    public Estudiante buscarPorCedula(String cedula) throws SQLException,Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM estudiantes WHERE cedula = ?");
        stmt.setString(1, cedula);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Estudiante e = new Estudiante();
            e.setId(rs.getInt("id"));
            e.setNombre(rs.getString("nombre"));
            e.setCedula(rs.getString("cedula"));
            return e;
        }

        conn.close();
        return null;
    }

    public boolean crear(Estudiante estudiante) throws SQLException,Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO estudiantes (nombre, cedula) VALUES (?, ?)");
        stmt.setString(1, estudiante.getNombre());
        stmt.setString(2, estudiante.getCedula());
        boolean success = stmt.executeUpdate() > 0;
        conn.close();
        return success;
    }

    public boolean actualizar(Estudiante estudiante) throws SQLException, Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE estudiantes SET nombre = ? WHERE cedula = ?");
        stmt.setString(1, estudiante.getNombre());
        stmt.setString(2, estudiante.getCedula());
        boolean success = stmt.executeUpdate() > 0;
        conn.close();
        return success;
    }

    public boolean eliminar(String cedula) throws SQLException,Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM estudiantes WHERE cedula = ?");
        stmt.setString(1, cedula);
        boolean success = stmt.executeUpdate() > 0;
        conn.close();
        return success;
    }
}
