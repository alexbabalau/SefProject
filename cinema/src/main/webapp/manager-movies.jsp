<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>

<head>
	<link type="text/css" rel="stylesheet" href="css/styles.css" >
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>

<body>
	<div id = "wrapper">
		<div>
			<nav class="navbar navbar-expand-sm bg-primary justify-content-between">
				<div>
					<span class="navbar-brand mb-0 h1"><h3><b>Cinema Movies</b></h3></span>
				</div>
				<div>
					<a href="CinemaControllerServlet?command=LOGOUT" class="btn btn-outline-light" role="button">Logout</a>
				</div>
			</nav>
		</div>
		<div id = "container">
			<div id = "content">
				<br>
				
				<input class="btn btn-outline-primary" type="button" value = "View Bookings" 
					onclick = "window.location.href = 'CinemaControllerServlet?command=MANAGER-BOOKING'; return false"/>
				<br><br>
				
				<input class="btn btn-outline-primary" type="button" value = "Add new movie" 
					onclick = "window.location.href = 'movie-form.html'; return false"/>
					
				<br>
				
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
			</div>
		</div>
	</div>
</body>
</html>