<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>
<body>
	<h1>Movies</h1>
	
	<hr>
	
	<input type = "button" value = "View Bookings" 
		onclick = "window.location.href = 'CinemaControllerServlet?command=MANAGER-BOOKING'; return false"/>
	<br><br>
	
	<input type = "button" value = "Add new movie" 
		onclick = "window.location.href = 'movie-form.html'; return false"/>
		
	<br><br>
	
	<table border = "1">
	
	<tr>
	
		<th>Title</th>
		<th>Start hour</th>
		<th>End hour</th>
		<th>Free seats</th>
		<th>Ticket price</th>
		<th></th>
		<th></th>
		
	</tr>
	
		<c:forEach var="tempMovie" items="<%= request.getAttribute(\"movie_list\") %>"> 
		
			<c:url var = "update" value = "CinemaControllerServlet">
				<c:param name = "command" value = "UPDATE-MOVIE"/>
				<c:param name = "id" value = "${tempMovie.id }"/>
			</c:url>
			
			<c:url var = "delete" value = "CinemaControllerServlet">
				<c:param name = "command" value = "DELETE-MOVIE"/>
				<c:param name = "id" value = "${tempMovie.id }"/>
			</c:url>
	
			<tr>
				<td>${tempMovie.title}</td>
				<td>${tempMovie.startHour}</td>
				<td>${tempMovie.endHour}</td>
				<td>${tempMovie.freeSeats}</td>
				<td>${tempMovie.price}</td>
				<td> <a href = "${update}">Update</a> </td>
				<td> <a href = "${delete}">Delete</a> </td>
			</tr>
	
		</c:forEach>
		
		<br><br>
	
	</table>

</body>
</html>