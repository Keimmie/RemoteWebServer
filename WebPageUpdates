<?php
	include 'db_connect.php';
	
	$query = "Select * FROM HeartRate t1 INNER JOIN Employee ON t1.USERid = Employee.PersonId Where t1.TimeRecieved > ADDDATE(current_timestamp(), INTERVAL - 1 minute) and t1.EntryNumber = (Select max(t2.EntryNumber) from HeartRate t2 Where t2.USERid = t1.USERid) ORDER BY USERid asc";
	
	if ($stmt = $con->prepare($query)) {
		$stmt->execute();
		$stmt->bind_result($USERid, $BPM, $TimeRecieved,$EntryNumber, $PersonId, $idEmployee, $FirstName, $MiddleInitial, $LastName, $DOB, $Height, $Weight);
		
		echo '<table cellspacing=70 class=grid id=live_table>
				<tr>
					<th>Employee Name </th>
					<th>Heart BPM</th>
					<th>Time</th>					
				</tr>';
		while ($stmt->fetch_array()) {
				$TimeRecieved2 = strtotime($TimeRecieved);
				
				$systime = new DateTime('now');	
				$dbtime = new DateTime($TimeRecieved);
				$interval = $systime->diff($dbtime);
			
				$new = (int)$interval->format('%s');
				echo $new;
				if($new < 10){		
				echo'<tr id=alert>
						<td>' . $FirstName . " " . $MiddleInitial . " " . $LastName . '</td>
						<td>
							<span class="heart">
							<span class="blinking">', $BPM,'</span></span>
						</td>
						<td>', date('h:i:s', $TimeRecieved2) ,'</td>
					</tr>';
				} else {
					echo'<tr id=alert>
							<td>' . $FirstName . " " . $MiddleInitial . " " . $LastName . '</td>
							<td>
								<span class="empty">
								<span class="stop">', $BPM,'</span></span>
							</td>
							<td>', date('h:i:s', $TimeRecieved2) ,'</td>
						</tr>';
					}
		} 
		echo '</table>';		
	}
	$stmt->close();

?>
