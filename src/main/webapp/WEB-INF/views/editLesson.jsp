<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
				${fn:escapeXml(lesson.title)}
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
				    		required value="${fn:escapeXml(lesson.title)}">				    	
			    	</div>		
			    	
			    	<!-- Lang -->	
			    	<div id="radioLang" class="form-group">
			    		<label for="radioLang"><fmt:message key="lesson.form.lang" bundle="${editLessonBundle}"/></label>
			    		<c:forEach items="${languages}" var="lang" varStatus="loop">
				    		<span>  
								<input type="radio" name="lang" id="radioLessonLang${lang.language}" value="${lang.id}" ${(lesson!=null)?(lang.id == lesson.language.id?'checked="checked"':''):(lang.id == userLang.id?'checked="checked"':'')} required />
								<img alt="${lang.language}" src="/resources/img/ico/flag_${lang.language}.jpg"/>
							</span>						
			    		</c:forEach>			    												    					    	
			    	</div>
			    	
			    	<!-- Media -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<span class="glyphicon glyphicon-paperclip"></span> <fmt:message key="lesson.form.media.heading" bundle="${editLessonBundle}"/>
							<button title="" data-original-title="" type="button" class="btn btn-xs btn-default pull-right" data-container="body" 
								data-toggle="popover" data-placement="bottom" data-trigger="focus"
								data-content="<fmt:message key="lesson.form.media.help" bundle="${editLessonBundle}"/>">?</button>
						</div>
						
						<div class="panel-body">				    	
							<c:if test="${lesson.idLessonMedia>0}">
					     		<c:set var="firstMedia" value="${medias[0]}"/>
					     		<div id="mediaDiv">
						     		<div class="lesson-media">		     		
						     			<c:choose>
						     				<c:when test="${lesson.mediaType == 'VIDEO'}">
								     			<video src="${firstMedia.URL}" id="lesson_media" controls="controls" poster="" height="auto" width="100%">
									     			<c:forEach items="${medias}" var="media">		
								       					<source src="${media.URL}" type="${media.mimetype}"/>							    							
													</c:forEach>
												</video>   
						     				</c:when>
						     				<c:when test="${lesson.mediaType == 'AUDIO'}">
						     					<audio src="${firstMedia.URL}" id="lesson_media" controls="controls">
									     			<c:forEach items="${medias}" var="media">		
								       					<source src="${media.URL}" type="${media.mimetype}"/>							    							
													</c:forEach>
												</audio>
						     				</c:when>
						     				<c:when test="${lesson.mediaType == 'IMAGE'}">
						     					<img class="img-thumbnail" id="lesson_media" alt="${firstMedia.filename}" src="${firstMedia.URL}"/>						     					
						     				</c:when>					     								     			
						     			</c:choose>
						     		</div>
						     		<table id="media-files" class="table">
						     			<fmt:formatNumber maxFractionDigits="2" value="${firstMedia.filesize/1024/1024}" var="mediaFileSize"/>
										<tr>
											<td></td>
											<td>${firstMedia.filename}</td>
											<c:choose>
							     				<c:when test="${lesson.mediaType == 'VIDEO'}">
									     			<td><span class='glyphicon glyphicon-film'></span></td>   
							     				</c:when>
							     				<c:when test="${lesson.mediaType == 'AUDIO'}">
							     					<td><span class='glyphicon glyphicon-volume-up'></span></td>
							     				</c:when>
							     				<c:when test="${lesson.mediaType == 'IMAGE'}">
							     					<td><span class='glyphicon glyphicon-picture'></span></td>
							     				</c:when>					     								     			
						     				</c:choose>											
											<td></td>
										</tr> 
										<tr>
											<td></td>
											<td><a href='${firstMedia.URL}'><span class='glyphicon glyphicon-download'></span> <fmt:message key="lesson.form.media.download" bundle="${editLessonBundle}"/></a></td>
											<td>${mediaFileSize}</td>
											<td>MB</td>
										</tr>
										<tr>
											<td></td>
											<td><a id="removeMedia"><span class='glyphicon glyphicon-remove'></span> <fmt:message key="lesson.form.media.remove" bundle="${editLessonBundle}"/></a></td>
											<td></td>
											<td></td>
											
										</tr>
	    							</table>  
    							</div>
							</c:if>
							<div id="uploadDiv" class="form-group ${lesson.idLessonMedia>0?'hidden':''}">			    					
								<div id="dropzone" class="well"><fmt:message key="lesson.form.media.drop" bundle="${editLessonBundle}"/></div>
								<p id="uploadFile" class="help-block"></p>
								<div class="progress hidden" id="progress_video">
									<div id="progressbar" class="progress-bar progress-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
										<span id="progressbar_bw" class="sr-only">0%</span>
									</div>
								</div>  									
								<table id="uploaded-files" class="table">
									<tr><td><fmt:message key="lesson.form.media.select" bundle="${editLessonBundle}"/></td></tr>										
								</table>															
				   				<span class="btn btn-default btn-file">
									<fmt:message key="lesson.form.media.browse" bundle="${editLessonBundle}"/> <input type="file" id="fileupload" name="files" data-url="/upload/"/>
								</span>								
	  						</div>
	  					</div>				  	
	  					<div class="panel-footer">
							<h5><small><fmt:message key="lesson.form.media.tip" bundle="${editLessonBundle}"/> ${maxUploadFileSize/1024/1024} MB</small></h5> 
	  					</div>					
					</div>						

					
					<!-- Text -->
			    	<div class="form-group">
						<label for="textareaLessonText"><fmt:message key="lesson.form.text" bundle="${editLessonBundle}"/>
						 <a title="" data-original-title="" data-container="body" 
								data-toggle="popover" data-placement="bottom"
								data-content="<fmt:message key="lesson.form.text.help" bundle="${editLessonBundle}"/>">?</a>
						</label>
				    	<textarea name="text" id="textareaLessonText" class="form-control" maxlength="140" 
				    		placeholder="<fmt:message key="lesson.form.text" bundle="${editLessonBundle}"/>">${fn:escapeXml(lesson.text)}</textarea>
				    	<p class="help-block"><fmt:message key="lesson.form.text.tip" bundle="${editLessonBundle}"/></p>				    	
			    	</div>
	    				
	    			<!-- Tags -->	             	
		          	<div class="panel panel-default">
		        		<div class="panel-heading"><span class="glyphicon glyphicon-tags"></span> <fmt:message key="lesson.form.tags" bundle="${editLessonBundle}"/>
		        			<button title="" data-original-title="" type="button" class="btn btn-xs btn-default pull-right" data-container="body" 
								data-toggle="popover" data-placement="bottom" data-trigger="focus"
								data-content="<fmt:message key="lesson.form.tag.help" bundle="${editLessonBundle}"/>">?</button>
		        		</div>
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
			    		<div class="panel-heading"><span class="glyphicon glyphicon-education"></span> <fmt:message key="lesson.source.heading" bundle="${lessonBundle}"/>
			    			<button title="" data-original-title="" type="button" class="btn btn-xs btn-default pull-right" data-container="body" 
								data-toggle="popover" data-placement="bottom" data-trigger="focus"
								data-content="<fmt:message key="lesson.form.source.help" bundle="${editLessonBundle}"/>">?</button>
			    		</div>
		    			<div class="panel-body">
	    					<table class="table table-striped" id="sources">
	    					<c:forEach items="${sourceLinks}" var="link">	
	    						<tr onclick="this.remove();$('#sources_${link.MD5}').remove();"><td><span class="glyphicon glyphicon-link"></span> ${link.desc} - ${link.URL}</td></tr>
							</c:forEach>     		    							    					
	    					</table>
	    					<div class="form-group form-inline">				    			
				    			<input type="text" id="inputLessonSource" class="form-control" 
					    			placeholder="<fmt:message key="lesson.form.source.add" bundle="${editLessonBundle}"/>">
					    		<button id="addSource" type="button" class="btn btn-default"><fmt:message key="lesson.form.source.add" bundle="${editLessonBundle}"/></button>
					    	</div>
    						<p class="help-block"><fmt:message key="lesson.form.source.tip" bundle="${editLessonBundle}"/></p>
							<select multiple="multiple" id="formSources" hidden="true" name="sources">
								<c:forEach items="${sourceLinks}" var="link">	
									<option id="sources_${link.MD5}" selected="selected">${link.URL}</option>
								</c:forEach>
							</select>
						</div>
			    	</div>
			    				
			    	<!-- More info links -->    	
			    	<div class="panel panel-default">
			    		<div class="panel-heading"><span class="glyphicon glyphicon-globe"></span> <fmt:message key="lesson.moreInfo.heading" bundle="${lessonBundle}"/>
			    			<button title="" data-original-title="" type="button" class="btn btn-xs btn-default pull-right" data-container="body" 
								data-toggle="popover" data-placement="bottom" data-trigger="focus"
								data-content="<fmt:message key="lesson.form.moreinfo.help" bundle="${editLessonBundle}"/>">?</button>
			    		</div>
		    			<div class="panel-body">
	    					<table class="table table-striped" id="moreInfo">	
	    					<c:forEach items="${moreInfoLinks}" var="link">		
								<tr onclick="this.remove();$('#more_${link.MD5}').remove();"><td><span class="glyphicon glyphicon-link"></span> ${link.desc} - ${link.URL}</td></tr>									
							</c:forEach>     	    							    					
	    					</table>
	    					<div class="form-group form-inline">				    			
				    			<input type="text" id="inputLessonMoreInfo" class="form-control" 
					    			placeholder="<fmt:message key="lesson.form.moreinfo.add" bundle="${editLessonBundle}"/>">
					    		<button id="addMoreInfo" type="button" class="btn btn-default"><fmt:message key="lesson.form.moreinfo.add" bundle="${editLessonBundle}"/></button>
					    	</div>
    						<p class="help-block"><fmt:message key="lesson.form.moreinfo.tip" bundle="${editLessonBundle}"/></p>
							<select multiple="multiple" id="formMoreInfo" hidden="true" name="moreInfos">
								<c:forEach items="${moreInfoLinks}" var="link">	
									<option id="more_${link.MD5}" selected="selected">${link.URL}</option>
								</c:forEach>
							</select>
						</div>
			    	</div>
		        </div><!-- col -->
	
				<!-- Sidebar -->
	        	<div class="col-sm-5">
					<c:choose>
					<c:when test="${lesson.id > 0}">
						
					<!-- Publish panel -->
						<div class="sidebar">
							<div class="panel ${lesson.draft?'panel-warning':'panel-success'}">
								<div class="panel-heading">
									<span><fmt:message key="lesson.publish.status" bundle="${editLessonBundle}"/></span>
									<c:choose>
									 	<c:when test="${lesson.draft}">
									 		<span class="label label-warning pull-right"><fmt:message key="lesson.publish.status.draft" bundle="${editLessonBundle}"/></span>
									 	</c:when>
									 	<c:otherwise>
									 		<span class="label label-success pull-right"><fmt:message key="lesson.publish.status.published" bundle="${editLessonBundle}"/></span>							    			
									 	</c:otherwise>
									 </c:choose>
								</div>
			    				<div id="login" class="panel-body">
			    					<button class="btn btn-xs btn-primary" type="submit"><span class="glyphicon glyphicon-save"></span>
						 			 <fmt:message key="lesson.form.save" bundle="${editLessonBundle}"/></button>
									 <c:choose>
									 	<c:when test="${lesson.draft}">									 											 		
									 		<a href="${lesson.URL}">
							    				<button class="btn btn-xs btn-info" type="button">
									 			<span class="glyphicon glyphicon-sunglasses"></span>
									 			  <fmt:message key="lesson.publish.preview" bundle="${editLessonBundle}"/>
									 			 </button>
											</a>
											<a onclick="confirm('${lesson.editURL}?publishLesson=true','lesson.publish.confirm');">
							    				<button class="btn btn-success btn-xs" type="button">
									 			<span class="glyphicon glyphicon-eye-open"></span>
									 			  <fmt:message key="lesson.publish.command" bundle="${editLessonBundle}"/>
									 			 </button>
											</a>
									 	</c:when>
									 	<c:otherwise>									 		
							    			<a onclick="confirm('${lesson.editURL}?publishLesson=false','lesson.unpublish.confirm');">
							    				<button class="btn btn-warning btn-xs" type="button">											
										 		<span class="glyphicon glyphicon-eye-close"></span>
												 <fmt:message key="lesson.unpublish.command" bundle="${editLessonBundle}"/>			 	
												</button>
											</a>
									 	</c:otherwise>
									 </c:choose>										 	
	     						</div>		     					
					    	</div>					
						</div><!-- Publish Panel -->
					
						<!-- Test Panel -->
						<div class="sidebar">
							<div class="panel panel-default">
					    		<div class="panel-heading">
					    			<span><fmt:message key="lesson.test.heading" bundle="${testBundle}"/>
									 	<button title="" data-original-title="" type="button" class="btn btn-xs btn-default" data-container="body" 
											data-toggle="popover" data-placement="bottom" data-trigger="focus"
											data-content="<fmt:message key="lesson.form.test.help" bundle="${editLessonBundle}"/>">?</button>
									</span>				    			
					    			 <c:choose>
									 	<c:when test="${not empty test}">										 	
					    					<a href="${test.editURL}">
					    						<button class="btn btn-default btn-xs pull-right" type="button">
													<span class="glyphicon glyphicon-edit"></span>								
													 <fmt:message key="lesson.test.edit" bundle="${testBundle}"/>
												</button>
											</a>										 	
										</c:when>
										<c:otherwise>
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
					</c:when>
					<c:otherwise>
						<!-- Save -->
						<div class="sidebar">
							<div class="panel panel-default">								
			    				<div id="login" class="panel-body">
			    					<button class="btn btn-primary" type="submit"><span class="glyphicon glyphicon-save"></span>
						 			 <fmt:message key="lesson.form.save" bundle="${editLessonBundle}"/></button>																			 	
	     						</div>		     					
					    	</div>					
						</div><!-- Save -->
					</c:otherwise>
					</c:choose>
					<c:if test="${lesson.id>0}">
						<nav>
							<ul class="pager">
								<li><a href="${lastPage}"><span class="glyphicon glyphicon-chevron-left"></span>
								 <fmt:message key="pager.back"/></a></li>						
							</ul>
						</nav>		
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
	<script src="https://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/md5.js"></script>
		<script type="text/javascript">
		<!--	    
		var msg = {};
		msg['lesson.confirm'] = 						"<c:choose><c:when test="${lesson.id > 0}"><fmt:message key="lesson.save.confirm" bundle="${editLessonBundle}"/></c:when><c:otherwise><fmt:message key="lesson.create.confirm" bundle="${editLessonBundle}"/></c:otherwise></c:choose>";
		msg['lesson.form.media.select'] = 				"<fmt:message key='lesson.form.media.select' bundle='${editLessonBundle}'/>";
		msg['lesson.form.media.download'] =				"<fmt:message key='lesson.form.media.download' bundle='${editLessonBundle}'/>";
		msg['lesson.form.media.upload.error'] = 		"<fmt:message key='lesson.form.media.upload.error' bundle='${editLessonBundle}'/>";
		msg['lesson.form.media.upload.ignore.size'] =	"<fmt:message key='lesson.form.media.upload.ignore.size' bundle='${editLessonBundle}'/>";
		msg['lesson.form.media.upload.ignore.type'] =	"<fmt:message key='lesson.form.media.upload.ignore.type' bundle='${editLessonBundle}'/>";
		msg['lesson.form.media.upload.progress'] = 		"<fmt:message key='lesson.form.media.upload.progress' bundle='${editLessonBundle}'/>";
		msg['lesson.form.media.remove.confirm'] = 		"<fmt:message key='lesson.form.media.remove.confirm' bundle='${editLessonBundle}'/>";
		msg['lesson.publish.confirm'] = 				"<fmt:message key='lesson.publish.confirm' bundle='${editLessonBundle}'/>";
		msg['lesson.unpublish.confirm'] = 				"<fmt:message key='lesson.unpublish.confirm' bundle='${editLessonBundle}'/>";
				
		var acceptedFileTypes = [<c:forEach items="${acceptedFileTypes}" var="type" varStatus="loop">${loop.index > 0?",":""}"${type}"</c:forEach>];
		var maxFileSize = ${maxUploadFileSize};
		//-->
	</script>
	<script src="/resources/js/editLesson.js"></script>
	<script src="/resources/js/confirm.js"></script>
	
	
</body>
</html>