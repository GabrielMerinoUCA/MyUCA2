<?php
    $conn = mysqli_connect("localhost", "root","", "MyUCA", "3306");
    if(!$conn){
        echo "Error de conexion";
    }