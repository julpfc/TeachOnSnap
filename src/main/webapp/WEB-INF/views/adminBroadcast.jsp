<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="i18n.notify" var="notifyBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Broadcast a message to a gorup or to all application's users --%>
<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="admin.group.broadcast.heading" bundle="${adminBundle}"/>			
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">		
		<div class="row">
			<div class="panel panel-default">
     			<div class="panel-heading">
			 		<c:if test="${not empty group}">
     					<label><fmt:message key="admin.group.broadcast.addressees" bundle="${adminBundle}"/>:</label>
						${fn:escapeXml(group.groupName)}
					</c:if>
					<c:if test="${empty group}">
     					<label><fmt:message key="admin.broadcast.all.users" bundle="${adminBundle}"/></label>						
					</c:if>
				</div><!-- /.panel-heading -->
				<div class="panel-body">
					<c:if test="${empty group}">
						<div class="alert alert-warning alert-dismissible text-center col-sm-10 col-sm-offset-1">
						 	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4><span class="glyphicon glyphicon-exclamation-sign"></span> <fmt:message key="admin.broadcast.all.warning" bundle="${adminBundle}"/></h4>
							<h4><small><fmt:message key="admin.broadcast.all.warning.tip" bundle="${adminBundle}"/></small></h4>
						</div>
					</c:if>				
					<c:if test="${not empty group && empty group.users}">
						<h4><fmt:message key="admin.group.users.empty" bundle="${adminBundle}"/></h4>
					</c:if>
					<c:if test="${not empty group.users}">	    			
						<c:forEach items="${group.users}" var="profile">
							<c:if test="${not empty profile.firstName}">
								<span class="label label-${profile.admin?'warning':(profile.author?'info':'default')}" data-toggle="tooltip" data-placement="top" title="${profile.email}"><img alt="${profile.language.language}" src="../../resources/img/ico/flag_${profile.language.language}.jpg"/> ${profile.fullName}</span> 	
							</c:if>
						</c:forEach>
					</c:if>					 
				</div><!-- /.panel-body -->
				<c:if test="${empty group || not empty group.users}">
					<div class="panel-footer">					 
						<form id="broadcastForm" method="POST">
							<ul class="list-group">
								<li class="list-group-item">
									<input type="text" name="broadcastSubject" class="form-control" maxlength="255"	placeholder="<fmt:message key="admin.broadcast.subject" bundle="${adminBundle}"/>" required="required" />	
								</li>
								<li class="list-group-item">
									<textarea id="iFrameMessage" name="broadcastMessage" class="form-control" placeholder="<fmt:message key="admin.broadcast.message" bundle="${adminBundle}"/>" required="required"></textarea>
								</li>
							</ul>	
							<div class="help-block">
								<fmt:message key="admin.broadcast.message.tip" bundle="${adminBundle}" var="messageTip"/>
						        <button id="previewButton" class="btn btn-xs btn-success" type="button"><span class="glyphicon glyphicon-search"></span> <fmt:message key="admin.broadcast.preview" bundle="${adminBundle}"/></button>
								 ${fn:escapeXml(messageTip)}								
								
							</div>	
							<div class="violetButton pull-right">
					        	<button class="btn btn-primary" type="submit"><span class="glyphicon glyphicon-bullhorn"></span> <fmt:message key="admin.broadcast.send" bundle="${adminBundle}"/></button>
							</div>				
					        <c:import url="./import/confirm.jsp"/>
					        &nbsp;
						</form>
			        	&nbsp;
						<div id="iFrameBody" class="embed-responsive embed-responsive-16by9 hidden"></div>
						&nbsp;
	     			</div><!-- /.panel-footer -->
     			</c:if>
     		</div><!-- /.panel -->		
     	</div><!-- /.row --> 
     	<c:if test="${not empty backPage}">    	
			<div class="row">
				<nav>
					<ul class="pager">
						<li><a href="${backPage}"><span class="glyphicon glyphicon-chevron-left"></span>
						 <fmt:message key="pager.back"/></a></li>						 						
					</ul>
				</nav>	
			</div><!-- /.row -->
		</c:if>
	</div><!-- /.content -->		
	<c:import url="./import/footer.jsp"/>
	<!-- Javascript -->
	<c:import url="./import/js_bootstrap.jsp"/>
	<fmt:message key="notify.html.broadcast.template" bundle="${notifyBundle}" var="template"/>
	<c:set var="templateEscaped">${fn:replace(template, '"', '\\"')}</c:set>
	<script type="text/javascript">
		<!--	    
		var msg = {};
		msg['admin.broadcast.confirm'] = 			"<fmt:message key="admin.broadcast.confirm" bundle="${adminBundle}"/>";
		msg['notify.html.broadcast.template'] = 	"${templateEscaped}";
		
		var appHost = "${host}";
		//-->
	</script>
	<script src="${host}/resources/js/confirm.js"></script>	
 	<script src="${host}/resources/js/adminBroadcast.js"></script>
 	<!-- /Javascript -->
</body>
</html>