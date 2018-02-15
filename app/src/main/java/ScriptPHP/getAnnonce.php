<?php
//if($_SERVER['REQUEST_METHOD']=='POST'){
	include 'DatabaseConfig.php';

	$con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName); 
	//"SELECT `idAnnonce`, `title`, `duration`, `nbPlace`, `location`, `description` FROM `annonce`";
	if(mysqli_connect_error($con)){
		echo"error";
	}
	else if(!mysqli_connect_error($con)){
		echo"connect";
	}
	$query = mysqli_query($con,"SELECT idAnnonce, title, duration, nbPlace, location, description FROM annonce");
	if($query){
		while($row = mysqli_fetch_array($query)){
			$flags[] = $row;
		}
	}
	print(json_encode($flags));

mysqli_close($con);
?>