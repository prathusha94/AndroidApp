<?php
if(isset($_POST) == true && empty($_POST) == false){
	$latitude = $_POST['Latitude'];
	$longitude = $_POST['Longitude'];
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
       
        
        $check = mysqli_query($conn,"Select * from LocationTable where name='$name' and Password = '$Password';");
        if(mysqli_num_rows($check)!=0)
        {
        	$sql = "Update LocationTable SET Latitude='$latitude', Longitude='$longitude' WHERE name='$name' AND Password='$Password';" ;
		$result1 = mysqli_query($conn, $sql); // its just a update action
        	//echo("$result1");
		if($result1)
		{
        		 $sql2= "Select DISTINCT name,Latitude,Longitude from LocationTable where name!='$name'";

	 		$result=mysqli_query($conn,$sql2); // fetch the value from the database.
         		//echo($result);
	 		$response = array();
	 		$i=1;
	 		while($row = mysqli_fetch_array($result))
	 		{

   				$n=$row[0];
				$lat = $row[1];
                		$lon = $row[2];
               
               			$distance =111.045* rad2deg(acos((cos(deg2rad($latitude)) ) * (cos(deg2rad($lat))) * (cos(deg2rad($lon) - deg2rad($longitude)) )+ ((sin(deg2rad($latitude))) * (sin(deg2rad($lat))))) );
			                
	       			// echo("$distance");
				//echo("\n");
                		if($distance<1)
				{       
                        
                			$response["$i"] = array("name"=>$row[0],"Latitude"=>$row[1],"Longitude"=>$row[2]);
                        		//echo json_encode(array("Login"=>$response));
                        		$i++;
               			}
			}
			if(!$response)
			{
				$error_msg=array();
               			$error_msg = array("Result"=>0,"Error"=>"No such user exists!Please Register before Login");
   				echo json_encode(array("Login"=>$error_msg));
        		}
      			else 
          			echo json_encode(array("Login"=>(array("Result"=>1,"friends"=>$response))));
     		}
	}
       else
       {
         $error_msg = array("Result"=>0,"Error"=>"No such user exists!Please Register before Login");
          echo json_encode(array("Login"=>$error_msg));
       }
	
}
?>
       
