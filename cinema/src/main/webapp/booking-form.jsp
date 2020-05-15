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
				<h2>Book seats</h2>
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