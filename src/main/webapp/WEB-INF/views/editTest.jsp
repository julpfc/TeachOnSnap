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
<link rel="stylesheet" href="<c:url value="/resources/css/editTest.css"/>"/>
<title>TeachOnSnap - ${lesson.title} - EditTest</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<form action="" method="post">
	<div class="content container-fluid">
		<div class="row">
			<div class="col-sm-7">
				<div>
	     			<div class="lesson-test">
	     				<c:if test="${test.multipleChoice}">
	     					<p><span class="glyphicon glyphicon-exclamation-sign"></span>
	     					 <fmt:message key="lesson.test.multiplechoice" bundle="${testBundle}"/></p>
	     				</c:if>
						<c:forEach items="${test.questions}" var="question">	
							<div class="panel panel-default question">
  								<div class="panel-heading">
  									<a href="#"><span class="glyphicon glyphicon-chevron-up"></span></a>
  									<a href="#"><span class="glyphicon glyphicon-chevron-down"></span></a>
  								  	<span>
  										${question.text}
	  									<a class="pull-right" href="${question.editURL}">Editar</a>
  									</span>						
  								</div>
								<ul class="list-group">
  									<c:forEach items="${question.answers}" var="answer">
  										<li class="list-group-item answer${answer.correct?' list-group-item-success':''}">    										    										
    										${answer.text}
	    									<!-- <ul><li class="list-group-item">${answer.reason}</li></ul>
	    									 -->	    									
    									</li>
									</c:forEach>								
								</ul>																	     			
							</div>
						</c:forEach>	     			
					</div>
				</div>
				<nav>
					<ul class="pager">
						<li><a href="${lesson.editURL}"><span class="glyphicon glyphicon-chevron-left"></span>
						 <fmt:message key="pager.back"/></a></li>						
					</ul>
				</nav>		
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
				<div class="sidebar">
					<a href="${test.newQuestionURL}"><button class="btn btn-default" type="button">
					 	Nueva pregunta
					 	 <span class="glyphicon glyphicon-edit"></span></button>
				 	</a>
				</div>
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
	</form>	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="/resources/js/editTest.js"></script>
	
</body>
</html>