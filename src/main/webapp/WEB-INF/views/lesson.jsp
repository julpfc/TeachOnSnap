<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<title>Title lesson</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<div class="container-fluid">
		<div>
       		<h2 class="lesson-title">${lesson.title}</h2>       		 	
       	</div>
		<div class="row">
			<div class="col-sm-7">
				<div>
					<p class="lesson-meta">
		       			<img alt="${lesson.language.language}" src="/resources/img/ico/flag_${lesson.language.language}.jpg"/>	            			 
		       			<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${lesson.date}"/>
		       			 by <a href="#">${lesson.author.fullName}</a>
	     			</p>           		
    				<c:if test="${not empty lesson.text}"><blockquote><p>${lesson.text}</p></blockquote></c:if>	             	
	          	
	          		<c:forEach items="${tags}" var="tag">		
						<span class="label label-default"><a href="${tag.URL}">${tag.tag}</a></span>									
					</c:forEach>		
				</div>
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
          		<c:if test="${not empty sourceLinks}">
	          		<div>
	               		<h4>Sources</h4>
	            		<ol class="list-unstyled">
	            			<c:forEach items="${sourceLinks}" var="link">		
								<li><a href="${link.URL}">${link.desc}</a></li>									
							</c:forEach>              			
	            		</ol>
	          		</div>
	          	</c:if>
          		<c:if test="${not empty moreInfoLinks}">
	          		<div>
	               		<h4>More info</h4>
	            		<ol class="list-unstyled">
	            			<c:forEach items="${moreInfoLinks}" var="link">		
								<li><a href="${link.URL}">${link.desc}</a></li>									
							</c:forEach>              			
	            		</ol>
	          		</div>
	          	</c:if>
	          	<c:if test="${not empty linkedLessons}">
		          	<div> 	
						<h4>Recomendado por el autor</h4>
						<ol class="list-unstyled">
	            			<c:forEach items="${linkedLessons}" var="linkedlesson">		
								<li><a href="${linkedlesson.URL}">${linkedlesson.title}</a></li>									
							</c:forEach>              			
	            		</ol>
					</div>
				</c:if>
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
	
</body>
</html>