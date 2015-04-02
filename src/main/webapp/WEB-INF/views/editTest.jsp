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
<title>TeachOnSnap - ${lesson.title} - <fmt:message key="lesson.test.edit" bundle="${testBundle}"/></title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
<c:import url="./import/confirm.jsp"/>
	<form action="" method="post">
	<div class="content container-fluid">
		<div class="row">
			<div class="col-sm-7">
     			<div class="lesson-test">
     				<c:if test="${empty test.questions}">
     					<div class="panel panel-default">
				    		<div class="panel-heading">
     							<fmt:message key="lesson.test.question.noquestions" bundle="${testBundle}"/>
     						</div>
     						<div class="panel-body">
     							<a href="${test.newQuestionURL}">				    	 			
				    	 			<button class="btn btn-primary pull-right" type="button">
					 	 				<span class="glyphicon glyphicon-plus-sign"></span>
					 					 <fmt:message key="lesson.test.question.new" bundle="${testBundle}"/>			 
					 	 			</button>
				 				</a>
     						</div>     						
     					</div>
     				</c:if>	     				
					<c:forEach items="${test.questions}" var="question" varStatus="loop">
						<div id="panel${loop.index}" class="panel-group" role="tablist">
   							<div class="panel panel-default">
     								<div class="panel-heading" role="tab" id="collapseQuestionHeading${question.id}" data-toggle="collapse" data-target="#collapseQuestion${question.id}">
     								<span class="label label-default">${loop.index+1}</span>
									<a class="collapsed" onclick="#collapseQuestion${question.id}" aria-expanded="false" aria-controls="collapseQuestion${question.id}">
           								${question.text}
         								</a>
								</div>
								<div style="height: 0px;" aria-expanded="false" id="collapseQuestion${question.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="collapseQuestionHeading${question.id}">
       								<ul class="list-group">
	        							<c:forEach items="${question.answers}" var="answer">
	  										<li class="list-group-item answer${answer.correct?' list-group-item-success':''}">    										    										
	    										${answer.text}			    										    									
	    									</li>
										</c:forEach>	
							        </ul>
							       	<div class="panel-footer">
							       		&nbsp;
										<a id="moveButton${question.id}" onclick="showMoveControls(${question.id});">
						    				<button class="btn btn-default btn-xs pull-left" type="button">
									 			<span class="glyphicon glyphicon-move"></span>
									 			 <fmt:message key="lesson.test.question.move" bundle="${testBundle}"/>
								 			 </button>
										</a>
										<a id="upButton${question.id}" onclick="moveUp(${question.id},${loop.index});" class="hidden">
						    				<button class="btn btn-success btn-xs pull-left cursor" type="button">
									 			<span class="glyphicon glyphicon-chevron-up"></span>
								 			 </button>
										</a>
										<a id="downButton${question.id}" onclick="moveDown(${question.id},${loop.index},${test.numQuestions});" class="hidden">
						    				<button class="btn btn-danger btn-xs pull-left" type="button">
									 			<span class="glyphicon glyphicon-chevron-down"></span>
								 			 </button>
										</a>										
										<a id="saveButton${question.id}" onclick="window.location.href='${test.editURL}?idQuestion=${question.id}&questionPriority='+currentPosition(${question.id},${loop.index});" class="hidden">
						    				<button class="btn btn-primary btn-xs pull-left cursor" type="button">
									 			<span class="glyphicon glyphicon-save"></span>
									 			 <fmt:message key="lesson.test.question.move.save" bundle="${testBundle}"/>
								 			 </button>
										</a>
										<a id="cancelButton${question.id}" onclick="hideMoveControls(${question.id},${loop.index});" class="hidden">
						    				<button class="btn btn-default btn-xs pull-left" type="button">
									 			<span class="glyphicon glyphicon-ban-circle"></span>
									 			 <fmt:message key="pager.cancel"/>
								 			 </button>
										</a>										
										<a onclick="confirm('${test.editURL}?delQuestion=${question.id}','lesson.test.question.delete.confirm');">
						    				<button class="btn btn-danger btn-xs pull-right" type="button">
								 			<span class="glyphicon glyphicon-remove"></span>
								 			 <fmt:message key="lesson.test.question.delete" bundle="${testBundle}"/>
								 			 </button>
										</a>
							       		<a href="${question.editURL}">
						    				<button class="btn btn-primary btn-xs pull-right" type="button">
								 			<span class="glyphicon glyphicon-edit"></span>
								 			 <fmt:message key="lesson.test.question.edit" bundle="${testBundle}"/>
								 			 </button>
										</a>
 										</div>
     								</div>
   							</div>
 						</div>							
					</c:forEach>	     			
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
					<div class="panel panel-default">
				    	<div class="panel-heading">
				    	 	<c:choose>
								<c:when test="${test.draft}">
									<span class="label label-warning"><fmt:message key="lesson.test.unpublished" bundle="${testBundle}"/></span>
									<a onclick="confirm('${test.editURL}?publishTest=true','lesson.test.publish.confirm');">
							    		<button class="btn btn-success btn-xs pull-right" type="button">
											<span class="glyphicon glyphicon-eye-open"></span>
										 	 <fmt:message key="lesson.test.publish" bundle="${testBundle}"/>
								 		</button>
									</a>
								</c:when>
								<c:otherwise>
									<span class="label label-success"><fmt:message key="lesson.test.published" bundle="${testBundle}"/></span>
							    		<a onclick="confirm('${test.editURL}?publishTest=false','lesson.test.unpublish.confirm');">
							    			<button class="btn btn-warning btn-xs pull-right" type="button">											
										 		<span class="glyphicon glyphicon-eye-close"></span>
												 <fmt:message key="lesson.test.unpublish" bundle="${testBundle}"/>			 	
											</button>
										</a>
								</c:otherwise>
							</c:choose>	    			
				    	</div>
			    		<div class="panel-body">
			    			<c:if test="${test.multipleChoice}">
								<h5><span class="glyphicon glyphicon-exclamation-sign"></span>
	     							 <fmt:message key="lesson.test.multiplechoice" bundle="${testBundle}"/>
	     						</h5>	     							
	     					</c:if>
							<h5><span class="label label-info">${test.numAnswers}</span> <fmt:message key="lesson.test.numAnswers" bundle="${testBundle}"/></h5>																	
							<h5><span class="label label-info">${test.numQuestions}</span> <fmt:message key="lesson.test.numQuestions" bundle="${testBundle}"/></h5>
							<a onclick="confirm('${test.editURL}?delTest=true','lesson.test.delete.confirm');">
			    				<button class="btn btn-danger btn-xs pull-right" type="button">
					 			<span class="glyphicon glyphicon-remove"></span>
					 			 <fmt:message key="lesson.test.delete" bundle="${testBundle}"/>
					 			 </button>
							</a>
						</div>
						<div class="panel-footer">							
		    	 			&nbsp;
			    	 		<a href="${test.newQuestionURL}">
			    	 			<button class="btn btn-primary btn-xs pull-right" type="button">
				 	 				<span class="glyphicon glyphicon-plus-sign"></span>
				 					 <fmt:message key="lesson.test.question.new" bundle="${testBundle}"/>			 
				 	 			</button>
			 				</a>									 	
	     				</div>		
					</div><!-- Test Panel -->
					<c:if test="${test.numQuestions>0}">
						<div class="panel panel-default">
				    		<div class="panel-heading">
				    			<p class="help-block">
								 	<fmt:message key="lesson.test.export.tip" bundle="${testBundle}"/>
		    				 	</p>	    			
				    		</div>			    	
				    		<div class="panel-footer">
				    			&nbsp;<a href="${test.editURL}?export=JSON"><button type="button" class="btn btn-primary btn-xs pull-right"><span class="glyphicon glyphicon-save-file"></span>
										 <fmt:message key="lesson.test.export" bundle="${testBundle}"/></button>
								</a>								
				    		</div>			
						</div><!-- Export Panel -->
					</c:if>
				</div>
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
	</form>	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script type="text/javascript">
		<!--	    
		var msg = {};
		msg['lesson.test.question.delete.confirm'] = 	"<fmt:message key="lesson.test.question.delete.confirm" bundle="${testBundle}"/>";
		msg['lesson.test.delete.confirm'] = 			"<fmt:message key="lesson.test.delete.confirm" bundle="${testBundle}"/>";
		msg['lesson.test.publish.confirm'] = 			"<fmt:message key="lesson.test.publish.confirm" bundle="${testBundle}"/>";
		msg['lesson.test.unpublish.confirm'] = 			"<fmt:message key="lesson.test.unpublish.confirm" bundle="${testBundle}"/>";		 
		//-->
	</script>
	<script src="/resources/js/editTest.js"></script>
	<script src="/resources/js/confirm.js"></script>
	
</body>
</html>