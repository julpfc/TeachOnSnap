<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.lesson" var="lessonBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.test" var="testBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/lesson.css"/>"/>
<title><fmt:message key="app.name"/> - ${fn:escapeXml(lesson.title)}</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">
		<div>
       		<h2 class="lesson-title">${fn:escapeXml(lesson.title)}</h2>       		 	
			<p class="lesson-meta">
				<c:if test="${(lesson.author.id == user.id && user.author)|| user.admin}">
					<span class="lesson-edit"><a href="${lesson.editURL}"><button class="btn btn-default btn-xs" type="button">
						<fmt:message key="lesson.command.edit" bundle="${lessonBundle}"/>
						 <span class="glyphicon glyphicon-edit"></span></button>
						</a>
					</span>			
				</c:if>
				<c:if test="${userLang.id != lesson.language.id}">
     				<img alt="${lesson.language.language}" src="/resources/img/ico/flag_${lesson.language.language}.jpg"/>
     			</c:if>	            			 
      			<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${lesson.date}"/>
      			 <fmt:message key="lesson.meta.author.by"/> <a href="${lesson.author.URL}">${lesson.author.fullName}</a>
   			</p>
       	</div>
		<div class="row">
			<div class="col-sm-7">
				<div>
					<c:if test="${lesson.idLessonMedia>0}">					
			     		<div class="lesson-media">		     		
				     			<c:set var="firstMedia" value="${medias[0]}"/>				     			
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
				     			</c:choose>
				     		</div>  		
					</c:if>												
					
    				<c:if test="${not empty lesson.text}"><blockquote><p>${lesson.text}</p></blockquote></c:if>	             	
	          		          	
	          		<span class="label label-default"><span class="glyphicon glyphicon-tags"></span></span>
	          		<c:forEach items="${tags}" var="tag">		
						<span class="label label-default"><a href="${tag.URL}">${tag.tag}</a></span>									
					</c:forEach>
				</div>
				<nav>
					<ul class="pager pagerBottom">
						<li><a href="${lastPage}"><span class="glyphicon glyphicon-chevron-left"></span>
						 <fmt:message key="pager.back"/></a>
						</li>						
					</ul>
				</nav>	
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">        		
        		<c:if test="${not empty test}">
					<div class="sidebar">
						<div class="panel panel-default">
			        		<div class="panel-heading"><fmt:message key="lesson.test.heading" bundle="${testBundle}"/>
			        			<a href="${test.URL}"><button class="btn btn-success btn-xs pull-right" type="button">
							 	<span class="glyphicon glyphicon-edit"></span>
							 	 <fmt:message key="lesson.test.start" bundle="${testBundle}"/></button>
						 	</a>
			        		</div>
			        		<c:if test="${not empty testRanks}">
			    			<div class="panel-body">
				    			<table class="table">
									<thead>
										<tr>
											<th>#</th>
											<th><fmt:message key="lesson.test.highscores.name" bundle="${testBundle}"/></th>
											<th><fmt:message key="lesson.test.highscores.points" bundle="${testBundle}"/></th>										
										</tr>
									</thead>
									<tbody>
					    				<c:forEach items="${testRanks}" var="testRank" varStatus="loop">
					    					<tr>
					    						<td>
					    							<c:choose>
					    								<c:when test="${testRank.user.id == user.id}">
					    									<label class="label label-info">${loop.index+1}</label>
						    							</c:when>
						    							<c:otherwise>
						    								${loop.index+1}
						    							</c:otherwise> 
					    							</c:choose>
					    						</td>
					    						<td>${testRank.user.fullName}</td>
					    						<td>${testRank.points} (${testRank.attempts})</td>
					    					</tr>
					    				</c:forEach>
			    					</tbody>
			    				</table>
			    			</div>
			    			</c:if>
			    			<c:if test="${not empty user}">
				    			<div class="panel-footer">
				    				<label class="label label-info"><fmt:message key="lesson.test.highscores.yourbest" bundle="${testBundle}"/>:</label> ${userTestRank.points} 
				    				<c:choose>
				    					<c:when test="${empty userTestRank}">
				    						<fmt:message key="lesson.test.highscores.norecords" bundle="${testBundle}"/>
				    					</c:when>
				    					<c:otherwise>
				    						(${userTestRank.attempts} <fmt:message key="lesson.test.highscores.attempts" bundle="${testBundle}"/>)
				    					</c:otherwise>
				    				
				    				</c:choose>
			    			</div>
			    			</c:if>
			    		</div>
					</div>
				</c:if>
          		<c:if test="${not empty moreInfoLinks}">
	          		<div class="sidebar">
	               		<h4><span class="glyphicon glyphicon-globe"></span> <fmt:message key="lesson.moreInfo.heading" bundle="${lessonBundle}"/></h4>
	            		<ol class="list-unstyled">
	            			<c:forEach items="${moreInfoLinks}" var="link">		
								<li><span class="glyphicon glyphicon-link"></span> <a href="${link.URL}">${link.desc}</a></li>									
							</c:forEach>              			
	            		</ol>
	          		</div>
	          	</c:if>
          		<c:if test="${not empty sourceLinks}">
	          		<div class="sidebar">
	               		<h4><span class="glyphicon glyphicon-education"></span> <fmt:message key="lesson.source.heading" bundle="${lessonBundle}"/></h4>
	            		<ol class="list-unstyled">
	            			<c:forEach items="${sourceLinks}" var="link">		
								<li><span class="glyphicon glyphicon-link"></span> <a href="${link.URL}">${link.desc}</a></li>									
							</c:forEach>              			
	            		</ol>
	          		</div>
	          	</c:if>
	          	<div class="sidebar">
               		<h4><span class="glyphicon glyphicon-pencil"></span> <fmt:message key="lesson.sameAuthor.heading" bundle="${lessonBundle}"/></h4>
            		<p><a href="${lesson.author.URL}">${lesson.author.fullName}</a></p>
          		</div>
        	</div><!-- sidebar -->
		</div><!-- /.row -->
		<c:if test="${not lesson.draft}">
			<div class="row">
				<div class="col-sm-8 col-sm-offset-2">
					<h3 class="comments-section">
						<span class="glyphicon glyphicon-comment"></span> 
						<fmt:message key="lesson.comment.heading" bundle="${lessonBundle}"/>
					</h3>
					<hr id="commentNewAnchor" />
					<div id="commentNew">			
						<div class="comment-new">
							<a data-toggle="collapse" href="#collapseNewComment" aria-expanded="${empty comments}" aria-controls="collapseNewComment">
		  					<fmt:message key="lesson.comment.new.heading" bundle="${lessonBundle}"/> <span class="caret"></span>
							</a>
						</div>
						<div class="collapse${not empty comments?'':' in'}" id="collapseNewComment">
		  					<form role="form" action="${lesson.commentURL}" method="post">
		  						<div class="form-group well">    					
						    		<p><textarea name="comment" class="form-control" rows="4" maxlength="65535"></textarea></p>				    	
		  							<p><button class="btn btn-primary form-control" type="submit"><span class="glyphicon glyphicon-send"></span>
									 <fmt:message key="lesson.comment.new.submit" bundle="${lessonBundle}"/></button></p>
		  						</div>
		  					</form>
						</div>
						<hr/>
					</div>
	 				<c:choose>
						<c:when test="${not empty comments}">
							<c:forEach items="${comments}" var="comment">		
								<article id="comment-${comment.id}" class="comment${comment.response?' col-sm-11 col-sm-offset-1 col-xs-11 col-xs-offset-1':''}">				
									<footer>
										<div class="comment-author">
										<span><img alt="avatar" src="https://www.gravatar.com/avatar/${comment.user.MD5}?s=48&d=identicon" width="48" height="48"></span>
										<cite>${comment.user.firstName}</cite> <span class="comment-author-says"><fmt:message key="lesson.comment.author.says" bundle="${lessonBundle}"/>:</span>	
										</div>
									</footer>
									<div class="comment-content">${comment.body}</div>
									<div class="comment-meta">
										<time datetime="${comment.date}">
											<c:choose>
												<c:when test="${comment.banned}">
													<span class="glyphicon glyphicon-ban-circle"></span> <fmt:message key="lesson.comment.banned" bundle="${lessonBundle}"/> | 
												</c:when>
												<c:otherwise>
													<c:if test="${comment.edited}"><span class="glyphicon glyphicon-edit"></span> <fmt:message key="lesson.comment.edited" bundle="${lessonBundle}"/> | </c:if>
												</c:otherwise>
											</c:choose>
										<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${comment.date}"/></time>
	
										<c:set var="replyText"><fmt:message key="lesson.comment.reply" bundle="${lessonBundle}"/>${comment.id>0?" <span class=\\'comment-cancel\\'> (":""}<fmt:message key="lesson.comment.cancel" bundle="${lessonBundle}"/>${comment.id>0?' )</span>':''}</c:set>
										| <a onclick="return moveCommentForm('${comment.id}','${replyText}');"><fmt:message key="lesson.comment.reply" bundle="${lessonBundle}"/></a>
		 
										<c:if test="${user.id == comment.user.id}">
											<c:set var="editText"><fmt:message key="lesson.comment.edit" bundle="${lessonBundle}"/>${comment.id>0?" <span class=\\'comment-cancel\\'> (":""}<fmt:message key="lesson.comment.cancel" bundle="${lessonBundle}"/>${comment.id>0?' )</span>':''}</c:set>
											| <a onclick="return moveCommentForm('${comment.id}','${editText}','true');"><fmt:message key="lesson.comment.edit" bundle="${lessonBundle}"/></a>
										</c:if>	
										<c:if test="${user.admin}">
											<c:set var="blockReasonText"><fmt:message key="lesson.comment.block.reason" bundle="${lessonBundle}"/>${user.admin?" <span class=\\'comment-cancel\\'> (":""}<fmt:message key="lesson.comment.cancel" bundle="${lessonBundle}"/>${user.admin?' )</span>':''}</c:set>
											<c:set var="blockText"><fmt:message key="lesson.comment.block" bundle="${lessonBundle}"/></c:set>
											<c:choose>
												<c:when test="${comment.banned}">
													| <a href="${lesson.commentURL}?idComment=${comment.id}&banComment=false"><fmt:message key="lesson.comment.unblock" bundle="${lessonBundle}"/></a> 
												</c:when>
												<c:otherwise>
												 	| <a onclick="return moveCommentForm('${comment.id}','${blockReasonText}','false','${blockText}');"><fmt:message key="lesson.comment.block" bundle="${lessonBundle}"/></a>
													
												</c:otherwise>
											</c:choose>							
										</c:if>
									</div><!-- .comment-meta .commentmetadata -->	 							
								</article>												
							</c:forEach>
							<nav>
								<ul class="pager">
									<c:if test="${not empty prevPage}">								
										<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-left"></span>
										 <fmt:message key="pager.previous"/></a></li>
										 <li><a href="${lesson.URL}"><span class="glyphicon glyphicon-home"></span>
										 <fmt:message key="lesson.comment.pager" bundle="${lessonBundle}"/></a></li>
									</c:if>								
									<c:if test="${not empty nextPage}">									
										<li><a href="${nextPage}"><span class="glyphicon glyphicon-chevron-right"></span>
										  <fmt:message key="pager.next"/></a></li>
									</c:if>
								</ul>
							</nav>              			
	            		</c:when>
						<c:otherwise> 
							<cite><fmt:message key="lesson.comment.nocomments" bundle="${lessonBundle}"/></cite>
					    </c:otherwise>
	        		</c:choose>							
				</div>				
			</div><!-- /.row -->
		</c:if>      	
	</div><!-- /.container -->
	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
	<script src="/resources/js/lesson.js"></script>
</body>
</html>