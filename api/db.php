<?php
function getDB()
{
    static $db = null;

    if ($db === null) {
        $host = 'localhost:3308'; // Cambia el puerto si es necesario
        $dbname = 'productos'; // Cambia el nombre de la base de datos si es necesario
        $user = 'root';
        $pass = '';

        try {
            $db = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $user, $pass);
            $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            echo json_encode(["error" => "Conexión fallida: " . $e->getMessage()]);
            exit;
        }
    }

    return $db;
}
