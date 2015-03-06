<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.test" var="testBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html> 
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/test.css"/>"/>
<title>TeachOnSnap - ${lesson.title} - NewTest</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<form action="" method="post">
	<div class="content container-fluid">		
		<div class="row">
			<div class="col-sm-7">
				
					
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
        		
				
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
	</form>	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	
</body>
</html>