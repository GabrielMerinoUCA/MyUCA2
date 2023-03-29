<?php
    if($_SERVER["REQUEST_METHOD"] == "DELETE"){
        require_once "conexion.php";
        $array = json_decode(file_get_contents("php://input"));
        $idC = $array[0];

        $my_query = "delete from Coordinador where idC = $idC";
        $result = $conn->query($my_query);
        if($result){
            echo "exito";
        }else{
            echo "pain";
        }
    }
?>