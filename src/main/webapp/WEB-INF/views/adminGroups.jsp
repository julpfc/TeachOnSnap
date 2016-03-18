<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Manage user groups --%>
<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="admin.groups.heading" bundle="${adminBundle}"/>			
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<div class="content container-fluid">		
		<div class="row">
			<div class="panel panel-default">
				<!-- Search -->
     			<div class="panel-heading">
     				<c:choose>
     					<c:when test="${not empty param['searchQuery']}">
     						<fmt:message key="admin.groups.search.result.heading" bundle="${adminBundle}"/>: <span class="label label-info">${param['searchQuery']}</span>     						    
     					</c:when>
     					<c:otherwise>     						
		     				<form action="" method="post" role="form">
				    			<div class="col-sm-4 col-sm-offset-8 input-group">
			      					<input type="text" class="form-control" name="searchQuery" placeholder="<fmt:message key="admin.groups.search.placeholder" bundle="${adminBundle}"/>"/>			      					
			      					<div class="input-group-btn">
			        					<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>        
			      					</div><!-- /btn-group -->
			    				</div><!-- /input-group -->
							</form>
     					</c:otherwise>
     				</c:choose>
				</div>
				<!-- Groups -->
				<div class="panel-body">
					<c:if test="${not empty param['searchQuery'] && empty groups}">
						<h2><fmt:message key="admin.groups.search.empty" bundle="${adminBundle}"/></h2>
					</c:if>
					<c:if test="${empty param['searchQuery'] && empty groups}">
						<h4><fmt:message key="admin.groups.empty" bundle="${adminBundle}"/></h4>
					</c:if>
					<c:if test="${not empty groups}">
	    				<div class="table-responsive">
							<table class="table table-hover table-striped">
	      						<thead>
	        						<tr>	          							
	          							<th><fmt:message key="admin.group.name" bundle="${adminBundle}"/></th>	          							
	          							<th><fmt:message key="admin.users" bundle="${adminBundle}"/></th>
	          							<th></th>
	        						</tr>
	      						</thead>
	      						<tbody>
		        					<c:forEach items="${groups}" var="group"> 		
		  								<tr onclick="window.location='${host}/admin/group/${group.id}'">			  								
			  								<td>${fn:escapeXml(group.groupName)}</td>
			  								<td><span class="badge">${fn:length(group.users)}</span></td>
		  									<td><a href="${host}/admin/group/${group.id}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.groups.edit" bundle="${adminBundle}"/>" class="glyphicon glyphicon-eye-open"></span></a></td>
		  								</tr>
									</c:forEach>	
	      						</tbody>
	    					</table>
						</div>		 	
					</c:if>
					<nav>
						<ul class="pager">
							<c:if test="${not empty prevPage && not empty groups}">
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
					<span class="pull-left">
	     				<a href="${host}/admin/broadcast/"><button class="btn btn-warning btn-sm" type="button"><span class="glyphicon glyphicon-bullhorn"></span> <fmt:message key="admin.group.broadcast.heading" bundle="${adminBundle}"/></button></a>	     			
	     			</span>
					 <c:if test="${empty param['searchQuery']}">
					 	<!-- Create group -->
					 	<form action="" method="post" role="form">
			    			<div class="col-sm-5 col-sm-offset-7 input-group violetButton">
					    		<input type="text" name="groupName" id="inputGroupName" class="form-control" placeholder="<fmt:message key="admin.group.name" bundle="${adminBundle}"/>" required>			      					
		      					<div class="input-group-btn">
		        					<button class="btn btn-primary" type="submit"><fmt:message key="admin.groups.new.group.create" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-save"></span></button>        
		      					</div><!-- /btn-group -->		      					
		    				</div><!-- /input-group -->
						</form>
     				</c:if>  
     				<c:if test="${not empty param['searchQuery']}"> &nbsp;</c:if>   			
     			</div>     				   
     			
   			</div>
		</div><!-- /.row -->
		<c:if test="${empty param['searchQuery']}">
			<div class="row">
				<nav>
					<ul class="pager">
						<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-left"></span>
						 <fmt:message key="pager.back"/></a></li>						 						
					</ul>
				</nav>	
			</div><!-- /.row -->
		</c:if>
	</div><!-- /.content -->		
	<c:import url="./import/footer.jsp"/>
	<!-- Javascript -->
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="${host}/resources/js/adminGroups.js"></script>
	<!-- /Javascript -->
</body>
</html>