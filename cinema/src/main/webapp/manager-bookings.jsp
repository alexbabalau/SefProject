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
					<span class="navbar-brand mb-0 h1"><h3><b>Manager Bookings</b></h3></span>
				</div>
				<div>
					<a href="CinemaControllerServlet?command=LOGOUT" class="btn btn-outline-light" role="button">Logout</a>
				</div>
			</nav>
		</div>
		
		<div id = "container">
			<div id = "content">
				<br>
				
				<input class="btn btn-outline-primary" type="button" value = "See Movies" 
					onclick = "window.location.href = 'CinemaControllerServlet?command=HOME'; return false"/>
				
				<br>
				
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