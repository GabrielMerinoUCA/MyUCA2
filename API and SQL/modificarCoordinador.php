<?php
    if($_SERVER["REQUEST_METHOD"] == "PUT"){
        require_once "conexion.php";
        $array= json_decode(file_get_contents("php://input"));

        $idC = $array[0];
        $nombres = $array[1];
        $apellidos = $array[2];
        $fechaNac = $array[3];
        $titulo = $array[4];
        $email = $array[5];
        $facultad = $array[6];

        $my_query = "update Coordinador set nombres = '$nombres', apellidos = '$apellidos', fechaNac = '$fechaNac', titulo = '$titulo', email = '$email', facultad = '$facultad' where idC = $idC";
        $result = $conn->query($my_query);
        if($result){
            echo "guardado exitosamente";
        }else{
            echo "Pain";
        }    
    }
?>