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
	            			<c:if test="${userLang.id != lesson.language.id}">
	            				<img alt="${lesson.language.language}" src="/resources/img/ico/flag_${lesson.language.language}.jpg"/>
	            			</c:if>	            			 
	            			<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${lesson.date}"/>
	            			 by <a href="${lesson.author.URL}">${lesson.author.fullName}</a>
            			</p>
	            		<p class="lesson-addons">
	            			<%//TODO Controlar si tiene video %>
							<span class="glyphicon glyphicon-facetime-video"></span> video
							<c:if test="${lesson.idLessonTest>0}">
								<span class="glyphicon glyphicon-edit"></span> test
							</c:if>
						</p>                    		
						<c:if test="${not empty lesson.text}"><blockquote><p>${lesson.text}</p></blockquote></c:if>
			            <nav class="col-sm-offset-8"><ul class="pager"><li>
							<a href="${lesson.URL}"><span class="glyphicon glyphicon-book"></span> Learn more</a></li></ul>
						</nav>
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