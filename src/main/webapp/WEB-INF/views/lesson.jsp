<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<title>Title lesson</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<h2>Lesson</h2>
	<table>
		<tr><th>id</th><th>title</th><th>Author</th><th>Language</th><th>date</th><th>text</th></tr>
		<tr><td>${lesson.id}</td><td>${lesson.title}</td><td>${lesson.author.fullName}</td>
		<td>${lesson.language.language}</td><td>${lesson.date}</td>
		<c:if test="${not empty lesson.text}"><td>${lesson.text}</td></c:if>
		</tr>		
	</table>
	
	<h2>Sources</h2>
	<table>
		<tr><th>id</th><th>desc</th></tr>
		<c:forEach items="${sourceLinks}" var="link">		
			<tr>
				<td><a href="${link.URL}">${link.id}</a></td><td>${link.desc}</td>
			</tr>		
		</c:forEach>
	</table>
	
	<h2>Linked Lessons</h2>
	<table>
		<tr><th>id</th><th>title</th><th>idUser</th><th>idLanguage</th><th>date</th><th>text</th></tr>
		<c:forEach items="${linkedLessons}" var="linkedlesson">		
		<tr>
			<td><a href="${linkedlesson.URL}">${linkedlesson.id}</a></td><td>${linkedlesson.title}</td>
			<td>${linkedlesson.author.fullName}</td><td>${linkedlesson.language.language}</td><td>${linkedlesson.date}</td>
		</tr>		
	</c:forEach>
	</table>
	
	<h2>More info</h2>
	<table>
		<tr><th>id</th><th>desc</th></tr>
		<c:forEach items="${moreInfoLinks}" var="link">		
			<tr>
				<td><a href="${link.URL}">${link.id}</a></td><td>${link.desc}</td>
			</tr>		
		</c:forEach>
	</table>
	
	<h2>Tags</h2>
	<table>
		<c:forEach items="${tags}" var="tag">		
			<tr>
				<td><a href="${tag.URL}">${tag.tag}</a></td>
			</tr>		
		</c:forEach>
	</table>
	
	<h5>Fin de pagina</h5>
	    <c:import url="./import/footer.jsp"/>

	<c:import url="./import/js_bootstrap.jsp"/>
	
</body>
</html>