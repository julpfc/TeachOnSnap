<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/follow.css"/>"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="admin.group.follow.heading" bundle="${adminBundle}"/>			
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
		        			<c:forEach items="${users}" var="profile"> 		
		  						<tr>
			  						<td>${fn:escapeXml(profile.fullName)}</td>
		  							<td><a href="/admin/user/${profile.id}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.show.user" bundle="${adminBundle}"/>" class="glyphicon glyphicon-eye-open"></span></a></td>
		  							<td><a onclick="confirm('/admin/group/follow/author/?unfollowAuthor=${profile.id}','admin.group.follow.author.unfollow.confirm');"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.follow.author.unfollow" bundle="${adminBundle}"/>" class="glyphicon glyphicon-remove"></span></a></td>	  							  							
		  						</tr>
							</c:forEach>	
	      					</tbody>
	    				</table>
					</div>	
					</c:if>
					
				</div>
				<div class="panel-footer violetButton">			    			
					<a href="/admin/group/follow/author/${group.id}"><button class="btn btn-primary btn-sm pull-right" type="button"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="admin.group.follow.add.author" bundle="${adminBundle}"/></button></a>		    						
		    		&nbsp;		    								
     			</div>     
     		</div>     		
     	</div><!-- /.row -->
     	<div class="row">		
			<div class="panel panel-default">
     			<div class="panel-heading">
			 		<label><fmt:message key="admin.group.follow.tag" bundle="${adminBundle}"/>:</label>
				</div>
				<div class="panel-body">
	    			
					<c:if test="${empty tags}">
						<h4><fmt:message key="admin.group.follow.empty.tag" bundle="${adminBundle}"/></h4>
					</c:if>
					<c:if test="${not empty tags}">					
						<h2>					
            			<c:forEach items="${tags}" var="tag">
            				<a onclick="confirm('/admin/group/follow/tag/?unfollowTag=${tag.id}','admin.group.follow.tag.unfollow.confirm');">
            					<span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.follow.tag.unfollow" bundle="${adminBundle}"/>" class="label label-info">${fn:escapeXml(tag.tag)}</span>            					
            				</a>  							
			            </c:forEach>
			            </h2>
					</c:if>
					
				</div>
				<div class="panel-footer violetButton">			    			
					<a href="/admin/group/follow/tag/${group.id}"><button class="btn btn-primary btn-sm pull-right" type="button"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="admin.group.follow.add.tag" bundle="${adminBundle}"/></button></a>		    						
		    		&nbsp;		    								
     			</div>     
     		</div>     		
     	</div><!-- /.row -->      	
		<div class="row">
			<nav>
				<ul class="pager">
					<li><a href="${backPage}"><span class="glyphicon glyphicon-chevron-left"></span>
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
		msg['admin.group.follow.tag.unfollow.confirm'] = "<fmt:message key="admin.group.follow.tag.unfollow.confirm" bundle="${adminBundle}"/>";
		//-->
	</script>
	<script src="/resources/js/adminGroup.js"></script>	
	<script src="/resources/js/confirm.js"></script>	
	
</body>
</html>