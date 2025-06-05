<?php
require_once 'db.php';

class CursoController
{
    public static function getAll()
    {
        $db = getDB();
        $stmt = $db->query("SELECT * FROM cursos");
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($result);
    }

    public static function buscarPorCodigo($codigo)
    {
        $db = getDB();
        $stmt = $db->prepare("SELECT * FROM cursos WHERE codigo = ?");
        $stmt->execute([$codigo]);
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        echo json_encode($result ?: ["success" => false, "error" => "Curso no encontrado"]);
    }

    public static function crear($data)
    {
        $db = getDB();
        $stmt = $db->prepare("INSERT INTO cursos (nombre, codigo) VALUES ( ?, ?)");
        $success = $stmt->execute([
            $data['nombre'],
            $data['codigo']
        ]);
        echo json_encode(["success" => $success]);
    }

    public static function actualizar($data)
    {
        $db = getDB();
        $stmt = $db->prepare("UPDATE cursos SET nombre=? WHERE codigo=?");
        $success = $stmt->execute([
            $data['nombre'],
            $data['codigo']
        ]);
        echo json_encode(["success" => $success]);
    }

    public static function eliminar($codigo)
    {
        $db = getDB();
        $stmt = $db->prepare("DELETE FROM cursos WHERE codigo = ?");
        $success = $stmt->execute([$codigo]);
        echo json_encode(["success" => $success]);
    }
}
