<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<body>
	<h1>Movies</h1>
	
	<hr>
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
				<td>${tempMovie.username}</td>
				<td>${tempMovie.cinema}</td>
				<td>${tempMovie.name}</td>
				<td>${tempMovie.phone}</td>
				<td>${tempMovie.email}</td>
				<td> <a href = "${update}">Update</a> </td>
				<td> <a href = "${delete}">Delete</a> </td>
			</tr>
	
		</c:forEach>
		
		<br><br>
		
		<c:url var = "addMovie" value = "CinemaControllerServlet">
			<c:param name = "command" value = "ADDMOVIE"/>
		</c:url>
		
		<a href = "${addMovie}">Add New Movie</a>
	
	</table>

</body>
</html>