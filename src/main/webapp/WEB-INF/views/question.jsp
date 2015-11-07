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
	<title><fmt:message key="app.name"/> - 
		<c:choose>
			<c:when test="${not empty question}">
				<fmt:message key="lesson.test.question.edit" bundle="${testBundle}"/>
			</c:when>
			<c:otherwise>
				<fmt:message key="lesson.test.question.new" bundle="${testBundle}"/>
			</c:otherwise>
		</c:choose>
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<div class="content container-fluid">		
		<div class="row">
 			<form id="questionForm" action="${not empty question?question.editURL:test.newQuestionURL}" method="POST">
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
						<input type="text" name="text" class="form-control" placeholder="<fmt:message key="lesson.test.question.text" bundle="${testBundle}"/>" value="${question.text}" required="required" maxlength="140"/>
						<c:if test="${not empty question}">
							<input name="idLessonTest" type="text" hidden="true" value="${test.id}"/>
							<input name="id" type="text" hidden="true" value="${question.id}"/>
							<input name="priority" type="text" hidden="true" value="${question.priority}"/>
						</c:if>
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
											<input type="text" name="answers[${loop.index}].text" class="form-control" placeholder="<fmt:message key="lesson.test.answer.text" bundle="${testBundle}"/>" value="${answer.text}" required="required" maxlength="140"/>
 										</td>
 										<td class="col-xs-6">
  											<div class="input-group">
										    	<span class="input-group-addon">
													<input name="answers[${loop.index}].correct" type="${test.multipleChoice?'checkbox':'radio'}" ${answer.correct?'checked="checked"':''} value="true" />
										      	</span>
										      	<input type="text" name="answers[${loop.index}].reason" class="form-control" placeholder="<fmt:message key="lesson.test.answer.reason" bundle="${testBundle}"/>" value="${answer.reason}" required="required" maxlength="140"/>
										    </div>
 										</td>	    									
 									</tr>
								</c:forEach>
								<c:if test="${empty question}">
									<c:forEach begin="0" end="${test.numAnswers - 1}" var="i">
										<tr>
											<td class="col-xs-6">												
 												<input type="text" name="answers[${i}].text" class="form-control" placeholder="<fmt:message key="lesson.test.answer.text" bundle="${testBundle}"/>" required="required" maxlength="140"/>
	 										</td>
	 										<td class="col-xs-6">
	  											<div class="input-group">
											    	<span class="input-group-addon">
														<input name="answers[${i}].correct" type="${test.multipleChoice?'checkbox':'radio'}" value="true" />
											      	</span>
											      	<input type="text" name="answers[${i}].reason" class="form-control" placeholder="<fmt:message key="lesson.test.answer.reason" bundle="${testBundle}"/>" required="required" maxlength="140"/>
											    </div>
	 										</td>	    									
	 									</tr>
									</c:forEach>
								</c:if>
							</tbody>								
						</table>									
						<input id="json" type="text" hidden="true" value=""/>
					</div>
					<div class="panel-footer">
						<c:choose>
							<c:when test="${not empty question}">
								<p class="help-block"><a href="${question.editURL}?export=JSON"><button type="button" class="btn btn-primary btn-xs pull-right"><span class="glyphicon glyphicon-save-file"></span>
										 <fmt:message key="lesson.test.export" bundle="${testBundle}"/></button>
								 	</a>
								 	<fmt:message key="lesson.test.question.export.tip" bundle="${testBundle}"/>
		    				 	</p>
							</c:when>
							<c:otherwise>
								<p class="help-block">
									<a data-toggle="collapse" href="#collapseImportJSON" aria-expanded="false" aria-controls="collapseImportJSON">
										<button type="button" class="btn btn-primary btn-xs pull-right">
											<span class="glyphicon glyphicon-open-file"></span>
											 <fmt:message key="lesson.test.question.import" bundle="${testBundle}"/>
										</button>
								 	</a>
								 	<fmt:message key="lesson.test.question.import.tip" bundle="${testBundle}"/>
		    				 	</p>		    				
								<div class="collapse" id="collapseImportJSON">	  					
		  							<div class="form-group well">			   					
						    			<p><textarea id="JSONarea" class="form-control" placeholder="<fmt:message key="lesson.test.question.import.placeholder" bundle="${testBundle}"/>" rows="6"></textarea></p>				    	
		  								<p><button class="btn btn-primary form-control" type="button" onclick="return importJSON();">
		  									<span class="glyphicon glyphicon-open-file"></span>
											 <fmt:message key="lesson.test.question.import" bundle="${testBundle}"/>
										</button></p>
		  							</div>		  							
	  							</div>
							</c:otherwise>
						</c:choose>
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