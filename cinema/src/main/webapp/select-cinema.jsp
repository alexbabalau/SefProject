<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>
<body>
	<h1>Cinema List</h1>
	
	<hr>
	<br><br>
	
	<input type = "button" name = "command" value = "SEE-BOOKINGS" 
		onclick = "window.location.href = 'CinemaControllerServlet'; return false"/>
		
	<br><br>
	
	<form action="CinemaControllerServlet">
	
	<select name = "cinema" >
	
		<c:forEach var="tempManager" items="<%= request.getAttribute(\"manager_list\") %>">
			<option>${tempManager.cinema}</option>
		</c:forEach>
		
	</select>
	
	<input type = "hidden" name = "command" value = "SEE-MOVIES" />
	
	<input type = "submit" value = "Submit"/>
	
	</form>

</body>
</html>