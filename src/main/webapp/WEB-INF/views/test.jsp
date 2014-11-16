<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/test.css"/>"/>
<title>TeachOnSnap - ${lesson.title} - Test</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<form action="" method="post">
	<div class="content container-fluid">
		<div>
       		<h2 class="lesson-title">${lesson.title}</h2>       		 	
			<p class="lesson-meta">
				<c:if test="${userLang.id != lesson.language.id}">
     				<img alt="${lesson.language.language}" src="/resources/img/ico/flag_${lesson.language.language}.jpg"/>
     			</c:if>	            			 
      			<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${lesson.date}"/>
      			 by <a href="${lesson.author.URL}">${lesson.author.fullName}</a>
   			</p> 
       	</div>
		<div class="row">
			<div class="col-sm-7">
				<div>
	     			<div class="lesson-test">
	     				<c:if test="${test.multipleChoice}">
	     					<p><span class="glyphicon glyphicon-exclamation-sign"></span> Este test es multiopción</p>
	     				</c:if>
						<c:forEach items="${not empty userTest?userTest.questions:test.questions}" var="question">	
							<div class="panel ${not empty userTest?(question.OK?'panel-success':'panel-danger'):'panel-default'} question">
  								<div class="panel-heading">
  									<c:if test="${not empty userTest}">
  										<span class="glyphicon ${question.OK?'glyphicon-ok':'glyphicon-remove'}"></span>
  									</c:if>
  									${question.text}
  								</div>
								<ul class="list-group">
  									<c:forEach items="${question.answers}" var="answer">
  										<li class="list-group-item answer${not empty userTest?(answer.checked?(answer.OK?' list-group-item-success':' list-group-item-danger'):''):''}">
    										<input type="${test.multipleChoice?'checkbox':'radio'}" name="question_${question.id}" value="${answer.id}" 
    											${not empty userTest?(answer.checked?'checked="checked" disabled="disabled"':'disabled="disabled"'):''}/>    										
    										${answer.text}
	    									<c:if test="${not empty userTest && not empty answer.reason && answer.checked && !answer.OK}">
	    										<ul><li class="list-group-item">${answer.reason}</li></ul>
	    									</c:if>
    									</li>
									</c:forEach>								
								</ul>																	     			
							</div>
						</c:forEach>	     			
					</div>
				</div>
				<nav>
					<ul class="pager">
						<li><a href="${lesson.URL}"><span class="glyphicon glyphicon-chevron-left"></span> Volver</a></li>						
					</ul>
				</nav>		
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
        		<c:if test="${lesson.idLessonTest>0 && empty userTest}">
						<div class="sidebar">
							<h4>Test de autoevaluación</h4>
							 <button class="btn btn-success" type="submit"><span class="glyphicon glyphicon-check"></span> Corregir</button>
						</div>
				</c:if>
				<c:if test="${not empty userTest}">
						<div class="sidebar">							
							<fmt:formatNumber maxFractionDigits="0" value="${100 * userTest.numOKs/test.numQuestions}" var="percentage"/>
							<h2>
								${percentage<40?'Very poor: ':(percentage<60?'Poor: ':(percentage<85?'Good: ':'Nice: '))}
								${percentage} %
							</h2>
							<div class="progress">
  								<div class="progress-bar ${percentage<40?'progress-bar-danger':(percentage<60?'progress-bar-warning':(percentage<85?'progress-bar-info':'progress-bar-success'))}" 
  									role="progressbar" aria-valuenow="${percentage}" aria-valuemin="0" aria-valuemax="100" style="width: ${percentage}%">
    								<span class="sr-only">${percentage}% Complete</span>
  								</div>
							</div>
							<a href="${lesson.testURL}"><button class="btn btn-default" type="button">Reintentar test</button></a>							
						</div>
				</c:if>				

        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
	</form>	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
	<script src="/resources/js/test.js"></script>
	
</body>
</html>