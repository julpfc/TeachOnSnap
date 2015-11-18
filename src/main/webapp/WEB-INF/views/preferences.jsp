<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.preferences" var="prefBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="user.pref.heading" bundle="${prefBundle}"/>			
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<div class="content container-fluid">		
		<div class="row"> 			
			<div class="panel panel-primary col-sm-6">
				<div class="panel-body">					
					<label><fmt:message key="user.pref.username" bundle="${prefBundle}"/>: </label>
				 	<span id="span-name">
						${user.fullName}
						<a class="pull-right" onclick="return showEditName(true);"><fmt:message key="user.pref.edit" bundle="${prefBundle}"/></a>
					</span>
					<form id="usernameForm" action="" method="POST">
						<div id="div-name" class="hidden">
							<span class="input-group-addon"><fmt:message key="user.pref.firstname" bundle="${prefBundle}"/>:</span>
							<input name="firstname" type="text" class="form-control" placeholder="${fn:escapeXml(user.firstName)}" required="required">
							<span class="input-group-addon"><fmt:message key="user.pref.lastname" bundle="${prefBundle}"/>:</span>
							<input name="lastname" type="text" class="form-control" placeholder="${fn:escapeXml(user.lastName)}" required="required">
					      	<span class="pull-right">
					        	<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-floppy-disk"></span></button>
					        	<button class="btn btn-default" type="button" onclick="return showEditName(false);"><span class="glyphicon glyphicon-remove"></span></button>
					      	</span>
					    </div>
					</form>						
				</div>
			</div>
			<div class="panel panel-primary col-sm-6">
				<div class="panel-body">					
						<label><fmt:message key="user.pref.password" bundle="${prefBundle}"/>: </label>
				 	<span id="span-password">
						**********
						<a class="pull-right" onclick="return showEditPassword(true);"><fmt:message key="user.pref.changepassword" bundle="${prefBundle}"/></a>
					</span>
					<form id="passwordForm" action="" method="POST">
						<div id="div-password" class="hidden">
							<span class="input-group-addon"><fmt:message key="user.pref.oldpassword" bundle="${prefBundle}"/>:</span>
							<input id="pwo" name="pwo" type="password" class="form-control" required="required">
							<span class="input-group-addon"><fmt:message key="user.pref.newpassword" bundle="${prefBundle}"/>:</span>
							<input id="pwn1" name="pwn" type="password" class="form-control" required="required">
							<span class="input-group-addon"><fmt:message key="user.pref.repeatpassword" bundle="${prefBundle}"/>:</span>
							<input id="pwn2" type="password" class="form-control" required="required">
					      	<span class="pull-right">
					        	<button id="passwordFormButton" class="btn btn-default" type="submit" ><span class="glyphicon glyphicon-floppy-disk"></span></button>
					        	<button class="btn btn-default" type="button" onclick="return showEditPassword(false);"><span class="glyphicon glyphicon-remove"></span></button>
					      	</span>
					      	<input id="pwnMatch" type="hidden" class="form-control" value="<fmt:message key="user.pref.validator.matchpasswords" bundle="${prefBundle}"/>">					      	
						</div>
				    </form>
				</div>
			</div>
		</div><!-- /.row -->
		<div class="row">
			<nav>
				<ul class="pager">
					<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-left"></span>
					 <fmt:message key="pager.back"/></a></li>						 						
				</ul>
			</nav>	
		</div><!-- /.row -->
	 </div><!-- /.container -->		
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="/resources/js/preferences.js"></script>	
</body>
</html>