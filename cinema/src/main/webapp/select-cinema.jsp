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
				<h2>Cinema List</h2>
		</div>
		<div id = "container">
			<div id = "content">
				<br>
				<a href="CinemaControllerServlet?command=LOGOUT">Logout</a>
				<hr>
				<br><br>
				
				<input type = "button" value = "My Bookings" 
					onclick = "window.location.href = 'CinemaControllerServlet?command=SEE-BOOKINGS'; return false"/>
					
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
			</div>
		</div>
	</div>

</body>
</html>