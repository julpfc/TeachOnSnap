<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.lessons" var="lessonsBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.lesson" var="lessonBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html>
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<c:choose> 
		<c:when test="${not empty searchKeyword}">
			<title><fmt:message key="app.name"/> - <fmt:message key="search.by" bundle="${lessonsBundle}"/> ${searchType}: ${searchKeyword}</title>
		</c:when>
		<c:otherwise>
			<title><fmt:message key="app.name"/> - <fmt:message key="last.ones" bundle="${lessonsBundle}"/></title>
		</c:otherwise>
	</c:choose>
</head>
<body>
<c:import url="./import/nav.jsp"/>	
	
	<div class="content container-fluid">
		<div class="row">					
			<div class="col-sm-7">
				<c:forEach items="${lessons}" var="lesson">					
					<div>
	            		<h2 class="lesson-title"><a href="${lesson.draft?lesson.editURL:lesson.URL}">${lesson.title}</a></h2>
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
			            	<c:choose>
			            		<c:when test="${lesson.draft}">
									<a href="${lesson.editURL}"><fmt:message key="lesson.command.edit" bundle="${lessonBundle}"/> <span class="glyphicon glyphicon-edit"></span></a>
			            		</c:when>
			            		<c:otherwise>
									<a href="${lesson.URL}"><span class="glyphicon glyphicon-book"></span> <fmt:message key="lesson.more"/></a>			            	
			            		</c:otherwise>
			            	</c:choose>
							</li></ul>
						</nav>
		          	</div>
		          	<hr/>	          	
				</c:forEach>
				<c:if test="${empty lessons}">
					<h2><fmt:message key="lessons.empty" bundle="${lessonsBundle}"/></h2>
				</c:if>			
	   
				<nav>
					<ul class="pager">
						<c:if test="${not empty nextPage}">
							<li><a href="${nextPage}"><span class="glyphicon glyphicon-chevron-left"></span>
							 <fmt:message key="pager.previous"/></a></li>
						</c:if>
						<li><a href="/"><span class="glyphicon glyphicon-home"></span>
						 <fmt:message key="pager.home"/></a></li>
						<c:if test="${not empty prevPage && not empty lessons}">
							<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-right"></span>
							 <fmt:message key="pager.next"/></a></li>
						</c:if>
					</ul>
				</nav>
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
          		<c:if test="${not empty searchKeyword}">
	          		<div class="sidebar">
	            		<h4><fmt:message key="search.by" bundle="${lessonsBundle}"/> ${searchType}:</h4>
	            		<span class="label label-info">${searchKeyword}</span>	            		
	          		</div>        
          		</c:if> 
          		<c:if test="${not empty cloudTags}">
	          		<div class="sidebar tags">
	            		<h4><span class="glyphicon glyphicon-tags"></span> <fmt:message key="cloudtag.heading"/></h4>
	            		<ul class="tags">
	            		<c:forEach items="${cloudTags}" var="cloudTag">
	            			<li class="tags tag${cloudTag.weight}"><a href="${cloudTag.URL}">${cloudTag.tag}</a></li>
			            </c:forEach>
			            </ul>
	          		</div>  
          		</c:if>
          		<c:if test="${not empty authorCloudTags}">
	          		<div class="sidebar tags">
	            		<h4><span class="glyphicon glyphicon-tags"></span> <fmt:message key="cloudtag.author.heading"/></h4>
	            		<ul class="tags">
	            		<c:forEach items="${authorCloudTags}" var="cloudTag">
	            			<li class="tags tag${cloudTag.weight}"><a href="${cloudTag.URL}">${cloudTag.tag}</a></li>
			            </c:forEach>
			            </ul>
	          		</div>    
	          	</c:if>     	 		          		
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
    <c:import url="./import/footer.jsp"/>

	<c:import url="./import/js_bootstrap.jsp"/>

</body>
</html>