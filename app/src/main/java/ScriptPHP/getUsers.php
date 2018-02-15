<?php
	include 'DatabaseConfig.php';

	$con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName); 
	if(mysqli_connect_error($con)){
		echo"error";
	}
	else if(!mysqli_connect_error($con)){
		echo"connect";
	}
	$query = mysqli_query($con,"SELECT id,username,email FROM user");
	if($query){
		while($row = mysqli_fetch_array($query)){
			$flags[] = $row;
		}
	}
	print(json_encode($flags));

mysqli_close($con);
?>