<?php
require_once 'db.php';

class RelacionController
{
    public static function asignar($data)
    {
        $db = getDB();
        $stmt = $db->prepare("INSERT INTO estudiante_curso (estudiante_id, curso_id) VALUES (?, ?)");
        $success = $stmt->execute([$data['estudiante_id'], $data['curso_id']]);
        echo json_encode(["success" => $success]);
    }

    public static function getAll()
    {
        $db = getDB();
        $stmt = $db->query("
        SELECT 
            e.nombre AS estudiante,
            c.nombre AS curso
        FROM estudiante_curso ec
        INNER JOIN estudiantes e ON ec.estudiante_id = e.id
        INNER JOIN cursos c ON ec.curso_id = c.id
    ");
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($result);
    }

}

