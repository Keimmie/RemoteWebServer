<html>
	<head>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="0">
		<link rel = "stylesheet" href = "test.css" />
		<script type="text/javascript" src="jquery.js"></script>
	</head>
<body>
	<h1> Heart Rate Monitor </h1>
	<div class="navbar">
	  <a href="#home">Live View</a>
	  <div class="subnav">
		    <button class="subnavbtn">Employee List <i class="fa fa-caret-down"></i></button>
		    <div class="subnav-content">
		      <a href="#company">Add new Employee</a>
		    </div>
		</div>
	</div>

	<p class="timeHead">Local Time: <span id="datetime"></span> 
		<script type="text/javascript">
			(function () {
	    		function checkTime(i) {
	        		return (i < 10) ? "0" + i : i;
	    		}

			    function startTime() {
			        var today = new Date(),
			        	d = checkTime(today.getDate()),
			        	y = checkTime(today.getUTCFullYear()),
			            t = today.toLocaleTimeString();
			        var month = new Array();
						  month[0] = "January";
						  month[1] = "February";
						  month[2] = "March";
						  month[3] = "April";
						  month[4] = "May";
						  month[5] = "June";
						  month[6] = "July";
						  month[7] = "August";
						  month[8] = "September";
						  month[9] = "October";
						  month[10] = "November";
						  month[11] = "December";
					var M = month[today.getMonth()];

					var day = new Array();
						  day[0] = "Sunday";
						  day[1] = "Monday";
						  day[2] = "Tuesday";
						  day[3] = "Wednesday";
						  day[4] = "Thursday";
						  day[5] = "Friday";
						  day[6] = "Saturday";		  
					var D = day[today.getDay()];
			        document.getElementById('datetime').innerHTML = D + ", " + M + " " + d +", "+ y +" "+ t;
			        t = setTimeout(function(){startTime()});
		        	//document.getElementById('alert').style.backgroundColor='#ffae42'; 
		    	}
	    		startTime();
			})();
		</script>
	</p>
	<div id="tdiv"></div>
	<script type="text/javascript">	
		//document.getElementById('alert').style.backgroundColor='#ffae42';
		setInterval(function(){
			$('#tdiv').load('testdb.php');
		}, 500);
		
		//Blinking number
		function blinker() {
			$('.blinking').fadeOut(500);
			$('.blinking').fadeIn(500);
		}
			setInterval(blinker, 500);
	</script>
</body>
</html>
