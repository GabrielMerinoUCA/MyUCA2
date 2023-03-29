<?php
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        require_once "conexion.php";
        $nombres = $_POST["nombres"];
        $apellidos = $_POST["apellidos"];
        $fechaNac = $_POST["fechaNac"];
        $titulo = $_POST["titulo"];        
        $email = $_POST["email"];
        $facultad = $_POST["facultad"];

        $my_querry  = "Insert into Coordinador(nombres, apellidos, fechaNac, titulo, email, facultad) Values('$nombres', '$apellidos', '$fechaNac', '$titulo', '$email', '$facultad');";
        
        $result = $conn->query($my_querry);
        if($result){
            echo "guardado exitosamente";
        }else{
            echo "Pain...";
        }
        $conn->close();
    }
?>