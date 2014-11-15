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
						<c:forEach items="${not empty userTest?userTest.questions:test.questions}" var="question">	
							<div class="panel ${not empty userTest?(question.OK?'panel-success':'panel-danger'):'panel-default' } question">
  								<div class="panel-heading">${question.text}</div>
								<ul class="list-group">
  									<c:forEach items="${question.answers}" var="answer">
  										<li class="list-group-item answer${not empty userTest?(answer.checked?' active':''):''}">
    										<input type="${test.multipleChoice?'checkbox':'radio'}" name="question_${question.id}" value="${answer.id}" 
    											${not empty userTest?(answer.checked?'checked="checked" disabled="disabled"':'disabled="disabled"'):''}/>
    										${answer.text}
    									</li>
									</c:forEach>								
								</ul>	     			
							</div>
						</c:forEach>	     			
					</div>
				</div>
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
        		<c:if test="${lesson.idLessonTest>0 && empty userTest}">
						<div class="sidebar">
							<h4>Test de autoevaluación</h4>
							 <button class="btn btn-success" type="submit">Corregir</button>
						</div>
				</c:if>
				<c:if test="${not empty userTest}">
						<div class="sidebar">
							<h4>Resultados</h4>
							<p>${userTest.numOKs}/${test.numQuestions}</p>
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