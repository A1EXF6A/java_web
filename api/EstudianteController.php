<?php
require_once 'db.php';

class EstudianteController
{
    public static function getAll()
    {
        $db = getDB();
        $stmt = $db->query("SELECT * FROM estudiantes");
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($result);
    }

    public static function buscarPorCedula($cedula)
    {
        $db = getDB();
        $stmt = $db->prepare("SELECT * FROM estudiantes WHERE cedula = ?");
        $stmt->execute([$cedula]);
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        echo json_encode($result ?: ["success" => false, "error" => "Estudiante no encontrado"]);
    }

    public static function crear($data)
    {
        $db = getDB();
        $stmt = $db->prepare("INSERT INTO estudiantes (nombre, cedula) VALUES (?, ?)");
        $success = $stmt->execute([
            $data['nombre'],
            $data['cedula']
        ]);
        echo json_encode(["success" => $success]);
    }

    public static function actualizar($data)
    {
        $db = getDB();
        $stmt = $db->prepare("UPDATE estudiantes SET nombre=? WHERE cedula=?");
        $success = $stmt->execute([
            $data['nombre'],
            $data['cedula']
        ]);
        echo json_encode(["success" => $success]);
    }

    public static function eliminar($cedula)
    {
        $db = getDB();
        $stmt = $db->prepare("DELETE FROM estudiantes WHERE cedula = ?");
        $success = $stmt->execute([$cedula]);
        echo json_encode(["success" => $success]);
    }
}
