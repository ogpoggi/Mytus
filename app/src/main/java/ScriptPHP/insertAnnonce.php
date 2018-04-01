<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
	
include 'DatabaseConfig.php';


 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 $titre = $_POST[""];
 $duration = $_POST[""];
 $nbplace = $_POST[""];
 $location = $_POST[""];
 $description = $_POST[""];
 $idUser = $_POST[""];
 $idCategorie =$_POST[""];
 
 $Sql_Query = "INSERT INTO annonce (title,duration,nbPlace,location,description,idUser,idCategorie) values ('$titre', '$duration', '$nbplace','$location', '$description','$idUser', '$idCategorie')";
 if(mysqli_query($con,$Sql_Query)){
	echo "Annonce enregistrée";
 }
 else{
	echo 'Something went wrong';
 }
}
  mysqli_close($con);
?>