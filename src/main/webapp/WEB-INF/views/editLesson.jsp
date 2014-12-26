<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.editLesson" var="editLessonBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.lesson" var="lessonBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>
 
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/lesson.css"/>"/>
<title>TeachOnSnap - ${lesson.title}</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">	
		<div class="row">
			<form role="form">
				<div class="col-sm-7">
					<div class="form-group">
						<label for="inputLessonTitle" class="${not empty lesson?'':'sr-only'}"><fmt:message key="lesson.form.title" bundle="${editLessonBundle}"/></label>
				    	<input type="text" name="title" id="inputLessonTitle" class="form-control" 
				    		placeholder="<fmt:message key="lesson.form.title" bundle="${editLessonBundle}"/>" 
				    		value="${lesson.title}" required>				    	
			    	</div>
			    	<div class="form-group">
						<label for="selectLessonLang" class="${not empty lesson?'':'sr-only'}"><fmt:message key="lesson.form.lang" bundle="${editLessonBundle}"/></label>				    	
				    	<select name="lang" id="selectLessonLang" class="form-control">
  							<option>${lesson.language.language}</option>
  							<option>2</option>  							
						</select>				    	
			    	</div>
			    	
					<div class="form-group">
    					<label for="exampleInputFile">Video File input</label>
    					<input type="file" id="exampleInputFile" value="${lesson.idLessonVideo}">
    					<p class="help-block">Example block-level help text here.</p>
  					</div>
				
					<div>
						<c:if test="${lesson.idLessonVideo>0}">					
				     		<div class="lesson-video">		     		
				     			<c:set var="firstVideo" value="${videos[0]}"/>
				     			<video src="${firstVideo.URL}" id="lesson_video" controls="controls" poster="" height="auto" width="100%">
					     			<c:forEach items="${videos}" var="video">		
				       					<source src="${video.URL}" type='video/mp4'/>							    							
									</c:forEach>
								</video>   
				     		</div>         		
						</c:if>												
					</div>
					<div class="form-group">
						<label for="textareaLessonText" class="${not empty lesson?'':'sr-only'}"><fmt:message key="lesson.form.text" bundle="${editLessonBundle}"/></label>
				    	<textarea name="text" id="textareaLessonText" class="form-control" 
				    		placeholder="<fmt:message key="lesson.form.text" bundle="${editLessonBundle}"/>">${lesson.text}</textarea>				    	
			    	</div>
	    					             	
		          	<div class="form-group">
						<label for="inputLessonTag" class="${not empty lesson?'':'sr-only'}"><fmt:message key="lesson.form.addTag" bundle="${editLessonBundle}"/></label>
				    	<input type="text" name="tag" id="inputLessonTag" class="form-control" 
				    		placeholder="<fmt:message key="lesson.form.addTag" bundle="${editLessonBundle}"/>">				    	
		          		<span class="label label-default"><span class="glyphicon glyphicon-tags"></span></span>
		          		<c:forEach items="${tags}" var="tag">		
							<span class="label label-default"><a href="${tag.URL}">${tag.tag}</a></span>									
						</c:forEach>
			    	</div>
			    	
			    		
		          	<c:if test="${not empty linkedLessons}">
			          	<div class="sidebar"> 	
							<h4><fmt:message key="lesson.linkedLessons.heading" bundle="${lessonBundle}"/></h4>
							<ol class="list-unstyled">
		            			<c:forEach items="${linkedLessons}" var="linkedlesson">		
									<li><a href="${linkedlesson.URL}">${linkedlesson.title}</a></li>									
								</c:forEach>              			
		            		</ol>
						</div>
					</c:if>
	          		<c:if test="${not empty moreInfoLinks}">
		          		<div class="sidebar">
		               		<h4><fmt:message key="lesson.moreInfo.heading" bundle="${lessonBundle}"/></h4>
		            		<ol class="list-unstyled">
		            			<c:forEach items="${moreInfoLinks}" var="link">		
									<li><a href="${link.URL}">${link.desc}</a></li>									
								</c:forEach>              			
		            		</ol>
		          		</div>
		          	</c:if>
	          		<c:if test="${not empty sourceLinks}">
		          		<div class="sidebar">
		               		<h4><fmt:message key="lesson.source.heading" bundle="${lessonBundle}"/></h4>
		            		<ol class="list-unstyled">
		            			<c:forEach items="${sourceLinks}" var="link">		
									<li><a href="${link.URL}">${link.desc}</a></li>									
								</c:forEach>              			
		            		</ol>
		          		</div>
		          	</c:if>          	
				
		        </div><!-- col -->
	
	        	<div class="col-sm-4 col-sm-offset-1">	        		
	        		<div class="sidebar">
						<h4><fmt:message key="lesson.command.heading" bundle="${lessonBundle}"/></h4>
						<button class="btn btn-primary" type="submit"><span class="glyphicon glyphicon-save"></span>
						 <fmt:message key="lesson.form.save" bundle="${editLessonBundle}"/></button>
					</div>			
	        	</div><!-- sidebar -->
	        </form>
		</div><!-- /.row -->
    </div><!-- /.container -->
	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
	
</body>
</html>