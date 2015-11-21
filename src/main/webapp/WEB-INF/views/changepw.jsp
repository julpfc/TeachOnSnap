<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.preferences" var="prefBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="user.pref.changepassword" bundle="${prefBundle}"/>			
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<div class="content container-fluid">			
		<div id="login" class="row col-sm-6 col-sm-offset-3"> 			
			<form id="passwordForm" action="" method="POST">
				<div class="panel panel-default">
				<div class="panel-heading">
					Completa tus datos:				 	
				</div>
					<div class="panel-body">
						<c:choose>
						<c:when test="${empty user.firstName || empty user.lastName}">
							<span class="input-group-addon"><fmt:message key="user.pref.firstname" bundle="${prefBundle}"/>:</span>
							<input name="firstname" type="text" class="form-control" required="required" value="${user.firstName}">
							<span class="input-group-addon"><fmt:message key="user.pref.lastname" bundle="${prefBundle}"/>:</span>
							<input name="lastname" type="text" class="form-control" required="required" value="${user.lastName}">
						</c:when>
						<c:otherwise>
							<label><fmt:message key="user.pref.username" bundle="${prefBundle}"/>: </label>
				 			<span id="span-name">
								${user.fullName}
							</span>
						</c:otherwise>
						</c:choose>
						<span class="input-group-addon"><fmt:message key="user.pref.newpassword" bundle="${prefBundle}"/>:</span>
						<input id="pwn1" name="pwn" type="password" class="form-control" required="required">
						<span class="input-group-addon"><fmt:message key="user.pref.repeatpassword" bundle="${prefBundle}"/>:</span>
						<input id="pwn2" type="password" class="form-control" required="required">
				      	
				      	<input id="pwnMatch" type="hidden" class="form-control" value="<fmt:message key="user.pref.validator.matchpasswords" bundle="${prefBundle}"/>">
			      	</div>					  
			</div>
			        	<button id="passwordFormButton" class="btn btn-primary pull-right" type="submit" ><span class="glyphicon glyphicon-floppy-disk"></span> <fmt:message key="user.pref.changepassword" bundle="${prefBundle}"/></button>									 	
		    	</form>
		</div><!-- /.row -->
	</div><!-- /.container -->		 
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="/resources/js/changepw.js"></script>	
</body>
</html>