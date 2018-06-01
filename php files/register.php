<?php
if(isset($_POST) == true && empty($_POST) == false){
	$latitude = $_POST['Latitude'];
	$longitude = $_POST['Longitude'];
	$userid = $_POST['userId'];
	$timestamp = $_POST['Timestamp'];
        $Password=$_POST['Password'];	
        $name=$_POST['userName'];


	$dbname = "friendFinder";
	$servername = "127.0.0.1";
	$username = "root";
	$password = "123456";

	//connect to the database
	$conn = mysqli_connect($servername, $username, $password, $dbname);
	if(!$conn)
	{
		die("Connection Failed" . mysqli_error($conn));
	}
	
	$sql = "INSERT INTO LocationTable(userId,name,Password,Latitude, Longitude, Timestamp) VALUES('$userid','$name','$Password',$latitude, $longitude, \"$timestamp\")";
	if(mysqli_query($conn, $sql))
	{
		$result = array("Result"=>1, "Message"=>"Inserted data correctly");
		echo json_encode($result);
	}
	else {
		echo "Error: " . mysqli_error($conn);
	}	

	mysqli_close($conn);
		

}
else {
	echo "POST message is empty \n";	
}

?>
