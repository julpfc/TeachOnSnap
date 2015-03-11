<%@page import="java.util.Collections"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.error" var="errorBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>
 
<!DOCTYPE html>
<html>
<head>
<c:import url="./import/head_bootstrap.jsp"/>
<!-- <link rel="stylesheet" href="<c:url value="/resources/css/error.css"/>"/>  -->
<title>TeachOnSnap - Error</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">
		
    	<h1><span class="glyphicon glyphicon-book"></span> <fmt:message key="${textKey}" bundle="${errorBundle}"/></h1>
    	
    	<h2>Details</h2>
	<% 
		for(String key:Collections.list(request.getAttributeNames())){
			out.print(key + "=" +  request.getAttribute(key) + "<br/>");			
		}
	%>
	</div>
   
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
</body>
</html>