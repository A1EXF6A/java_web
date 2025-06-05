package dao;

import model.Relacion;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelacionDAO {

    public boolean asignar(int estudianteId, int cursoId) throws SQLException, Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO estudiante_curso (estudiante_id, curso_id) VALUES (?, ?)"
        );
        stmt.setInt(1, estudianteId);
        stmt.setInt(2, cursoId);
        boolean success = stmt.executeUpdate() > 0;
        conn.close();
        return success;
    }

    public List<Relacion> getAll() throws SQLException,Exception {
        List<Relacion> relaciones = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(
    "SELECT " +
    "   e.nombre AS estudiante, " +
    "   c.nombre AS curso " +
    "FROM estudiante_curso ec " +
    "INNER JOIN estudiantes e ON ec.estudiante_id = e.id " +
    "INNER JOIN cursos c ON ec.curso_id = c.id"
);

        while (rs.next()) {
            Relacion r = new Relacion();
            r.setEstudiante(rs.getString("estudiante"));
            r.setCurso(rs.getString("curso"));
            relaciones.add(r);
        }

        conn.close();
        return relaciones;
    }
}
