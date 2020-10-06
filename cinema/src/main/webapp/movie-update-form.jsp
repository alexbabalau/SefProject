<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>

<head>
	<link type="text/css" rel="stylesheet" href="css/styles.css" >
	<link type="text/css" rel="stylesheet" href="css/buttonsstyle.css" >
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>

<body>
	<div id = "wrapper">
		<div>
			<nav class="navbar navbar-dark bg-primary">
  				<span class="navbar-brand mb-0 h1"><h3><b>Update Movie</b></h3></span>
			</nav>
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
					
					<input class="btn btn-outline-primary" type = "submit" value = "Submit"/>
				</form>
			</div>
		</div>
	</div>

</body>
</html>