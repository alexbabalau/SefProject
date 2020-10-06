<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>

<head>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
	<link type="text/css" rel="stylesheet" href="css/styles.css" >
	<link type="text/css" rel="stylesheet" href="css/buttonsstyle.css" >
</head>

<body>
	<div id = "wrapper">
		<div>
			<nav class="navbar navbar-expand-sm bg-primary justify-content-between">
				<div>
					<span class="navbar-brand mb-0 h1 text-light"><h3><b>Movies</b></h3></span>
				</div>
				<div>
					<a href="CinemaControllerServlet?command=LOGOUT" class="btn btn-outline-light" role="button">Logout</a>
				</div>
			</nav>
		</div>
		<div id = "container">
			<div id = "content">
				<br><br>
				<div>
					<input class="btn btn-outline-primary" type="button" value = "Cinemas" 
					onclick = "window.location.href = 'CinemaControllerServlet?command=SEE-CINEMAS'; return false"/>
				</div>
					
				<br>
				
				<table class = "table table-striped table-primary">
				<thead>
					<tr>
						<th scope = "col">Title</th>
						<th scope = "col">Start hour</th>
						<th scope = "col">End hour</th>
						<th scope = "col">Free seats</th>
						<th scope = "col">Ticket price</th>
						<th scope = "col"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="tempMovie" items="<%= request.getAttribute(\"movie_list\") %>"> 
					
						<c:url var = "booking" value = "CinemaControllerServlet">
							<c:param name="command" value = "BOOK-MOVIE"/>
							<c:param name = "id" value = "${tempMovie.id }"/>
						</c:url>
				
						<tr>
							<td>${tempMovie.title}</td>
							<td>${tempMovie.startHour}</td>
							<td>${tempMovie.endHour}</td>
							<td>${tempMovie.freeSeats}</td>
							<td>${tempMovie.price}</td>
							<td> <a href = "${booking}">Book</a> </td>
						</tr>
				
					</c:forEach>
					
					<br><br>
				</tbody>
				</table>
			</div>
		</div>
	</div>

</body>
</html>