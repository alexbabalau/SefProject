<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>
<body>
	<h1>Bookings</h1>
	
	<hr>
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

</body>
</html>