<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>

<head>
	<link type="text/css" rel="stylesheet" href="css/styles.css" >
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>

<body>

	<div id="wrapper">
		<div>
			<nav class="navbar navbar-expand-sm bg-primary justify-content-between">
				<div>
					<span class="navbar-brand mb-0 h1 text-light"><h3><b>Admin Requests</b></h3></span>
				</div>
				<div>
					<a href="CinemaControllerServlet?command=LOGOUT" class="btn btn-outline-light" role="button">Logout</a>
				</div>
			</nav>
		</div>
		
		<div id="container">
			<div id = "content">
			
				<table class="table table-striped">
  					<thead>
    					<tr>
      						<th scope="col">Username</th>
      						<th scope="col">Cinema Name</th>
      						<th scope="col">Name</th>
      						<th scope="col">Phone Number</th>
      						<th scope="col">Email</th>
							<th scope="col"></th>
							<th scope="col"></th>
    					</tr>
  					</thead>
					<tbody>
					<c:forEach var="tempRequest" items="<%= request.getAttribute(\"manager_list\") %>"> 
					
						<c:url var = "acceptLink" value = "CinemaControllerServlet">
							<c:param name = "command" value = "ACCEPT"/>
							<c:param name = "username" value = "${tempRequest.manager.username }"/>
						</c:url>
						
						<c:url var = "denyLink" value = "CinemaControllerServlet">
							<c:param name = "command" value = "DENY"/>
							<c:param name = "username" value = "${tempRequest.manager.username }"/>
						</c:url>
				
						<tr>
							<td>${tempRequest.manager.username}</td>
							<td>${tempRequest.cinemaName}</td>
							<td>${tempRequest.manager.name}</td>
							<td>${tempRequest.manager.phone}</td>
							<td>${tempRequest.manager.email}</td>
							<td> <a href = "${acceptLink}">Accept</a> </td>
							<td> <a href = "${denyLink}">Deny</a> </td>
						</tr>
				
					</c:forEach>
					</tbody>
				</table>
			</div>	
		</div>
	</div>

</body>
</html>