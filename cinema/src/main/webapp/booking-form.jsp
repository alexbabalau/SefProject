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
  				<span class="navbar-brand mb-0 h1"><h3><b>Book seats</b></h3></span>
			</nav>
		</div>
			<div id = "container">
				<div id = "content">
					<a href="CinemaControllerServlet?command=LOGOUT">Logout</a>
					<hr>
					<br><br>
					
					<form action="CinemaControllerServlet">
					
					<input type = "number" name = "selectedSeats" />
					
					<input type = "hidden" name = "command" value = "BOOK" />
					
					<input type = "hidden" name = "id" value = "${id}" />
					
					<input type = "submit" value = "Submit"/>
					
					</form>
				</div>
			</div>
		</div>

</body>
</html>