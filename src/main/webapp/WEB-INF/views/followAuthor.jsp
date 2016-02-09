<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.login" var="loginBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>
<%-- Follow author --%>
<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="admin.group.follow.author.heading" bundle="${adminBundle}"/>			
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
			      					<input type="text" class="form-control" name="searchQuery" placeholder="<fmt:message key="admin.search.author.placeholder" bundle="${adminBundle}"/>"/>
			      					<span class="input-group-addon">      
			         					<input id="emailRadio" type="radio" name="searchType" value="email" required checked="checked" />
			        					<label for="emailRadio"><span data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="admin.users.search.tooltip.mail" bundle="${adminBundle}"/>" class="glyphicon glyphicon-envelope"></span></label>
			       						<input id="fullNameRadio" type="radio" name="searchType" value="name" required />
			        					<label for="fullNameRadio"><span data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="admin.users.search.tooltip.name" bundle="${adminBundle}"/>" class="glyphicon glyphicon-user"></span></label>
			      					</span>
			      					<div class="input-group-btn">
			        					<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>        
			      					</div><!-- /btn-group -->
			    				</div><!-- /input-group -->
							</form>							     				
     					</c:otherwise>
     				</c:choose>
				</div>
				<!-- Authors -->
				<div class="panel-body">
					<c:if test="${empty authors}">
						<h2><fmt:message key="admin.group.follow.author.empty" bundle="${adminBundle}"/></h2>
					</c:if>
					<c:if test="${not empty authors}">
	    			<div class="table-responsive">
						<table class="table table-striped table-hover">
	      					<thead>
	        					<tr>	          						
	          						<th><fmt:message key="admin.user.author" bundle="${adminBundle}"/></th>
	          						<th><fmt:message key="login.form.email" bundle="${loginBundle}"/></th>
	          						<th></th>          						
	        					</tr>
	      					</thead>
	      					<tbody>
		        			<c:forEach items="${authors}" var="profile"> 		
		  						<tr class="${profile.followed?'success':''}">			  						
			  						<td>${fn:escapeXml(profile.fullName)}</td>
		  							<td>${profile.email}</td>
		  							<td>
		  								<c:choose>
		  								<c:when test="${!profile.followed}">
		  									<a onclick="confirm('?followAuthor=${profile.id}','admin.group.follow.author.follow.confirm');"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.follow.author.follow" bundle="${adminBundle}"/>" class="glyphicon glyphicon-plus"></span></a>
		  								</c:when>
		  								<c:otherwise>
		  									<a onclick="confirm('?unfollowAuthor=${profile.id}','admin.group.follow.author.unfollow.confirm');"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.follow.author.unfollow" bundle="${adminBundle}"/>" class="glyphicon glyphicon-minus"></span></a>
		  								</c:otherwise>
		  								</c:choose>
		  							</td>	  							  							
		  						</tr>
							</c:forEach>	
	      					</tbody>
	    				</table>
					</div>		 	
					</c:if>
					<nav>
						<ul class="pager">
							<c:if test="${not empty prevPage && not empty authors}">
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
		msg['admin.group.follow.author.follow.confirm'] = 	"<fmt:message key="admin.group.follow.author.follow.confirm" bundle="${adminBundle}"/>";
		msg['admin.group.follow.author.unfollow.confirm'] = "<fmt:message key="admin.group.follow.author.unfollow.confirm" bundle="${adminBundle}"/>";
		//-->
	</script>
	<script src="${host}/resources/js/followAuthor.js"></script>	
	<script src="${host}/resources/js/confirm.js"></script>	
	<!-- /Javascript -->
</body>
</html>