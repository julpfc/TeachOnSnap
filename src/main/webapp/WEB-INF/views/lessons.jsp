<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Title lessons</title>
</head>
<body>
	<h1>Tag: ${tag}</h1>
	
	<h2>Lessons</h2>
	<table>
		<tr><th>id</th><th>title</th><th>idUser</th><th>idLanguage</th><th>date</th><th>texto</th></tr>
	<c:forEach items="${lessons}" var="lesson">		
		<tr>
			<td><a href="${lesson.URL}">${lesson.id}</a></td><td>${lesson.title}</td><td>${lesson.idUser}</td>
			<td>${lesson.idLanguage}</td><td>${lesson.date}</td>
		</tr>		
	</c:forEach>
	</table>
	<h5>Fin de pagina</h5>
</body>
</html>