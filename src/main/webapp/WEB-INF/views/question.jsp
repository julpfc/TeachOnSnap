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
	<title>TeachOnSnap - <fmt:message key="lesson.test.question.edit" bundle="${testBundle}"/></title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<div class="content container-fluid">
		<div class="row">
 			<form id="questionForm" action="${question.editURL}" method="POST">
				<div class="panel panel-default question">
					<div class="panel-heading">
					 	<!-- 	<span id="spanq-${question.id}">
										${question.text}
 									<a class="pull-right" onclick="return showEditQuestion(${question.id},true);">Editar</a>
									</span>
									<div id="divq-${question.id}" class="input-group hidden">
								<input type="text" class="form-control" placeholder="${question.text}">
						      	<span class="input-group-btn">
						        	<button class="btn btn-default" type="button"><span class="glyphicon glyphicon-floppy-disk"></span></button>
						        	<button class="btn btn-default" type="button" onclick="return showEditQuestion(${question.id},false);"><span class="glyphicon glyphicon-remove"></span></button>
						      	</span>
						    </div>
						     -->
					<!-- /input-group -->
						<input type="text" name="text" class="form-control" placeholder="<fmt:message key="lesson.test.question.text" bundle="${testBundle}"/>" value="${question.text}" required="required"/>
						<input name="id" type="text" hidden="true" value="${question.id}"/>
						<input name="idLessonTest" type="text" hidden="true" value="${question.idLessonTest}"/>
						<input name="priority" type="text" hidden="true" value="${question.priority}"/>
					</div>
								
					<div class="panel-body">
  	     				<c:if test="${test.multipleChoice}">
    						<p><span class="glyphicon glyphicon-exclamation-sign"></span>
    					 		<fmt:message key="lesson.test.multiplechoice" bundle="${testBundle}"/></p>
    					</c:if>
    				 	<p><span class="label label-info">${test.numAnswers}</span> <fmt:message key="lesson.test.numAnswers" bundle="${testBundle}"/>
  							<button class="btn btn-primary pull-right" type="submit"><span class="glyphicon glyphicon-save"></span>
						 <fmt:message key="lesson.test.question.save" bundle="${testBundle}"/></button>
    				 	</p>
						<table class="table">
							<thead>
								<tr>
									<th><fmt:message key="lesson.test.answer.text" bundle="${testBundle}"/></th>
									<th><fmt:message key="lesson.test.answer.reason.heading" bundle="${testBundle}"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${question.answers}" var="answer" varStatus="loop">
									<tr class="${answer.correct?' list-group-item-success':''}">
										<td class="col-xs-6">
											<input name="answers[${loop.index}].id" type="text" hidden="true" value="${answer.id}"/>
											<input name="answers[${loop.index}].idQuestion" type="text" hidden="true" value="${question.id}"/>
 												<input type="text" name="answers[${loop.index}].text" class="form-control" placeholder="<fmt:message key="lesson.test.answer.text" bundle="${testBundle}"/>" value="${answer.text}" required="required"/>
 										</td>
 										<td class="col-xs-6">
  											<div class="input-group">
										    	<span class="input-group-addon">
													<input name="answers[${loop.index}].correct" type="${test.multipleChoice?'checkbox':'radio'}" ${answer.correct?'checked="checked"':''} value="true" />
										      	</span>
										      	<input type="text" name="answers[${loop.index}].reason" class="form-control" placeholder="<fmt:message key="lesson.test.answer.reason" bundle="${testBundle}"/>" value="${answer.reason}" required="required"/>
										    </div>
 										</td>	    									
 									</tr>
								</c:forEach>
							</tbody>								
						</table>									
						<input id="json" type="text" hidden="true" value=""/>
					</div>
					<div class="panel-footer">
						<p class="help-block"><a href="${question.editURL}?export=JSON"><button type="button" class="btn btn-primary btn-xs pull-right"><span class="glyphicon glyphicon-save-file"></span>
								 <fmt:message key="lesson.test.question.export" bundle="${testBundle}"/></button>
						 	</a>
						 	<fmt:message key="lesson.test.question.export.tip" bundle="${testBundle}"/>
    				 	</p>
					</div>
				</div>
			</form>
	
			<nav>
				<ul class="pager">
					<li><a href="${test.editURL}"><span class="glyphicon glyphicon-chevron-left"></span>
					 <fmt:message key="pager.cancel"/></a></li>						 						
				</ul>
			</nav>		
		</div><!-- /.row -->
    </div><!-- /.container -->		
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="/resources/js/ext/form2js.js"></script>
	<script src="/resources/js/question.js"></script>	
</body>
</html>