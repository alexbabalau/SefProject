<html>
<body>

<h1>Update Movie</h1>
<hr>

<br><br>

<form action="CinemaControllerServlet">

	Title : <input type = "text" name = "title" value = "${movie.title}" />
	
	<br/><br/>
	
	Start hour : <input type = "number" name = "startHour" value = "${movie.startHour}"/>
	
	<br/><br/>
	
	End hour : <input type = "number" name = "endHour" value = "${movie.endHour}"/>
	
	<br/><br/>
	
	Seats number : <input type = "number" name = "freeSeats" value = "${movie.freeSeats}"/>
	
	<br/><br/>
	
	One ticket price : <input type = "number" name = "price" value = "${movie.price}"/>
	
	<br/><br/>
	
	<input type = "hidden" name = "command" value = "ADDMOVIE" />
	
	<input type = "submit" value = "Submit"/>
</form>

</body>
</html>