<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
	
include 'DatabaseConfig.php';


 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

 $Username = $_POST['username'];
 $Email = $_POST['email'];
 
 $Password = $_POST['password'];
 
 

 $CheckSQL = "SELECT * FROM user WHERE email='$Email'";
 
 $check = mysqli_fetch_array(mysqli_query($con,$CheckSQL));
 
 if(isset($check)){

 echo 'Email Already Exist, Please Enter Another Email.';

 }
else{ 
$Sql_Query = "INSERT INTO user (username,email,password) values ('$Username', '$Email', '$Password')";

 if(mysqli_query($con,$Sql_Query))
{
 echo 'User Registration Successfully';
}
else
{
 echo 'Something went wrong';
 }
 }
}
 mysqli_close($con);
?>