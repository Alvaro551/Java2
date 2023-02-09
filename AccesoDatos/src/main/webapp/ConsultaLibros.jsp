<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>TiendaLibros</title>
</head>
<body>
	<h2>Tienda Libros</h2>
	<form method="post" action="Consulta">
		<b>Elige un autor:</b> <input type="checkbox" name="autor"
			value="Alvaro Garcia">Alvaro Garcia <input type="checkbox"
			name="autor" value="Aleksa Vukotic">Aleksa Vukotic <input
			type="checkbox" name="autor" value="Giulio Zambon">Giulio
		Zambon <input type="submit" value="Buscar">
	</form>
</body>
</html>