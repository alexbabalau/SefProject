<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>

<head>
	<link type="text/css" rel="stylesheet" href="css/styles.css" >
	<link type="text/css" rel="stylesheet" href="css/buttonsstyle.css" >
</head>

<body>
	<div id = "wrapper">
		<div id = "header">
			<h2>Update Movie</h2>
		</div>
			<hr>
			
			<br><br>
		<div id = "container">
			<div id = "content">
				<form action="CinemaControllerServlet">
				
					Title : <input type = "text" name = "title" value = "${movie.title}" />
					
					<br/><br/>
					
					Start hour : <input type = "number" name = "startHour" value = "${movie.startHour}"/>
					
					<br/><br/>
					
					End hour : <input type = "number" name = "endHour" value = "${movie.endHour}"/>
					
					<br/><br/>
					
					Seats number : <input type = "number" name = "freeSeats" value = "${movie.freeSeats}"/>
					
					<br/><br/>
					
					One ticket price : <input type = "number" step = "0.01" name = "price" value = "${movie.price}"/>
					
					<br/><br/>
					
					<input type = "hidden" name = "command" value = "ADDMOVIEID" />
					<input type = "hidden" name = "id" value = "${movie.id}" />
					
					<input type = "submit" value = "Submit"/>
				</form>
			</div>
		</div>
	</div>

</body>
</html>