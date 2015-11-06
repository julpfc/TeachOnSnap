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
		TeachOnSnap - <fmt:message key="user.pref.changepassword" bundle="${prefBundle}"/>			
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<div class="content container-fluid">		
		<div class="row"> 			
			<div class="panel col-sm-6 col-sm-offset-3">
				<div class="panel-body">					
					<label><fmt:message key="user.pref.username" bundle="${prefBundle}"/>: </label>
				 	<span id="span-name">
						${user.fullName}
					</span>
					<form id="passwordForm" action="" method="POST">
						<div id="login">
							<span class="input-group-addon"><fmt:message key="user.pref.newpassword" bundle="${prefBundle}"/>:</span>
							<input id="pwn1" name="pwn" type="password" class="form-control" required="required">
							<span class="input-group-addon"><fmt:message key="user.pref.repeatpassword" bundle="${prefBundle}"/>:</span>
							<input id="pwn2" type="password" class="form-control" required="required">
					      	
					        <button id="passwordFormButton" class="btn btn-primary pull-right" type="submit" ><span class="glyphicon glyphicon-floppy-disk"></span> <fmt:message key="user.pref.changepassword" bundle="${prefBundle}"/></button>
					      	
					      	<input id="pwnMatch" type="hidden" class="form-control" value="<fmt:message key="user.pref.validator.matchpasswords" bundle="${prefBundle}"/>">					      	
						</div>
				    </form>
				</div>
			</div>
		</div><!-- /.row -->
	 </div><!-- /.container -->		
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="/resources/js/changepw.js"></script>	
</body>
</html>