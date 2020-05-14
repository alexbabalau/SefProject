<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>
<body>
	
	<h1>Book seats</h1>
	<a href="CinemaControllerServlet?command=LOGOUT">Logout</a>
	<hr>
	<br><br>
	
	<form action="CinemaControllerServlet">
	
	<input type = "number" name = "selectedSeats" />
	
	<input type = "hidden" name = "command" value = "BOOK" />
	
	<input type = "hidden" name = "id" value = "${id}" />
	
	<input type = "submit" value = "Submit"/>
	
	</form>

</body>
</html>