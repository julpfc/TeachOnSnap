<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.preferences" var="prefBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		TeachOnSnap - <fmt:message key="user.pref.heading" bundle="${prefBundle}"/>			
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<div class="content container-fluid">		
		<div class="row">
 			<form id="questionForm" action="${not empty question?question.editURL:test.newQuestionURL}" method="POST">
				<div class="panel panel-default question">
					<div class="panel-heading">
					 	<span id="spanq-${question.id}">
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
						
					<!-- /input-group -->
						<input type="text" name="text" class="form-control" placeholder="P" value="${question.text}" required="required" maxlength="140"/>
						<c:if test="${not empty question}">
							<input name="idLessonTest" type="text" hidden="true" value="${test.id}"/>
							<input name="id" type="text" hidden="true" value="${question.id}"/>
							<input name="priority" type="text" hidden="true" value="${question.priority}"/>
						</c:if>
					</div>
					 			
					<div class="panel-body">
  	     				<c:if test="${test.multipleChoice}">
    						<p><span class="glyphicon glyphicon-exclamation-sign"></span>
    					 		M</p>
    					</c:if>
    				 	<p><span class="label label-info">${test.numAnswers}</span> N
  							<button class="btn btn-primary pull-right" type="submit"><span class="glyphicon glyphicon-save"></span>
						 Save</button>
    				 	</p>
						<table class="table">
							<thead>
								<tr>
									<th>T</th>
									<th>R</th>
								</tr>
							</thead>
							<tbody>
										<tr>
											<td class="col-xs-6">												
 												<input type="text" name="answers[${i}].text" class="form-control" placeholder="T" required="required" maxlength="140"/>
	 										</td>
	 										<td class="col-xs-6">
	  											<div class="input-group">
											    	<span class="input-group-addon">
														<input name="answers[${i}].correct" type="${test.multipleChoice?'checkbox':'radio'}" value="true" />
											      	</span>
											      	<input type="text" name="answers[${i}].reason" class="form-control" placeholder="R" required="required" maxlength="140"/>
											    </div>
	 										</td>	    									
	 									</tr>
							</tbody>								
						</table>
					</div>
					<div class="panel-footer">
								<p class="help-block">
									<a data-toggle="collapse" href="#collapseImportJSON" aria-expanded="false" aria-controls="collapseImportJSON">
										<button type="button" class="btn btn-primary btn-xs pull-right">
											<span class="glyphicon glyphicon-open-file"></span>
											Import
										</button>
								 	</a>
								 	Tip
		    				 	</p>		    				
					</div>
				</div>
			</form>
	
			<nav>
				<ul class="pager">
					<li><a href="#"><span class="glyphicon glyphicon-chevron-left"></span>
					 <fmt:message key="pager.back"/></a></li>						 						
				</ul>
			</nav>		
		</div><!-- /.row -->
    </div><!-- /.container -->		
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
</body>
</html>