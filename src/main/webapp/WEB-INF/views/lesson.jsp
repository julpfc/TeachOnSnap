<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/lesson.css"/>"/>
<title>TeachOnSnap - ${lesson.title}</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">
		<div>
       		<h2 class="lesson-title">${lesson.title}</h2>       		 	
			<p class="lesson-meta">
				<c:if test="${userLang.id != lesson.language.id}">
     				<img alt="${lesson.language.language}" src="/resources/img/ico/flag_${lesson.language.language}.jpg"/>
     			</c:if>	            			 
      			<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${lesson.date}"/>
      			 by <a href="${lesson.author.URL}">${lesson.author.fullName}</a>
   			</p> 
       	</div>
		<div class="row">
			<div class="col-sm-7">
				<div>
	     			<div class="lesson-video">
<!-- 	     				<video src="/resources/video/bunny.webm" id="my_video" controls="controls" poster="" height="auto" width="100%"><source src="/resources/video/bunny.webm" type='video/webm; codecs="vp8, vorbis"'></video>
 -->	     				<video src="/resources/video/epi.mp4" id="lesson_video" controls="controls" poster="" height="auto" width="100%"><source src="/resources/video/epi.mp4" type='video/mp4; codecs="h264"'></video>
	     			</div>          		
    				<c:if test="${not empty lesson.text}"><blockquote><p>${lesson.text}</p></blockquote></c:if>	             	
	          	
	          	
	          		<span class="label label-default"><span class="glyphicon glyphicon-tags"></span></span>
	          		<c:forEach items="${tags}" var="tag">		
						<span class="label label-default"><a href="${tag.URL}">${tag.tag}</a></span>									
					</c:forEach>
				</div>
				<nav>
					<ul class="pager">
						<li><a href="/"><span class="glyphicon glyphicon-home"></span> Home</a></li>						
					</ul>
				</nav>	
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
        		<c:if test="${lesson.idLessonTest>0}">
						<div class="sidebar">
							<h4>Test de autoevaluación</h4>
							 <a href="${lesson.testURL}"><button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"></span> Comenzar test</button></a>
						</div>
				</c:if>
          		<c:if test="${not empty sourceLinks}">
	          		<div class="sidebar">
	               		<h4>Sources</h4>
	            		<ol class="list-unstyled">
	            			<c:forEach items="${sourceLinks}" var="link">		
								<li><a href="${link.URL}">${link.desc}</a></li>									
							</c:forEach>              			
	            		</ol>
	          		</div>
	          	</c:if>
          		<c:if test="${not empty moreInfoLinks}">
	          		<div class="sidebar">
	               		<h4>More info</h4>
	            		<ol class="list-unstyled">
	            			<c:forEach items="${moreInfoLinks}" var="link">		
								<li><a href="${link.URL}">${link.desc}</a></li>									
							</c:forEach>              			
	            		</ol>
	          		</div>
	          	</c:if>
	          	<c:if test="${not empty linkedLessons}">
		          	<div class="sidebar"> 	
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