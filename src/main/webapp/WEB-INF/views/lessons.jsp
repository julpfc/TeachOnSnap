<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.lessons" var="lessonsBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.lesson" var="lessonBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.admin" var="adminBundle"/>
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
<c:if test="${not empty user}"><c:import url="./import/confirm.jsp"/></c:if>	
	<div class="content container-fluid">
		<div class="row">					
			<div class="col-sm-7">
				<c:forEach items="${lessons}" var="lesson" varStatus="loop">	
					<c:if test="${loop.last}"><c:set var="author" value="${lesson.author}"/></c:if>
					<div><c:set var="lessonID" value="[${lesson.id}]"/>
						<span class="author-avatar pull-left"><a href="${lesson.author.URL}"><img alt="avatar" src="https://www.gravatar.com/avatar/${lesson.author.MD5}?s=48&d=identicon" width="48" height="48"></a></span>
	            		<h2 class="lesson-title"><a href="${lesson.draft?lesson.editURL:lesson.URL}">${fn:escapeXml(lesson.title)}</a>${not empty user.lessonFollowed[lessonID]?' <span class="glyphicon glyphicon-star"></span>':''}</h2>
	            		<p class="lesson-meta">
	            			<c:if test="${userLang.id != lesson.language.id}">
	            				<img alt="${lesson.language.language}" src="/resources/img/ico/flag_${lesson.language.language}.jpg"/>
	            			</c:if>	            			 
	            			<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${lesson.date}"/>
	            			 <fmt:message key="lesson.meta.author.by"/><c:set var="authorID" value="[${lesson.author.id}]"/>
	            			 <a href="${lesson.author.URL}">${lesson.author.fullName}${not empty user.authorFollowed[authorID]?' <span class="glyphicon glyphicon-star"></span>':''}</a>
            			</p>
	            		<p class="lesson-addons">
	            			<c:choose>
			     				<c:when test="${lesson.mediaType == 'VIDEO'}">
									<span class="glyphicon glyphicon-film"></span> video
			     				</c:when>
			     				<c:when test="${lesson.mediaType == 'AUDIO'}">
			     					<span class="glyphicon glyphicon-volume-up"></span> audio
			     				</c:when>
			     				<c:when test="${lesson.mediaType == 'IMAGE'}">
			     					<span class='glyphicon glyphicon-picture'></span> img
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
	            		<c:if test="${searchType eq 'author' && not empty author}">
	            			<span class="author-avatar pull-left"><img alt="avatar" src="https://www.gravatar.com/avatar/${author.MD5}?s=48&d=identicon" width="48" height="48"></span>
	            		</c:if>
	            		<h3><span class="label label-info">${searchKeyword}</span></h3>	            		
	            		<c:if test="${not empty user && searchType eq 'author' && not empty author}">
	            			<c:choose>
	            			<c:when test="${not empty user.authorFollowed[authorID]}">	            				
	            				<a onclick="confirm('?unfollowAuthor=${author.id}','admin.group.follow.author.unfollow.confirm');"><button class="btn btn-xs btn-warning" type="button"><span class="glyphicon glyphicon-star"></span> <fmt:message key="admin.group.follow.author.unfollow" bundle="${adminBundle}"/></button></a>	            				
	            			</c:when>
	            			<c:otherwise>	            				
		            			<a class="violetButton" onclick="confirm('?followAuthor=${author.id}','admin.group.follow.author.follow.confirm');"><button class="btn btn-xs btn-primary" type="button"><span class="glyphicon glyphicon-star"></span> <fmt:message key="admin.group.follow.author.follow" bundle="${adminBundle}"/></button></a>
	            			</c:otherwise>
	            			</c:choose>
	            		</c:if>           		
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
	<c:if test="${not empty user}">
		<script type="text/javascript">
			<!--	    
			var msg = {};
			msg['admin.group.follow.author.follow.confirm'] = 	"<fmt:message key="admin.group.follow.author.follow.confirm" bundle="${adminBundle}"/>";
			msg['admin.group.follow.author.unfollow.confirm'] = "<fmt:message key="admin.group.follow.author.unfollow.confirm" bundle="${adminBundle}"/>";
			//-->
		</script>
			
		<script src="/resources/js/confirm.js"></script>
	</c:if>
</body>
</html>