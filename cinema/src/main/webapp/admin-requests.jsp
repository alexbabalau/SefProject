<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ page isELIgnored="false"%>
<html>
<body>
	<h1>Admin requests</h1>
	
	<a href="CinemaControllerServlet?command=LOGOUT">Logout</a>
	
	<hr>
	<br><br>
	
	<table border = "1">
	
	<tr>
	
		<th>Username</th>
		<th>Cinema Name</th>
		<th>Name</th>
		<th>Phone Number</th>
		<th>Email</th>
		<th></th>
		<th></th>
		
	</tr>
	
		<c:forEach var="tempManager" items="<%= request.getAttribute(\"manager_list\") %>"> 
		
			<c:url var = "acceptLink" value = "CinemaControllerServlet">
				<c:param name = "command" value = "ACCEPT"/>
				<c:param name = "username" value = "${tempManager.username }"/>
			</c:url>
			
			<c:url var = "denyLink" value = "CinemaControllerServlet">
				<c:param name = "command" value = "DENY"/>
				<c:param name = "username" value = "${tempManager.username }"/>
			</c:url>
	
			<tr>
				<td>${tempManager.username}</td>
				<td>${tempManager.cinema}</td>
				<td>${tempManager.name}</td>
				<td>${tempManager.phone}</td>
				<td>${tempManager.email}</td>
				<td> <a href = "${acceptLink}">Accept</a> </td>
				<td> <a href = "${denyLink}">Deny</a> </td>
			</tr>
	
		</c:forEach>
	
	</table>

</body>
</html>