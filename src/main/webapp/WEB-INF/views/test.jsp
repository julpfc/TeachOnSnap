<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.test" var="testBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Self evaluation test --%>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/test.css"/>"/>
<title><fmt:message key="app.name"/> - 
	<c:choose>
		<c:when test="${not empty statsTest}">
			<fmt:message key="lesson.test.stats.heading" bundle="${testBundle}"/>
		</c:when>
		<c:otherwise>
			<fmt:message key="lesson.test.heading" bundle="${testBundle}"/>
		</c:otherwise>
	</c:choose>
</title>
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
	     				<!-- Questions -->
						<c:forEach items="${not empty userTest?userTest.questions:test.questions}" var="question">	
							<c:set var="questionID" value="[${question.id}]"/>							
							<div class="panel ${not empty userTest?(question.OK?'panel-success':'panel-danger'):'panel-default'} question">
  								<div class="panel-heading">
  									<c:if test="${not empty userTest}">
  										<span class="glyphicon ${question.OK?'glyphicon-ok':'glyphicon-remove'}"></span>
  									</c:if>
  									${fn:escapeXml(question.text)}
  								</div>
								<ul class="list-group">
  									<c:forEach items="${question.answers}" var="answer">
  										<li class="list-group-item answer${not empty userTest?(answer.checked?(answer.OK?' list-group-item-success':' list-group-item-danger'):''):((not empty statsTest && answer.correct)?' list-group-item-success':'')}">
    										<input type="${test.multipleChoice?'checkbox':'radio'}" name="question_${question.id}" value="${answer.id}" 
    											${not empty userTest?(answer.checked?'checked="checked" disabled="disabled"':'disabled="disabled"'):(not empty statsTest?'disabled="disabled"':'')}/>    										
    										${fn:escapeXml(answer.text)}
	    									<c:if test="${not empty userTest && not empty answer.reason && answer.checked && !answer.OK}">
	    										<ul><li class="list-group-item">${answer.reason}</li></ul>
	    									</c:if>
    									</li>
									</c:forEach>								
								</ul>
							<c:if test="${not empty statsTest}">
							<div class="panel-footer">
								<fmt:formatNumber maxFractionDigits="0" value="${100 * (1 - statsTest.questionKOs[questionID]/statsTest.numTests)}" var="percentage"/> 
								<fmt:message key="lesson.test.stats.question.rate" bundle="${testBundle}"/>: ${percentage}%
								<div class="progress">
									<div class="progress-bar ${percentage<40?'progress-bar-danger':(percentage<60?'progress-bar-warning':(percentage<85?'progress-bar-info':'progress-bar-success'))}" 
										role="progressbar" aria-valuenow="${percentage}" aria-valuemin="0" aria-valuemax="100" style="width: ${percentage}%">
			  								<span class="sr-only">${percentage}%</span>
									</div>
								</div>
							</div>
							</c:if>																	     			
							</div>
						</c:forEach><!-- /Questions -->	     			
					</div>
				</div>
				<nav>
					<ul class="pager">
						<li><a href="${backPage}"><span class="glyphicon glyphicon-chevron-left"></span>
						 <fmt:message key="pager.back"/></a></li>						
					</ul>
				</nav>		
	        </div><!-- col -->

			<!-- aside -->
        	<div class="col-sm-4 col-sm-offset-1">
        		<c:if test="${lesson.testAvailable && empty userTest && empty statsTest}">
        			<!-- Validate test -->
					<div class="sidebar">							
        				<div class="panel panel-default">
				    		<div class="panel-heading">
				    			<fmt:message key="lesson.test.heading" bundle="${testBundle}"/>
				    			<button class="btn btn-success btn-xs pull-right" type="submit"><span class="glyphicon glyphicon-check"></span>
						  			<fmt:message key="lesson.test.validate" bundle="${testBundle}"/></button>
				    		</div>
				    		<div class="panel-footer">
				    			<p class="help-block">
								 	<fmt:message key="lesson.test.validate.tip" bundle="${testBundle}"/>
		    				 	</p>	    			
				    		</div>			
						</div>						 
					</div>
				</c:if>
				<c:if test="${not empty userTest && empty statsTest}">
					<!-- Score -->
					<div class="sidebar">							
						<fmt:formatNumber maxFractionDigits="0" value="${100 * userTest.numOKs/test.numQuestions}" var="percentage"/> 
						<h2>								
							<c:if test="${percentage<40}">
								<fmt:message key="lesson.test.result.verypoor" bundle="${testBundle}"/>
 							</c:if>
							<c:if test="${percentage>=40 && percentage<60}">
								<fmt:message key="lesson.test.result.poor" bundle="${testBundle}"/>
							</c:if>
							<c:if test="${percentage>=60 && percentage<85}">
								<fmt:message key="lesson.test.result.good" bundle="${testBundle}"/>
							</c:if>
							<c:if test="${percentage>=85}">
								<fmt:message key="lesson.test.result.nice" bundle="${testBundle}"/>
							</c:if>								
							: ${percentage} %
						</h2>
						<div class="progress">
							<div class="progress-bar ${percentage<40?'progress-bar-danger':(percentage<60?'progress-bar-warning':(percentage<85?'progress-bar-info':'progress-bar-success'))}" 
								role="progressbar" aria-valuenow="${percentage}" aria-valuemin="0" aria-valuemax="100" style="width: ${percentage}%">
   								<span class="sr-only">${percentage}%</span>
							</div>
						</div>
						<a href="${test.URL}"><button class="btn btn-default" type="button"><fmt:message key="lesson.test.retry" bundle="${testBundle}"/></button></a>							
					</div>
				</c:if>
				<c:if test="${not empty userTest || not empty statsTest}">
					<!-- Ranking -->
					<div class="sidebar">
						<div class="panel panel-default">
			        		<div class="panel-heading"><fmt:message key="lesson.test.highscores" bundle="${testBundle}"/></div>
			    			<div class="panel-body">
				    			<table class="table">
									<thead>
										<tr>
											<th>#</th>
											<th><fmt:message key="lesson.test.highscores.name" bundle="${testBundle}"/></th>
											<th><fmt:message key="lesson.test.highscores.points" bundle="${testBundle}"/></th>										
										</tr>
									</thead>
									<tbody>
					    				<c:forEach items="${testRanks}" var="testRank" varStatus="loop">
					    					<tr>
					    						<td>
					    							<c:choose>
					    								<c:when test="${testRank.user.id == user.id}">
					    									<label class="label label-info">${loop.index+1}</label>
						    							</c:when>
						    							<c:otherwise>
						    								${loop.index+1}
						    							</c:otherwise> 
					    							</c:choose>
					    						</td>
					    						<td>${testRank.user.fullName}</td>
					    						<td>${testRank.points} (${testRank.attempts})</td>
					    					</tr>
					    				</c:forEach>
			    					</tbody>
			    				</table>
			    				<c:if test="${not empty statsTest}">
			    					<fmt:message key="lesson.test.stats.num.tests" bundle="${testBundle}"/>: ${statsTest.numTests}
			    				</c:if>
			    			</div>
			    			<div class="panel-footer">
			    				<label class="label label-info"><fmt:message key="lesson.test.highscores.yourbest" bundle="${testBundle}"/>:</label> ${userTestRank.points} 
			    				<c:choose>
			    					<c:when test="${empty userTestRank}">
			    						<fmt:message key="lesson.test.highscores.norecords" bundle="${testBundle}"/>
			    					</c:when>
			    					<c:otherwise>
			    						(${userTestRank.attempts} <fmt:message key="lesson.test.highscores.attempts" bundle="${testBundle}"/>)
			    					</c:otherwise>			    				
			    				</c:choose>			    				
			    			</div>
			    		</div>
					</div>			
				</c:if>	
        	</div><!-- /aside -->
		</div><!-- /.row -->
	</div><!-- /.content -->
	</form>	
	<c:import url="./import/footer.jsp"/>
	<!-- Javascript -->
	<c:import url="./import/js_bootstrap.jsp"/>
	<script src="${host}/resources/js/test.js"></script>
	<!-- /Javascript -->
</body>
</html>