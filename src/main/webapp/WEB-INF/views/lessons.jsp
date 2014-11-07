<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<title>Title lessons</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<h1>Tag: ${tag}</h1>
	
	<h2>Lessons</h2>
	<table>
		<tr><th>id</th><th>title</th><th>idUser</th><th>idLanguage</th><th>date</th><th>text</th></tr>
	<c:forEach items="${lessons}" var="lesson">		
		<tr>
			<td><a href="${lesson.URL}">${lesson.id}</a></td><td>${lesson.title}</td><td>${lesson.idUser}</td>
			<td>${lesson.idLanguage}</td><td>${lesson.date}</td>
			<c:if test="${not empty lesson.text}"><td>${lesson.text}</td></c:if>
		</tr>		
	</c:forEach>
	</table>
	<h5>Fin de pagina</h5>
    <c:import url="./import/footer.jsp"/>

	<c:import url="./import/js_bootstrap.jsp"/>

</body>
</html>