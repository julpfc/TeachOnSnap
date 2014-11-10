<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/home.css"/>"/>
<title>Teach On Snap - Home</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>

    <div class="container-fluid">

		<div> 
	        <h1>Teach On Snap</h1>
	        <p class="lead">Learn on snapshots!</p>
	    </div>
		<div class="row">
			<div class="col-sm-7">
				<c:forEach items="${lastLessons}" var="lesson">		
					<div>
	            		<h2 class="lesson-title"><a href="${lesson.URL}">${lesson.title}</a></h2>
	            		<p class="lesson-meta">
	            			<img alt="${lesson.language.language}" src="/resources/img/ico/flag_${lesson.language.language}.jpg"/>	            			 
	            			<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${lesson.date}"/>
	            			 by <a href="#">${lesson.author.fullName}</a></p>            		
	    				<c:if test="${not empty lesson.text}"><blockquote><p>${lesson.text}</p></blockquote></c:if>
		             	<a href="${lesson.URL}"><button class="btn btn-default col-sm-offset-8" type="button">More</button></a>
		          	</div>
		          	<hr/>	          	
				</c:forEach>			
	   
				<nav>
					<ul class="pager">
						<li><a href="#">Previous</a></li>
						<li><a href="#">Next</a></li>
					</ul>
				</nav>
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
          		<div>
            		<h4>About</h4>
            		<p>Etiam porta <em>sem malesuada magna</em> mollis euismod. Cras mattis consectetur purus sit amet fermentum. Aenean lacinia bibendum nulla sed consectetur.</p>
          		</div>
          		<div class="tags">
            		<h4>Tag cloud</h4>
            		<ul class="tags">
            		<c:forEach items="${cloudTags}" var="cloudTag">
            			<li class="tags tag${cloudTag.weight}"><a href="${cloudTag.tag.URL}">${cloudTag.tag.tag}</a></li>
		            </c:forEach>
		            </ul>
          		</div>          		
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
    <c:import url="./import/footer.jsp"/>

	<c:import url="./import/js_bootstrap.jsp"/>
</body>
</html>