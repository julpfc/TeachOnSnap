<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.userprofile" var="profBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/follow.css"/>"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="user.follow.heading" bundle="${profBundle}"/>
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<c:import url="./import/confirm.jsp"/>
	<div class="content container-fluid">		
		<div class="row">		
			<div class="panel panel-default">
     			<div class="panel-heading">
			 		<label><fmt:message key="admin.group.follow.author" bundle="${adminBundle}"/>:</label>
				</div>
				<div class="panel-body">	    			
					<c:if test="${empty users}">
						<h4><fmt:message key="admin.group.follow.empty.author" bundle="${adminBundle}"/></h4>
					</c:if>
					<c:if test="${not empty users}">	    			
	    			<div class="table-responsive">
						<table class="table table-hover table-striped">	      					
	      					<tbody>
		        			<c:forEach items="${users}" var="author"> 		
		  						<tr>
			  						<td>${fn:escapeXml(author.fullName)}</td>
		  							<td>
		  								<c:choose>
											<c:when test="${user.admin}">
		  										<a href="${host}/admin/user/${author.id}">
		  										<span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.show.user" bundle="${adminBundle}"/>" class="glyphicon glyphicon-eye-open"></span></a>
											</c:when>
											<c:otherwise>
		  										<a href="${host}/author/${author.URIName}">
		  										<span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.show.user" bundle="${adminBundle}"/>" class="glyphicon glyphicon-eye-open"></span></a>
											</c:otherwise>		  								
		  								</c:choose>
		  								</td>
		  							<td><a onclick="confirm('${host}/follow/author/${profile.id}?unfollowAuthor=${author.id}','admin.group.follow.author.unfollow.confirm');"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.follow.author.unfollow" bundle="${adminBundle}"/>" class="glyphicon glyphicon-remove"></span></a></td>	  							  							
		  						</tr>
							</c:forEach>	
	      					</tbody>
	    				</table>
					</div>	
					</c:if>
					
				</div>
				<div class="panel-footer violetButton">			    			
					<a href="${host}/follow/author/${profile.id}"><button class="btn btn-primary btn-xs pull-right" type="button"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="admin.group.follow.add.author" bundle="${adminBundle}"/></button></a>		    						
		    		&nbsp;		    								
     			</div>     
     		</div>     		
     	</div><!-- /.row -->
     	<div class="row">		
			<div class="panel panel-default">
     			<div class="panel-heading">
			 		<label><fmt:message key="user.follow.lesson" bundle="${profBundle}"/>:</label>
				</div>
				<div class="panel-body">	    			
					<c:if test="${empty lessons}">
						<h4><fmt:message key="user.follow.empty.lesson" bundle="${profBundle}"/></h4>
					</c:if>
					<c:if test="${not empty lessons}">	    			
	    			<div class="table-responsive">
						<table class="table table-hover table-striped">	      					
	      					<tbody>
		        			<c:forEach items="${lessons}" var="lesson"> 		
		  						<tr>
			  						<td>${fn:escapeXml(lesson.title)}</td>
			  						<td>${fn:escapeXml(lesson.author.fullName)}</td>
		  							<td>
  										<a href="${lesson.URL}">
  										<span data-toggle="tooltip" data-placement="top" title="<fmt:message key="user.follow.show.lesson" bundle="${profBundle}"/>" class="glyphicon glyphicon-eye-open"></span></a>
									</td>
		  							<td><a onclick="confirm('${host}/follow/lesson/${profile.id}?unfollowLesson=${lesson.id}','user.follow.lesson.unfollow.confirm');"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="user.follow.lesson.unfollow" bundle="${profBundle}"/>" class="glyphicon glyphicon-remove"></span></a></td>	  							  							
		  						</tr>
							</c:forEach>	
	      					</tbody>
	    				</table>
					</div>	
					</c:if>
					
				</div>
				<div class="panel-footer violetButton">			    			
					<a href="${host}/last/"><button class="btn btn-primary btn-xs pull-right" type="button"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="user.follow.add.lesson" bundle="${profBundle}"/></button></a>		    						
		    		&nbsp;		    								
     			</div>     
     		</div>     		
     	</div><!-- /.row -->   	
		<div class="row">
			<nav>
				<ul class="pager">
					<li><a href="${lastPage}"><span class="glyphicon glyphicon-chevron-left"></span>
					 <fmt:message key="pager.back"/></a></li>						 						
				</ul>
			</nav>	
		</div><!-- /.row -->
	 </div><!-- /.container -->		
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
	
	<script type="text/javascript">
		<!--	    
		var msg = {};
		msg['admin.group.follow.author.unfollow.confirm'] = "<fmt:message key="admin.group.follow.author.unfollow.confirm" bundle="${adminBundle}"/>";
		msg['user.follow.lesson.unfollow.confirm'] = 		"<fmt:message key="user.follow.lesson.unfollow.confirm" bundle="${profBundle}"/>";
		//-->
	</script>
	<script src="${host}/resources/js/adminGroup.js"></script>	
	<script src="${host}/resources/js/confirm.js"></script>	
	
</body>
</html>