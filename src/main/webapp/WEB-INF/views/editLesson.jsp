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
<link rel="stylesheet" href="<c:url value="/resources/css/editLesson.css"/>"/>
<title>TeachOnSnap - ${lesson.title}</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">	
		<div class="row">
			<form role="form">
				<div class="col-sm-7">
					<div class="form-group">
						<label for="inputLessonTitle"><fmt:message key="lesson.form.title" bundle="${editLessonBundle}"/></label>
				    	<input type="text" name="title" id="inputLessonTitle" class="form-control" maxlength="140"
				    		placeholder="<fmt:message key="lesson.form.title" bundle="${editLessonBundle}"/>" 
				    		required value="${lesson.title}">				    	
			    	</div>			
			    	<div id="radioLang" class="form-group">
			    		<label for="radioLang"><fmt:message key="lesson.form.lang" bundle="${editLessonBundle}"/></label>
			    		<span> 
							<input type="radio" name="lang" id="radioLessonLangES" value="2" required checked="checked" />
							<img alt="ES" src="/resources/img/ico/flag_es.jpg"/>
						</span>
						<span>
							<input type="radio" name="lang" id="radioLessonLangEN" value="1" required/>
							<img alt="EN" src="/resources/img/ico/flag_en.jpg"/>
						</span>										    					    	
			    	</div>
			    	
					<div class="panel panel-default">
		    			
				    	<div class="panel-body" role="tabpanel">
							<!-- Nav tabs -->
					  		<ul class="nav nav-tabs" role="tablist">
					    		<li role="presentation" class="active"><a href="#video" aria-controls="video" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-facetime-video"></span> video</a></li>
					    		<li role="presentation"><a href="#audio" aria-controls="audio" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-bullhorn"></span> audio</a></li>
					    		<li role="presentation"><a href="#nofiles" aria-controls="nofiles" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-ban-circle"></span> No files</a></li>				    		
				    		</ul>
					
					  		<!-- Tab panes -->
							<div class="tab-content">
					  			<div role="tabpanel" class="tab-pane fade in active" id="video">
						  			<div class="form-group">			    					
										<div id="dropzone_video" class="well">Drop video files here</div>
										<p id="uploadFile_video" class="help-block"></p>
										<div class="progress hidden" id="progress_video">
											<div id="progressbar_video" class="progress-bar progress-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
												<span id="progressbar_bw_video" class="sr-only">0%</span>
											</div>
										</div>  									
										<table id="uploaded-files_video" class="help-block table">
											<tr><td>Selecciona un fichero de video o arrástralo hasta la zona marcada.</td></tr>										
										</table>															
				   						<span class="btn btn-default btn-file">
											Examinar... <input type="file" id="fileupload_video" name="files_video" data-url="/upload/video" data-accept-file-types="/^video\/.*$/"/>
										</span>
										<h5><small>Max File Size: 2 Mb - Display last 20 files</small></h5> 
	  								</div>				  						
				  					<input type="radio" name="attach" id="inputRadioAttach_video" value="video" required checked="checked"/>			  					
					  			</div>
					  			<div role="tabpanel" class="tab-pane fade" id="audio">
					  				<div class="form-group">			    					
										<div id="dropzone_audio" class="well">Drop audio files here</div>
										<p id="uploadFile_audio" class="help-block"></p>
										<div class="progress hidden" id="progress_audio">
											<div id="progressbar_audio" class="progress-bar progress-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
												<span id="progressbar_bw_audio" class="sr-only">0%</span>
											</div>
										</div>  									
										<table id="uploaded-files_audio" class="help-block table">
											<tr><td>Selecciona un fichero de audio o arrástralo hasta la zona marcada.</td></tr>										
										</table>															
				   						<span class="btn btn-default btn-file">
											Examinar... <input type="file" id="fileupload_audio" name="files_audio" data-url="/upload/audio" data-accept-file-types="/^audio\/.*$/"/>
										</span>
										<h5><small>Max File Size: 2 Mb - Display last 20 files</small></h5> 
	  								</div>	
					  				<input type="radio" name="attach" id="inputRadioAttach_audio" value="audio" required/>
					  			</div>
					  			<div role="tabpanel" class="tab-pane fade" id="nofiles">
					  				<input type="radio" name="attach" id="inputRadioAttach_nofiles" value="none" required/>
					  				<p class="help-block">No se adjuntarán ficheros multimedia a la lección.</p>
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
					
			    	<div class="form-group">
						<label for="textareaLessonText"><fmt:message key="lesson.form.text" bundle="${editLessonBundle}"/></label>
				    	<textarea name="text" id="textareaLessonText" class="form-control" maxlength="140" 
				    		placeholder="<fmt:message key="lesson.form.text" bundle="${editLessonBundle}"/>">${lesson.text}</textarea>
				    	<p class="help-block">Recuerda: Un máximo de 140 caracteres.</p>				    	
			    	</div>
	    					             	
		          	<div class="panel panel-default">
		        		<div class="panel-heading">Tags</div>
		    			<div class="panel-body">
		    				<div class="form-group form-inline">	
		    			    	<input type="text" id="inputLessonTag" class="form-control" 
						    		placeholder="<fmt:message key="lesson.form.addTag" bundle="${editLessonBundle}"/>">
						    	<button id="addTag" type="button" class="btn btn-default"><fmt:message key="lesson.form.addTag" bundle="${editLessonBundle}"/>
						    	 <span class="glyphicon glyphicon-tags"></span>
				    	 		</button>
				    	 	</div>

			    			<div class="form-group">
		    					<div id="tags">
		    						<c:forEach items="${tags}" var="tag">
										<span class="label label-default" onclick="this.remove();$('#tag_${tag.MD5}').remove();">${tag.tag}</span>  									
									</c:forEach>
								</div>
								<p class="help-block">Pincha sobre los tags para eliminarlos.</p>
		    					<select multiple="multiple" id="formTags" hidden="true" name="tags">
			    					<c:forEach items="${tags}" var="tag">
			    						<option id="tag_${tag.MD5}" selected="selected">${tag.tag}</option>										  									
									</c:forEach>
		    					</select>	    								
							</div>
				    	</div>
			    	</div>
		          	
		          		<div class="panel panel-default">
			    		<div class="panel-heading"><fmt:message key="lesson.source.heading" bundle="${lessonBundle}"/></div>
		    			<div class="panel-body">
	    					<table class="table table-striped" id="sources">
	    					<c:forEach items="${sourceLinks}" var="link">	
	    						<tr onclick="this.remove();$('#sources_${link.MD5}').remove();"><td>${link.desc} - ${link.URL}</td></tr>
							</c:forEach>     		    							    					
	    					</table>
    						<p class="help-block">Añade URLs, si te equivocas puedes hacer click sobre ellas para eliminarlas.</p>
	    					<div class="form-group form-inline">				    			
				    			<input type="text" id="inputLessonSource" class="form-control" 
					    			placeholder="Source URL">
					    		<button id="addSource" type="button" class="btn btn-default">Add URL</button>
					    	</div>
							<select multiple="multiple" id="formSources" hidden="true" name="sources">
								<c:forEach items="${sourceLinks}" var="link">	
									<option id="sources_${link.MD5}" selected="selected">${link.desc} - ${link.URL}</option>
								</c:forEach>
							</select>
						</div>
			    	</div>
			    				    	
			    	<div class="panel panel-default">
			    		<div class="panel-heading"><fmt:message key="lesson.moreInfo.heading" bundle="${lessonBundle}"/></div>
		    			<div class="panel-body">
	    					<table class="table table-striped" id="moreInfo">	
	    					<c:forEach items="${moreInfoLinks}" var="link">		
								<tr onclick="this.remove();$('#more_${link.MD5}').remove();"><td>${link.desc} - ${link.URL}</td></tr>									
							</c:forEach>     	    							    					
	    					</table>
    						<p class="help-block">Añade URLs, si te equivocas puedes hacer click sobre ellas para eliminarlas.</p>
	    					<div class="form-group form-inline">				    			
				    			<input type="text" id="inputLessonMoreInfo" class="form-control" 
					    			placeholder="More Info URL">
					    		<button id="addMoreInfo" type="button" class="btn btn-default">Add URL</button>
					    	</div>
							<select multiple="multiple" id="formMoreInfo" hidden="true" name="moreInfos">
								<c:forEach items="${moreInfoLinks}" var="link">	
									<option id="more_${link.MD5}" selected="selected">${link.desc} - ${link.URL}</option>
								</c:forEach>
							</select>
						</div>
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
					
		        </div><!-- col -->
	
	        	<div class="col-sm-4 col-sm-offset-1">	        		
	        		<div class="sidebar">
						<h4><fmt:message key="lesson.command.heading" bundle="${lessonBundle}"/></h4>
						<button class="btn btn-primary" type="submit"><span class="glyphicon glyphicon-save"></span>
						 <fmt:message key="lesson.form.save" bundle="${editLessonBundle}"/></button>
						 
						 <c:if test="${lesson.idLessonTest>0}">
						<div class="sidebar">
							<h4><fmt:message key="lesson.test.heading" bundle="${lessonBundle}"/></h4>
							<a href="${lesson.editTestURL}"><button class="btn btn-default" type="button">
							 	<span class="glyphicon glyphicon-edit"></span>
							 	 Edit test</button>
						 	</a>
						</div>
						</c:if>
						<c:if test="${lesson.idLessonTest<=0}">
						<div class="sidebar">
							<h4><fmt:message key="lesson.test.heading" bundle="${lessonBundle}"/></h4>
							<a href="${lesson.editTestURL}"><button class="btn btn-default" type="button">
							 	<span class="glyphicon glyphicon-edit"></span>
							 	 New test</button>
						 	</a>
						</div>
						</c:if>
					</div>			
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
	<script src="/resources/js/editLesson.js"></script>
	
	
</body>
</html>