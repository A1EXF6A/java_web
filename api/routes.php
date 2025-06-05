<?php
require_once 'CursoController.php';
require_once 'EstudianteController.php';
require_once 'RelacionController.php'; // Para asignar estudiante a curso, por ejemplo

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}

$method = $_SERVER['REQUEST_METHOD'];
$resource = $_GET['resource'] ?? null; // e.g. 'estudiante', 'curso'

switch ($method) {
    case 'GET':
        $body = json_decode(file_get_contents("php://input"), true);
        if ($resource === 'estudiante') {
            if (!empty($_GET['cedula'])) {
                EstudianteController::buscarPorCedula($_GET['cedula']);
            } else {
                EstudianteController::getAll();
            }
        } elseif ($resource === 'curso') {
            if (!empty($_GET['codigo'])) {
                CursoController::buscarPorCodigo($_GET['codigo']);
            } else {
                CursoController::getAll();
            }
        } elseif ($resource === 'relacion') {
            RelacionController::getAll();
        } else {
            echo json_encode(["success" => false, "error" => "Recurso GET no reconocido"]);
        }
        break;

    case 'POST':
        $data = json_decode(file_get_contents("php://input"), true);
        if ($resource === 'estudiante') {
            EstudianteController::crear($data);
        } elseif ($resource === 'curso') {
            CursoController::crear($data);
        } elseif ($resource === 'asignar') {
            RelacionController::asignar($data);
        } else {
            echo json_encode(["success" => false, "error" => "Recurso POST no reconocido"]);
        }
        break;

    case 'PUT':
        $data = json_decode(file_get_contents("php://input"), true);
        if ($resource === 'estudiante') {
            EstudianteController::actualizar($data);
        } elseif ($resource === 'curso') {
            CursoController::actualizar($data);
        } else {
            echo json_encode(["success" => false, "error" => "Recurso PUT no reconocido"]);
        }
        break;

    case 'DELETE':
        $data = json_decode(file_get_contents("php://input"), true);
        if ($resource === 'estudiante' && !empty($data['cedula'])) {
            EstudianteController::eliminar($data['cedula']);
        } elseif ($resource === 'curso' && !empty($data['codigo'])) {
            CursoController::eliminar($data['codigo']);
        } else {
            echo json_encode(["success" => false, "error" => "Datos faltantes en DELETE"]);
        }
        break;

    default:
        echo json_encode(["success" => false, "error" => "Método no permitido"]);
        break;
}
