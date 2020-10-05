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
					<span class="navbar-brand mb-0 h1"><h3><b>Cinemas</b></h3></span>
				</div>
				<div>
					<a href="CinemaControllerServlet?command=LOGOUT" class="btn btn-outline-light" role="button">Logout</a>
				</div>
			</nav>
		</div>
		<div id = "container">
			<div id = "content">
				<hr>
				<br><br>
				<div class = "row">
					<input class="btn btn-outline-dark" type="button" value = "My Bookings" onclick = "window.location.href = 'CinemaControllerServlet?command=SEE-BOOKINGS'; return false" />
				</div>
					
				<br><br>
				
				<p>Select a cinema</p>
				
				<div class = "row">
				
					<div class = "col-6">
				
					<form action="CinemaControllerServlet">
				
					<select class="custom-select" name = "cinema">
				
						<c:forEach var="tempManager" items="<%= request.getAttribute(\"manager_list\") %>">
							<option>${tempManager.cinema}</option>
						</c:forEach>
  					
					</select>
				
						<br><br>
				
							<input type = "hidden" name = "command" value = "SEE-MOVIES" />
				
							<input class="btn btn-outline-primary" type = "submit" value = "Submit"/>
				
					</form>
				
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>