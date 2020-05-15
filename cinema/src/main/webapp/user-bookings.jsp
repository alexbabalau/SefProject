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
			<h2>My Bookings</h2>
		</div>
			<hr>
			<br><br>
		<div id = "container">
			<div id = "content">
				<input type = "button" value = "Home" 
					onclick = "window.location.href = 'CinemaControllerServlet?command=HOME'; return false"/>
				<input type = "button" value = "Cinemas" 
					onclick = "window.location.href = 'CinemaControllerServlet?command=SEE-CINEMAS'; return false"/>
					
				<br><br>
				
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