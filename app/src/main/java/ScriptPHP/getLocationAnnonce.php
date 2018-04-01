<?php
	include 'DatabaseConfig.php';

	$con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName); 
	if(mysqli_connect_error($con)){
		echo"error";
	}

	$query = mysqli_query($con,"SELECT lcoation FROM annonce");
	if($query){
		while($row = mysqli_fetch_assoc($query)){
			$flags[] = $row;
		}
	}
	print(json_encode($flags));

mysqli_close($con);
?>