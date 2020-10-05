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
					<span class="navbar-brand mb-0 h1"><h3><b>My Bookings</b></h3></span>
				</div>
				<div>
					<a href="CinemaControllerServlet?command=LOGOUT" class="btn btn-outline-light" role="button">Logout</a>
				</div>
			</nav>
		</div>
			<hr>
			<br>
		<div class = "">
			<input class="btn btn-primary" type="button" value = "Cinemas" 
					onclick = "window.location.href = 'CinemaControllerServlet?command=SEE-CINEMAS'; return false"/>
		</div>			
		<div id = "container">
			<div id = "content">
				
				<table border = "1">
				
				<tr>
				
					<th>Title</th>
					<th>Start hour</th>
					<th>End hour</th>
					<th>Booked seats</th>
					<th>Price</th>
					<th></th>
					
				</tr>
				
					<c:forEach var="tempBooking" items="<%= request.getAttribute(\"booking_list\") %>"> 
						
						<c:url var = "delete" value = "CinemaControllerServlet">
							<c:param name = "command" value = "DELETE-BOOKING"/>
							<c:param name = "id" value = "${tempBooking.booking.id }"/>
						</c:url>
				
						<tr>
							<td>${tempBooking.movie.title}</td>
							<td>${tempBooking.movie.startHour}</td>
							<td>${tempBooking.movie.endHour}</td>
							<td>${tempBooking.booking.selectedSeats}</td>
							<td>${tempBooking.movie.price * tempBooking.booking.selectedSeats}</td>
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