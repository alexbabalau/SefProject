<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>

<head>
	<link type="text/css" rel="stylesheet" href="css/styles.css" >
</head>

<body>
	<div id = "wrapper">
		<div id = "header">	
			<h2>Bookings</h2>
		</div>
		<div id = "container">
			<div id = "content">
				<br>
				<c:url var = "logout" value = "CinemaControllerServlet">
					<c:param name="command" value = "LOGOUT"/>
				</c:url>
				<a href = "${logout}">Logout</a>
				<hr>
				
				<input class = "button" type = "button" value = "See Movies" 
					onclick = "window.location.href = 'CinemaControllerServlet?command=HOME'; return false"/>
				
				<br><br>
				
				<table border = "1">
				
				<tr>
					<th>Name</th>
					<th>Title</th>
					<th>Start hour</th>
					<th>End hour</th>
					<th>Booked seats</th>
					<th>Price</th>
					
				</tr>
				
					<c:forEach var="tempMovieBookingAndUser" items="<%= request.getAttribute(\"movie_booking_and_user_list\") %>"> 
				
						<tr>
							<td>${tempMovieBookingAndUser.user.name}
							<td>${tempMovieBookingAndUser.movie.title}</td>
							<td>${tempMovieBookingAndUser.movie.startHour}</td>
							<td>${tempMovieBookingAndUser.movie.endHour}</td>
							<td>${tempMovieBookingAndUser.booking.selectedSeats}</td>
							<td>${tempMovieBookingAndUser.movie.price * tempMovieBookingAndUser.booking.selectedSeats}</td>
						</tr>
				
					</c:forEach>
					
					<br><br>
				
				</table>
			</div>
		</div>
	</div>
</body>
</html>