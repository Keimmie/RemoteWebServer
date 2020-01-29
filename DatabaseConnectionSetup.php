<?php
	//Setup Connection
		$host="localhost";
		$port=3306;
	//Adding a socket is not a nesscisyt	
		$socket="";
		$user="adminusername";
		$password="adminpassword";
		$dbname="DatabaseName";
	//Conncetion variable
		$con = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());
	//Connectino Function
		 if ($con ) {
			echo "";
		 } else {
			 echo "Server Connection failed";
		 }
?>
