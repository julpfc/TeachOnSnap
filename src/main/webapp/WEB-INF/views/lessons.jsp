<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<title>TeachOnSnap - Búsqueda por ${searchType}: ${searchKeyword}</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>	
	
	<div class="content container-fluid">
		<div class="row">					
			<div class="col-sm-7">
				<c:forEach items="${lessons}" var="lesson">					
					<div>
	            		<h2 class="lesson-title"><a href="${lesson.URL}">${lesson.title}</a></h2>
	            		<p class="lesson-meta">
	            			<c:if test="${browserLang.id != lesson.language.id}">
	            				<img alt="${lesson.language.language}" src="/resources/img/ico/flag_${lesson.language.language}.jpg"/>
	            			</c:if>	            			 
	            			<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${lesson.date}"/>
	            			 by <a href="${lesson.author.URL}">${lesson.author.fullName}</a></p>            		
						<c:if test="${not empty lesson.text}"><blockquote><p>${lesson.text}</p></blockquote></c:if>
			            <a href="${lesson.URL}"><button class="btn btn-default col-sm-offset-8" type="button"><span class="glyphicon glyphicon-play"></span> Let's see</button></a>
		          	</div>
		          	<hr/>	          	
				</c:forEach>			
	   
				<nav>
					<ul class="pager">
						<li><a href="#">Older</a></li>
						<li><a href="#">Newer</a></li>
					</ul>
				</nav>
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
          		<div>
            		<h4>Búsqueda por ${searchType}:</h4>
            		<span class="label label-info">${searchKeyword}</span>
            		<h5>${searchResults} resultado(s)</h5>
          		</div>          		          		
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
    <c:import url="./import/footer.jsp"/>

	<c:import url="./import/js_bootstrap.jsp"/>

</body>
</html>