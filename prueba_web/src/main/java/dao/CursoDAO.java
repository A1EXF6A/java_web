package dao;

import model.Curso;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class CursoDAO {
    public List<Curso> getAll() throws SQLException, Exception {
        List<Curso> cursos = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM cursos");

        while (rs.next()) {
            Curso c = new Curso();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setCodigo(rs.getString("codigo"));
            cursos.add(c);
        }

        conn.close();
        return cursos;
    }

    public Curso buscarPorCodigo(String codigo) throws SQLException, Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cursos WHERE codigo = ?");
        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Curso c = new Curso();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setCodigo(rs.getString("codigo"));
            return c;
        }

        conn.close();
        return null;
    }

    public boolean crear(Curso curso) throws SQLException, Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO cursos (nombre, codigo) VALUES (?, ?)");
        stmt.setString(1, curso.getNombre());
        stmt.setString(2, curso.getCodigo());
        boolean success = stmt.executeUpdate() > 0;
        conn.close();
        return success;
    }

    public boolean actualizar(Curso curso) throws SQLException, Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE cursos SET nombre=? WHERE codigo=?");
        stmt.setString(1, curso.getNombre());
        stmt.setString(2, curso.getCodigo());
        boolean success = stmt.executeUpdate() > 0;
        conn.close();
        return success;
    }

    public boolean eliminar(String codigo) throws SQLException, Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM cursos WHERE codigo = ?");
        stmt.setString(1, codigo);
        boolean success = stmt.executeUpdate() > 0;
        conn.close();
        return success;
    }
}
