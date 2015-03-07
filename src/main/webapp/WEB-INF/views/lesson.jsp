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
					<c:if test="${lesson.idLessonMedia>0}">					
			     		<div class="lesson-video">		     		
			     			<c:set var="firstVideo" value="${medias[0]}"/>
			     			<video src="${firstVideo.URL}" id="lesson_video" controls="controls" poster="" height="auto" width="100%">
				     			<c:forEach items="${medias}" var="media">		
			       					<source src="${media.URL}" type="${media.mimetype}"/>							    							
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
					<ul class="pager pagerBottom">
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
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">
				<h3 class="comments-section">
					<span class="glyphicon glyphicon-comment"></span> 
					<fmt:message key="lesson.comment.heading" bundle="${lessonBundle}"/>
				</h3>
				<hr id="commentNewAnchor" />
				<div id="commentNew">			
					<div class="comment-new">
						<a data-toggle="collapse" href="#collapseNewComment" aria-expanded="${empty comments}" aria-controls="collapseNewComment">
	  					<fmt:message key="lesson.comment.new.heading" bundle="${lessonBundle}"/> <span class="caret"></span>
						</a>
					</div>
					<div class="collapse${not empty comments?'':' in'}" id="collapseNewComment">
	  					<form role="form" action="${lesson.commentURL}" method="post">
	  						<div class="form-group well">    					
					    		<p><textarea name="comment" class="form-control" rows="4"></textarea></p>				    	
	  							<p><button class="btn btn-primary form-control" type="submit"><span class="glyphicon glyphicon-send"></span>
								 <fmt:message key="lesson.comment.new.submit" bundle="${lessonBundle}"/></button></p>
	  						</div>
	  					</form>
					</div>
					<hr/>
				</div>
 				<c:choose>
					<c:when test="${not empty comments}">
						<c:forEach items="${comments}" var="comment">		
							<article id="comment-${comment.id}" class="comment${comment.response?' col-sm-11 col-sm-offset-1':''}">				
								<footer>
									<div class="comment-author">
									<span><img alt="avatar" src="http://www.gravatar.com/avatar/${comment.user.MD5}?s=48&d=identicon" width="48" height="48"></span>
									<cite>${comment.user.firstName}</cite> <span class="comment-author-says"><fmt:message key="lesson.comment.author.says" bundle="${lessonBundle}"/>:</span>	
									</div>
								</footer>
								<div class="comment-content">
									${comment.body}									
								</div>
								<div class="comment-meta">
									<a href="#"><time datetime="${comment.date}">
									<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${comment.date}"/></time></a>
									| <a onclick="return moveCommentForm('${comment.id}','<fmt:message key="lesson.comment.reply" bundle="${lessonBundle}"/>');"><fmt:message key="lesson.comment.reply" bundle="${lessonBundle}"/></a>	
								</div><!-- .comment-meta .commentmetadata -->								
							</article>												
						</c:forEach>              			
            		</c:when>
					<c:otherwise> 
						<cite><fmt:message key="lesson.comment.nocomments" bundle="${lessonBundle}"/></cite>
				    </c:otherwise>
        		</c:choose>							
			</div>				
		</div><!-- /.row -->      	
	</div><!-- /.container -->
	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
	<script src="/resources/js/lesson.js"></script>
</body>
</html>