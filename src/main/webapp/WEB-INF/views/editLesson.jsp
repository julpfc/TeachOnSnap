<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.editLesson" var="editLessonBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.lesson" var="lessonBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.test" var="testBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>
 
<!DOCTYPE html>
<html>
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/editLesson.css"/>"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/lesson.css"/>"/>
	<title><fmt:message key="app.name"/> - 
		<c:choose>
			<c:when test="${lesson.id>0}">
				${lesson.title}
			</c:when>
			<c:otherwise>
				<fmt:message key="lesson.new.heading" bundle="${editLessonBundle}"/>
			</c:otherwise>
		</c:choose>
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">	
		<div class="row">
			<form id="lessonForm" role="form" method="POST"> 
	<c:import url="./import/confirm.jsp"/>
				<div class="col-sm-7">
					<!-- Title -->					
					<div class="form-group">
						<label for="inputLessonTitle"><fmt:message key="lesson.form.title" bundle="${editLessonBundle}"/></label>
				    	<input type="text" name="title" id="inputLessonTitle" class="form-control" maxlength="140" 
				    		placeholder="<fmt:message key="lesson.form.title" bundle="${editLessonBundle}"/>" 
				    		required value="${lesson.title}">				    	
			    	</div>		
			    	
			    	<!-- Lang -->	
			    	<div id="radioLang" class="form-group">
			    		<label for="radioLang"><fmt:message key="lesson.form.lang" bundle="${editLessonBundle}"/></label>
			    		<c:forEach items="${languages}" var="lang" varStatus="loop">
				    		<span>  
								<input type="radio" name="lang" id="radioLessonLang${lang.language}" value="${lang.id}" ${lang.id == userLang.id?'checked="checked"':''} required />
								<img alt="${lang.language}" src="/resources/img/ico/flag_${lang.language}.jpg"/>
							</span>						
			    		</c:forEach>			    												    					    	
			    	</div>
			    	
			    	<!-- Media -->
					<div class="panel panel-default">
		    			
				    	<div class="panel-body" role="tabpanel">
							<!-- Nav tabs -->
					  		<ul class="nav nav-tabs" role="tablist">
					    		<li role="presentation" class="active"><a href="#video" aria-controls="video" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-facetime-video"></span> <fmt:message key="lesson.form.media.video" bundle="${editLessonBundle}"/></a></li>
					    		<li role="presentation"><a href="#audio" aria-controls="audio" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-bullhorn"></span> <fmt:message key="lesson.form.media.audio" bundle="${editLessonBundle}"/></a></li>
					    		<li role="presentation"><a href="#nofiles" aria-controls="nofiles" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-ban-circle"></span> <fmt:message key="lesson.form.media.nofiles" bundle="${editLessonBundle}"/></a></li>				    		
				    		</ul>
					
					  		<!-- Tab panes -->
							<div class="tab-content">
					  			<div role="tabpanel" class="tab-pane fade in active" id="video">
						  			<div class="form-group">			    					
										<div id="dropzone_video" class="well"><fmt:message key="lesson.form.media.video.drop" bundle="${editLessonBundle}"/></div>
										<p id="uploadFile_video" class="help-block"></p>
										<div class="progress hidden" id="progress_video">
											<div id="progressbar_video" class="progress-bar progress-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
												<span id="progressbar_bw_video" class="sr-only">0%</span>
											</div>
										</div>  									
										<table id="uploaded-files_video" class="help-block table">
											<tr><td><fmt:message key="lesson.form.media.video.select" bundle="${editLessonBundle}"/></td></tr>										
										</table>															
				   						<span class="btn btn-default btn-file">
											<fmt:message key="lesson.form.media.browse" bundle="${editLessonBundle}"/> <input type="file" id="fileupload_video" name="files_video" data-url="/upload/video" data-accept-file-types="/^video\/.*$/"/>
										</span>
										<h5><small><fmt:message key="lesson.form.media.tip" bundle="${editLessonBundle}"/></small></h5> 
	  								</div>				  						
				  					<input type="radio" name="attach" id="inputRadioAttach_video" value="video" required checked="checked"/>			  					
					  			</div>
					  			<div role="tabpanel" class="tab-pane fade" id="audio">
					  				<div class="form-group">			    					
										<div id="dropzone_audio" class="well"><fmt:message key="lesson.form.media.audio.drop" bundle="${editLessonBundle}"/></div>
										<p id="uploadFile_audio" class="help-block"></p>
										<div class="progress hidden" id="progress_audio">
											<div id="progressbar_audio" class="progress-bar progress-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
												<span id="progressbar_bw_audio" class="sr-only">0%</span>
											</div>
										</div>  									
										<table id="uploaded-files_audio" class="help-block table">
											<tr><td><fmt:message key="lesson.form.media.audio.select" bundle="${editLessonBundle}"/></td></tr>										
										</table>															
				   						<span class="btn btn-default btn-file">
											<fmt:message key="lesson.form.media.browse" bundle="${editLessonBundle}"/> <input type="file" id="fileupload_audio" name="files_audio" data-url="/upload/audio" data-accept-file-types="/^audio\/.*$/"/>
										</span>
										<h5><small><fmt:message key="lesson.form.media.tip" bundle="${editLessonBundle}"/></small></h5> 
	  								</div>	
					  				<input type="radio" name="attach" id="inputRadioAttach_audio" value="audio" required/>
					  			</div>
					  			<div role="tabpanel" class="tab-pane fade" id="nofiles">
					  				<input type="radio" name="attach" id="inputRadioAttach_nofiles" value="none" required/>
					  				<p class="help-block"><fmt:message key="lesson.form.media.nofiles.tip" bundle="${editLessonBundle}"/></p>
				  				</div>
							</div>				
						</div>
					</div>
					
					<div>
						<c:if test="${lesson.idLessonMedia>0}">					
				     		<div class="lesson-video">		     		
				     			<c:set var="firstVideo" value="${medias[0]}"/>
				     			<video src="${firstVideo.URL}" id="lesson_video" controls="controls" poster="" height="auto" width="100%">
					     			<c:forEach items="${medias}" var="media">		
				       					<source src="${media.URL}" type='video/mp4'/>							    							
									</c:forEach>
								</video>   
				     		</div>         		
						</c:if>												
					</div>
					
					<!-- Text -->
			    	<div class="form-group">
						<label for="textareaLessonText"><fmt:message key="lesson.form.text" bundle="${editLessonBundle}"/></label>
				    	<textarea name="text" id="textareaLessonText" class="form-control" maxlength="140" 
				    		placeholder="<fmt:message key="lesson.form.text" bundle="${editLessonBundle}"/>">${lesson.text}</textarea>
				    	<p class="help-block"><fmt:message key="lesson.form.text.tip" bundle="${editLessonBundle}"/></p>				    	
			    	</div>
	    				
	    			<!-- Tags -->	             	
		          	<div class="panel panel-default">
		        		<div class="panel-heading"><span class="glyphicon glyphicon-tags"></span> <fmt:message key="lesson.form.tags" bundle="${editLessonBundle}"/></div>
		    			<div class="panel-body">
		    				<div class="form-group form-inline">	
		    			    	<input type="text" id="inputLessonTag" class="form-control"
						    		placeholder="<fmt:message key="lesson.form.tags.add" bundle="${editLessonBundle}"/>">
						    	<button id="addTag" type="button" class="btn btn-default"><fmt:message key="lesson.form.tags.add" bundle="${editLessonBundle}"/></button>
				    	 	</div>

			    			<div class="form-group">
		    					<div id="tags">		    											
		    						<c:forEach items="${tags}" var="tag">
										<span class="label label-default" onclick="this.remove();$('#tag_${tag.MD5}').remove();">${tag.tag}</span>  									
									</c:forEach>
								</div>
								<p class="help-block"><fmt:message key="lesson.form.tags.tip" bundle="${editLessonBundle}"/></p>
		    					<select multiple="multiple" id="formTags" hidden="true" name="tags">
			    					<c:forEach items="${tags}" var="tag">
			    						<option id="tag_${tag.MD5}" selected="selected">${tag.tag}</option>										  									
									</c:forEach>
		    					</select>	    								
							</div>
				    	</div>
			    	</div>
		          	
		          	<!-- Source links -->
		          	<div class="panel panel-default">
			    		<div class="panel-heading"><span class="glyphicon glyphicon-education"></span> <fmt:message key="lesson.source.heading" bundle="${lessonBundle}"/></div>
		    			<div class="panel-body">
	    					<table class="table table-striped" id="sources">
	    					<c:forEach items="${sourceLinks}" var="link">	
	    						<tr onclick="this.remove();$('#sources_${link.MD5}').remove();"><td><span class="glyphicon glyphicon-link"></span> ${link.desc} - ${link.URL}</td></tr>
							</c:forEach>     		    							    					
	    					</table>
	    					<div class="form-group form-inline">				    			
				    			<input type="text" id="inputLessonSource" class="form-control" 
					    			placeholder="Source URL">
					    		<button id="addSource" type="button" class="btn btn-default"><fmt:message key="lesson.form.source.add" bundle="${editLessonBundle}"/></button>
					    	</div>
    						<p class="help-block"><fmt:message key="lesson.form.source.tip" bundle="${editLessonBundle}"/></p>
							<select multiple="multiple" id="formSources" hidden="true" name="sources">
								<c:forEach items="${sourceLinks}" var="link">	
									<option id="sources_${link.MD5}" selected="selected">${link.desc} - ${link.URL}</option>
								</c:forEach>
							</select>
						</div>
			    	</div>
			    				
			    	<!-- More info links -->    	
			    	<div class="panel panel-default">
			    		<div class="panel-heading"><span class="glyphicon glyphicon-globe"></span> <fmt:message key="lesson.moreInfo.heading" bundle="${lessonBundle}"/></div>
		    			<div class="panel-body">
	    					<table class="table table-striped" id="moreInfo">	
	    					<c:forEach items="${moreInfoLinks}" var="link">		
								<tr onclick="this.remove();$('#more_${link.MD5}').remove();"><td><span class="glyphicon glyphicon-link"></span> ${link.desc} - ${link.URL}</td></tr>									
							</c:forEach>     	    							    					
	    					</table>
	    					<div class="form-group form-inline">				    			
				    			<input type="text" id="inputLessonMoreInfo" class="form-control" 
					    			placeholder="More Info URL">
					    		<button id="addMoreInfo" type="button" class="btn btn-default"><fmt:message key="lesson.form.moreinfo.add" bundle="${editLessonBundle}"/></button>
					    	</div>
    						<p class="help-block"><fmt:message key="lesson.form.moreinfo.tip" bundle="${editLessonBundle}"/></p>
							<select multiple="multiple" id="formMoreInfo" hidden="true" name="moreInfos">
								<c:forEach items="${moreInfoLinks}" var="link">	
									<option id="more_${link.MD5}" selected="selected">${link.desc} - ${link.URL}</option>
								</c:forEach>
							</select>
						</div>
			    	</div>
			    	
			    	<!-- Recommended -->
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
					
		        </div><!-- col -->
	
				<!-- Sidebar -->
	        	<div class="col-sm-5">	        		
	        		<div class="sidebar">
						<h4><fmt:message key="lesson.command.heading" bundle="${lessonBundle}"/></h4>
						<button class="btn btn-primary" type="submit"><span class="glyphicon glyphicon-save"></span>
						 <fmt:message key="lesson.form.save" bundle="${editLessonBundle}"/></button>
					</div>
					
					<!-- Test Panel -->
					<c:if test="${lesson.id > 0}">
						<div class="sidebar">
							<div class="panel panel-default">
					    		<div class="panel-heading">
					    			 <c:choose>
									 	<c:when test="${not empty test}">
										 	<span><fmt:message key="lesson.test.heading" bundle="${testBundle}"/></span>				    			
					    					<a href="${test.editURL}">
					    						<button class="btn btn-default btn-xs pull-right" type="button">
													<span class="glyphicon glyphicon-edit"></span>								
													 <fmt:message key="lesson.test.edit" bundle="${testBundle}"/>
												</button>
											</a>										 	
										</c:when>
										<c:otherwise>
											<span><fmt:message key="lesson.test.heading" bundle="${testBundle}"/></span>				    			
					    					<a href="${lesson.newTestURL}">
					    						<button class="btn btn-default btn-xs pull-right" type="button">
													<span class="glyphicon glyphicon-edit"></span>
											 		 <fmt:message key="lesson.test.new" bundle="${testBundle}"/>
												</button>
											</a>
										</c:otherwise>
									 </c:choose>		    		
					    		</div>
				    			<c:if test="${not empty test}">
				    				<div class="panel-body">
										<c:if test="${test.multipleChoice}">
											<h5><span class="glyphicon glyphicon-exclamation-sign"></span>
		     								 <fmt:message key="lesson.test.multiplechoice" bundle="${testBundle}"/>
		     								</h5>	     							
		     							</c:if>
										<h5><span class="label label-info">${test.numAnswers}</span> <fmt:message key="lesson.test.numAnswers" bundle="${testBundle}"/></h5>																	
										<h5><span class="label label-info">${test.numQuestions}</span> <fmt:message key="lesson.test.numQuestions" bundle="${testBundle}"/></h5>
									</div>
								</c:if>
								<c:if test="${not empty test}">
									<div class="panel-footer">										     								    			
										 <c:choose>
										 	<c:when test="${test.draft}">
										 		<span class="label label-warning"><fmt:message key="lesson.test.unpublished" bundle="${testBundle}"/></span>
										 		<a href="${test.editURL}?publishTest=true">
								    				<button class="btn btn-success btn-xs pull-right" type="button">
										 			<span class="glyphicon glyphicon-eye-open"></span>
										 			 <fmt:message key="lesson.test.publish" bundle="${testBundle}"/>
										 			 </button>
												</a>
										 	</c:when>
										 	<c:otherwise>
										 		<span class="label label-success"><fmt:message key="lesson.test.published" bundle="${testBundle}"/></span>
								    			<a href="${test.editURL}?publishTest=false">
								    				<button class="btn btn-warning btn-xs pull-right" type="button">											
											 		<span class="glyphicon glyphicon-eye-close"></span>
													 <fmt:message key="lesson.test.unpublish" bundle="${testBundle}"/>			 	
													</button>
												</a>
										 	</c:otherwise>
										 </c:choose>										 	
		     						</div>
		     					</c:if>
					    	</div>					
						</div><!-- Test Panel -->							
					</c:if>
	        	</div><!-- sidebar -->
	        </form>
		</div><!-- /.row -->
    </div><!-- /.container -->
	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
	<script src="/resources/js/ext/jquery.ui.widget.js"></script>
	<script src="/resources/js/ext/jquery.iframe-transport.js"></script>
	<script src="/resources/js/ext/jquery.fileupload.js"></script>
	<script src="/resources/js/ext/jquery.fileupload-validate.js"></script>
	<script src="https://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/md5.js"></script>
		<script type="text/javascript">
		<!--	    
		var msg = {};
		msg['lesson.confirm'] = 		"<c:choose><c:when test="${lesson.id > 0}"><fmt:message key="lesson.save.confirm" bundle="${editLessonBundle}"/></c:when><c:otherwise><fmt:message key="lesson.create.confirm" bundle="${editLessonBundle}"/></c:otherwise></c:choose>";
		msg['lesson.save.confirm'] = 	"<fmt:message key="lesson.save.confirm" bundle="${editLessonBundle}"/>";
		//-->
	</script>
	<script src="/resources/js/editLesson.js"></script>
	<script src="/resources/js/confirm.js"></script>
	
	
</body>
</html>