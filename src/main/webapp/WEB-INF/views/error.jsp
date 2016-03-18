<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.error" var="errorBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Show friendly error --%> 
<!DOCTYPE html>
<html>
<head>
<c:import url="./import/head_bootstrap.jsp"/>
<title><fmt:message key="app.name"/> - <fmt:message key="error.heading" bundle="${errorBundle}"/></title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">
		<div class="row">
			<div class="help-block col-sm-10 col-sm-offset-1">
				<h2><span class="glyphicon glyphicon-remove-circle"></span> <fmt:message key="error.heading" bundle="${errorBundle}"/></h2>
			</div>
		</div>		
		<c:if test="${not empty statusCode}">
			<!-- Status code errors -->
			<div class="row">
				<fmt:message key="error.status.${statusCode}" bundle="${errorBundle}" var="statusMessage"/>
				<div class="col-sm-10 col-sm-offset-1"><blockquote>
					<p>
						<c:choose>
							<c:when test="${fn:startsWith(statusMessage,'???')}">
								<fmt:message key="error.status.${fn:substring(statusCode,0,1)}xx" bundle="${errorBundle}"/>
							</c:when>
							<c:otherwise>
								${statusMessage}
							</c:otherwise>
						</c:choose>
					</p>
					<footer>Status ${statusCode}</footer>
				</blockquote></div>
			</div>
		</c:if>
		<c:if test="${not empty exceptionName}">
			<!-- Exceptions -->
			<div class="row">
				<fmt:message key="error.exception.${exceptionName}" bundle="${errorBundle}" var="exceptionMessage"/>
				<div class="col-sm-10 col-sm-offset-1"><blockquote class="blockquote-reverse">
					<p>
						<c:choose>
							<c:when test="${fn:startsWith(exceptionMessage,'???')}">
								<fmt:message key="error.exception.default" bundle="${errorBundle}"/>
							</c:when>
							<c:otherwise>
								${exceptionMessage}
							</c:otherwise>
						</c:choose>
					</p>
					<footer>${exceptionName}</footer>
				</blockquote></div>
			</div>
		</c:if>
		<div class="row">
			<nav>
				<ul class="pager">
					<li><a href="${host}/"><span class="glyphicon glyphicon-home"></span>
			 		<fmt:message key="pager.home"/></a></li>						 						
				</ul>
			</nav>
		</div>			
	</div><!-- /.content -->
	<c:import url="./import/footer.jsp"/>
	<!-- Javascript -->
	<c:import url="./import/js_bootstrap.jsp"/>
	<!-- /Javascript -->
</body>
</html>