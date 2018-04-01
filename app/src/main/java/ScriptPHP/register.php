<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
	
include 'DatabaseConfig.php';


 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

 $Username = $_POST['username'];
 $Email = $_POST['email'];
 
 $Password = $_POST['password'];
 $Avatar = $_POST['avatar'];
 $Password = password_hash($Password, PASSWORD_BCRYPT);
 $CheckSQL = "SELECT * FROM user WHERE email='$Email'";
 
 $check = mysqli_fetch_array(mysqli_query($con,$CheckSQL));
 
 if(isset($check)){
	echo 'Email Already Exist, Please Enter Another Email.';
 }
else{ 	
	$p = "images/$Email.png";
	$mainpath = "http://bpoggifrpw.cluster026.hosting.ovh.net/Android/Mytus/$p";
	$Sql_Query = "INSERT INTO user (username,email,password,avatar) values ('$Username', '$Email', '$Password','".$mainpath."')";
 if(mysqli_query($con,$Sql_Query)){
	file_put_contents($p,base64_decode($Avatar));
	echo "Successfully Uploaded and user created";
 }
else
{
 echo 'Something went wrong';
 }
 }
}
 mysqli_close($con);
?>