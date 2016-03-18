<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.test" var="testBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Create test --%>
<!DOCTYPE html>
<html> 
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/test.css"/>"/>
<title><fmt:message key="app.name"/> - ${fn:escapeXml(lesson.title)} - <fmt:message key="lesson.test.new" bundle="${testBundle}"/></title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">		
		<div class="row">
			<!-- New test form -->
 			<form id="testForm" action="${lesson.newTestURL}" method="POST">
				<div class="panel panel-default">
					<div class="panel-heading">
						<fmt:message key="lesson.test.new.heading" bundle="${testBundle}"/>						
					</div>
					<div class="panel-body">										 						 		
						<div class="form-group">				    		
    						<p class="input-group">
      							<input id="inputMultipleChoice" type="radio" name="multipleChoice" value="true" required checked="checked"/>
    							<label class="label label-info"><fmt:message key="lesson.test.new.multiplechoice" bundle="${testBundle}"/></label>
      							<fmt:message key="lesson.test.new.multiplechoice.tip" bundle="${testBundle}"/>
      						</p>
							<p class="input-group">
      							<input id="inputMultipleChoice" type="radio" name="multipleChoice" value="false" required checked="checked"/>
    							<label class="label label-info"><fmt:message key="lesson.test.new.singlechoice" bundle="${testBundle}"/></label>
      							<fmt:message key="lesson.test.new.singlechoice.tip" bundle="${testBundle}"/>
      						</p>									    					    	
				    	</div>
				    	<div class="form-group">
    						<label for="inputNumAnswers"><fmt:message key="lesson.test.new.numAnswers.tip" bundle="${testBundle}"/></label>
    						<input id="inputNumAnswers" type="text" name="numAnswers" class="form-control" placeholder="#" required="required" maxlength="1"/>
  						</div>				    	
						<input id="json" type="text" hidden="true" value=""/>
										    
					</div>
					<div class="panel-footer">
						<button class="btn btn-success pull-right" type="submit"><span class="glyphicon glyphicon-save"></span>
						 <fmt:message key="lesson.test.new.save" bundle="${testBundle}"/></button>  						
						<p>&nbsp;</p>
					</div>
				</div>
			</form>
	
			<nav>
				<ul class="pager">
					<li><a href="${lesson.editURL}"><span class="glyphicon glyphicon-chevron-left"></span>
					 <fmt:message key="pager.cancel"/></a></li>						 						
				</ul>
			</nav>		
		</div><!-- /.row -->
	</div><!-- /.content -->	
	<c:import url="./import/footer.jsp"/>
	<!-- Javascript -->
	<c:import url="./import/js_bootstrap.jsp"/>
	<!-- /Javascript -->	
</body>
</html>