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
			<h2>Movie List</h2>
		</div>
		<div id = "container">
			<div id = "content">
				<a href="CinemaControllerServlet?command=LOGOUT">Logout</a>
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
					
				</tr>
			
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
				
				</table>
			</div>
		</div>
	</div>

</body>
</html>