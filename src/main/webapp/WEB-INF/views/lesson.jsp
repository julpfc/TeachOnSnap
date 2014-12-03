<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.lesson" var="lessonBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

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
      			 <fmt:message key="lesson.meta.author.by"/> <a href="${lesson.author.URL}">${lesson.author.fullName}</a>
   			</p>
       	</div>
		<div class="row">
			<div class="col-sm-7">
				<div>
					<c:if test="${lesson.idLessonVideo>0}">					
			     		<div class="lesson-video">		     		
			     			<c:set var="firstVideo" value="${videos[0]}"/>
			     			<video src="${firstVideo.URL}" id="lesson_video" controls="controls" poster="" height="auto" width="100%">
				     			<c:forEach items="${videos}" var="video">		
			       					<source src="${video.URL}" type='video/mp4'/>							    							
								</c:forEach>
							</video>   
			     		</div>         		
					</c:if>												
					
    				<c:if test="${not empty lesson.text}"><blockquote><p>${lesson.text}</p></blockquote></c:if>	             	
	          		          	
	          		<span class="label label-default"><span class="glyphicon glyphicon-tags"></span></span>
	          		<c:forEach items="${tags}" var="tag">		
						<span class="label label-default"><a href="${tag.URL}">${tag.tag}</a></span>									
					</c:forEach>
				</div>
				<nav>
					<ul class="pager">
						<li><a href="/"><span class="glyphicon glyphicon-home"></span>
						 <fmt:message key="pager.home"/></a>
						</li>						
					</ul>
				</nav>	
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
        		<c:if test="${lesson.author.id == user.id}">
						<div class="sidebar">
							<h4><fmt:message key="lesson.command.heading" bundle="${lessonBundle}"/></h4>
							<a href="${lesson.editURL}"><button class="btn btn-default" type="button">
							 	<fmt:message key="lesson.command.edit" bundle="${lessonBundle}"/>
							 	 <span class="glyphicon glyphicon-edit"></span></button>
						 	</a>
						</div>
				</c:if>
        		<c:if test="${lesson.idLessonTest>0}">
						<div class="sidebar">
							<h4><fmt:message key="lesson.test.heading" bundle="${lessonBundle}"/></h4>
							<a href="${lesson.testURL}"><button class="btn btn-success" type="button">
							 	<span class="glyphicon glyphicon-edit"></span>
							 	 <fmt:message key="lesson.test.start" bundle="${lessonBundle}"/></button>
						 	</a>
						</div>
				</c:if>
	          	<c:if test="${not empty linkedLessons}">
		          	<div class="sidebar"> 	
						<h4><fmt:message key="lesson.linkedLessons.heading" bundle="${lessonBundle}"/></h4>
						<ol class="list-unstyled">
	            			<c:forEach items="${linkedLessons}" var="linkedlesson">		
								<li><a href="${linkedlesson.URL}">${linkedlesson.title}</a></li>									
							</c:forEach>              			
	            		</ol>
					</div>
				</c:if>
          		<c:if test="${not empty moreInfoLinks}">
	          		<div class="sidebar">
	               		<h4><fmt:message key="lesson.moreInfo.heading" bundle="${lessonBundle}"/></h4>
	            		<ol class="list-unstyled">
	            			<c:forEach items="${moreInfoLinks}" var="link">		
								<li><a href="${link.URL}">${link.desc}</a></li>									
							</c:forEach>              			
	            		</ol>
	          		</div>
	          	</c:if>
          		<c:if test="${not empty sourceLinks}">
	          		<div class="sidebar">
	               		<h4><fmt:message key="lesson.source.heading" bundle="${lessonBundle}"/></h4>
	            		<ol class="list-unstyled">
	            			<c:forEach items="${sourceLinks}" var="link">		
								<li><a href="${link.URL}">${link.desc}</a></li>									
							</c:forEach>              			
	            		</ol>
	          		</div>
	          	</c:if>
	          	<div class="sidebar">
               		<h4><fmt:message key="lesson.sameAuthor.heading" bundle="${lessonBundle}"/></h4>
            		<p><a href="${lesson.author.URL}">${lesson.author.fullName}</a></p>
          		</div>
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
	
</body>
</html>