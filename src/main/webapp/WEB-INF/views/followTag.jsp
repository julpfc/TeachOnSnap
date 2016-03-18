<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="i18n.views.login" var="loginBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Follow tag --%>
<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/follow.css"/>"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="admin.group.follow.tag.heading" bundle="${adminBundle}"/>			
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>
	<c:import url="./import/confirm.jsp"/>	
	<div class="content container-fluid">		
		<div class="row">
			<div class="panel panel-default">
				<!-- Search -->
     			<div class="panel-heading">
     				<c:choose>
     					<c:when test="${not empty param['searchQuery']}">
     						<fmt:message key="admin.users.search.result.heading" bundle="${adminBundle}"/>: <span class="label label-info">${param['searchQuery']}</span>     						    
     					</c:when>
     					<c:otherwise>     						
		     				<form action="" method="post" role="form">
				    			<div class="col-sm-4 col-sm-offset-8 input-group">
			      					<input type="text" class="form-control" name="searchQuery" placeholder="<fmt:message key="admin.search.tag.placeholder" bundle="${adminBundle}"/>"/>			      					
			      					<div class="input-group-btn">
			        					<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>        
			      					</div><!-- /btn-group -->
			    				</div><!-- /input-group -->
							</form>							     				
     					</c:otherwise>
     				</c:choose>
				</div>
				<!-- Tags -->
				<div class="panel-body">
					<c:if test="${empty followTags}">
						<h2><fmt:message key="admin.group.follow.tag.empty" bundle="${adminBundle}"/></h2>
					</c:if>
					<c:if test="${not empty followTags}">
						<h2>					
            			<c:forEach items="${followTags}" var="tag">
            				<c:choose>
		  						<c:when test="${!tag.followed}">
		  							<a class="tag" onclick="confirm('?followTag=${tag.id}','admin.group.follow.tag.follow.confirm');"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.follow.tag.follow" bundle="${adminBundle}"/>" class="label label-info">${fn:escapeXml(tag.tag)}</span></a>
  								</c:when>
  								<c:otherwise>
  									<a class="tag" onclick="confirm('?unfollowTag=${tag.id}','admin.group.follow.tag.unfollow.confirm');"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.follow.tag.unfollow" bundle="${adminBundle}"/>" class="label label-warning">${fn:escapeXml(tag.tag)}</span></a>
  								</c:otherwise>
							</c:choose>
			            </c:forEach>
			            </h2>
					</c:if>
					<nav>
						<ul class="pager">
							<c:if test="${not empty prevPage && not empty followTags}">
								<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-left"></span>
								 <fmt:message key="pager.previous"/></a></li>
							</c:if>
							<c:if test="${not empty nextPage}">
								<li><a href="${nextPage}"><span class="glyphicon glyphicon-chevron-right"></span>
								 <fmt:message key="pager.next"/></a></li>
							</c:if>						
						</ul>
					</nav>
				</div>
				<div class="panel-footer">
					<nav>
						<ul class="pager">
							<li><a href="${backPage}"><span class="glyphicon glyphicon-chevron-left"></span>
					 		<fmt:message key="pager.back"/></a></li>						 						
						</ul>
					</nav>	
     			</div>
   			</div>
		</div><!-- /.row -->
	</div><!-- /.content -->
	<c:import url="./import/footer.jsp"/>
	<!-- Javascript -->
	<c:import url="./import/js_bootstrap.jsp"/>
	<script type="text/javascript">
		<!--	    
		var msg = {};
		msg['admin.group.follow.tag.follow.confirm'] = 	"<fmt:message key="admin.group.follow.tag.follow.confirm" bundle="${adminBundle}"/>";
		msg['admin.group.follow.tag.unfollow.confirm'] = "<fmt:message key="admin.group.follow.tag.unfollow.confirm" bundle="${adminBundle}"/>";
		//-->
	</script>
	<script src="${host}/resources/js/followTag.js"></script>	
	<script src="${host}/resources/js/confirm.js"></script>
	<!-- /Javascript -->	
</body>
</html>