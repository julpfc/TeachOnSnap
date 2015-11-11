<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.home" var="homeBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html>
<head>
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/home.css"/>"/>
<title><fmt:message key="app.name"/> - <fmt:message key="pager.home"/></title> 
</head>
<body>
 	<c:import url="./import/nav.jsp"/>
      <div class="content container-fluid">      	
		<div class="home">		
			<div class="container-fluid"> 
		        <div class="col-sm-8">
		        	<h1>TeachOnSnap</h1>
		        	<p><fmt:message key="brand.subtitle" bundle="${homeBundle}"/></p>
		        </div>
		        <div class="col-sm-4">
		        	<h2><span class="glyphicon glyphicon-facetime-video"></span>
		        	<span class="glyphicon glyphicon-book"></span></h2>
		        </div>
		    </div>
	    </div>
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
	            			 <fmt:message key="lesson.meta.author.by"/> 
            			 	<a href="${lesson.author.URL}">${lesson.author.fullName}</a>
           			 	</p>
	            		<p class="lesson-addons">	            			
							<c:choose>
			     				<c:when test="${lesson.mediaType == 'VIDEO'}">
									<span class="glyphicon glyphicon-film"></span> video
			     				</c:when>
			     				<c:when test="${lesson.mediaType == 'AUDIO'}">
			     					<span class="glyphicon glyphicon-volume-up"></span> audio
			     				</c:when>				     								     			
			     			</c:choose>			     		
							<c:if test="${lesson.testAvailable}">
								<span class="glyphicon glyphicon-edit"></span> test
							</c:if>
						</p>            		
						<c:if test="${not empty lesson.text}"><blockquote><p>${lesson.text}</p></blockquote></c:if>
													            
			            <nav class="col-sm-offset-8"><ul class="pager"><li>
							<a href="${lesson.URL}"><span class="glyphicon glyphicon-book"></span>
							 <fmt:message key="lesson.more"/></a></li></ul>
						</nav>
		          	</div>
		          	<hr/>	          	
				</c:forEach>			
	   
				<nav><ul class="pager"><li>
					 <c:if test="${not empty nextPage}">
						<li><a href="${nextPage}"><span class="glyphicon glyphicon-chevron-left"></span>
							<fmt:message key="pager.previous"/></a></li>
					</c:if>
					</ul>
				</nav>
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
          		<div class="sidebar">
            		<h4>About</h4>
            		<p>Etiam porta <em>sem malesuada magna</em> mollis euismod. Cras mattis consectetur purus sit amet fermentum. Aenean lacinia bibendum nulla sed consectetur.</p>
          		</div>
          		<div class="sidebar tags">
            		<h4><span class="glyphicon glyphicon-tags"></span> <fmt:message key="cloudtag.heading"/></h4>
            		<ul class="tags">
            		<c:forEach items="${cloudTags}" var="cloudTag">
            			<li class="tags tag${cloudTag.weight}"><a href="${cloudTag.URL}">${cloudTag.tag}</a></li>
		            </c:forEach>
		            </ul>
          		</div>   
          		<div class="sidebar tags">
            		<h4><span class="glyphicon glyphicon-tags"></span> <fmt:message key="cloudtag.author.heading"/></h4>
            		<ul class="tags">
            		<c:forEach items="${authorCloudTags}" var="cloudTag">
            			<li class="tags tag${cloudTag.weight}"><a href="${cloudTag.URL}">${cloudTag.tag}</a></li>
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