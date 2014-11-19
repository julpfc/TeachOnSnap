<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<title>TeachOnSnap - Búsqueda por ${searchType}: ${searchKeyword}</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>	
	
	<div class="content container-fluid">
		<div class="row">					
			<div class="col-sm-7">
				<c:forEach items="${lessons}" var="lesson">					
					<div>
	            		<h2 class="lesson-title"><a href="${lesson.URL}">${lesson.title}</a></h2>
	            		<p class="lesson-meta">
	            			<c:if test="${userLang.id != lesson.language.id}">
	            				<img alt="${lesson.language.language}" src="/resources/img/ico/flag_${lesson.language.language}.jpg"/>
	            			</c:if>	            			 
	            			<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${lesson.date}"/>
	            			 by <a href="${lesson.author.URL}">${lesson.author.fullName}</a>
            			</p>
	            		<p class="lesson-addons">
	            			<c:if test="${lesson.idLessonVideo>0}">
								<span class="glyphicon glyphicon-facetime-video"></span> video
							</c:if>
							<c:if test="${lesson.idLessonTest>0}">
								<span class="glyphicon glyphicon-edit"></span> test
							</c:if>
						</p>                    		
						<c:if test="${not empty lesson.text}"><blockquote><p>${lesson.text}</p></blockquote></c:if>
			            <nav class="col-sm-offset-8"><ul class="pager"><li>
							<a href="${lesson.URL}"><span class="glyphicon glyphicon-book"></span> Learn more</a></li></ul>
						</nav>
		          	</div>
		          	<hr/>	          	
				</c:forEach>
				<c:if test="${empty lessons}">
					<h2>No existen resultados.</h2>
				</c:if>			
	   
				<nav>
					<ul class="pager">
						<c:if test="${not empty nextPage}">
							<li><a href="${nextPage}"><span class="glyphicon glyphicon-chevron-left"></span> Older</a></li>
						</c:if>
						<li><a href="/"><span class="glyphicon glyphicon-home"></span> Home</a></li>
						<c:if test="${not empty prevPage && not empty lessons}">
							<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-right"></span> Newer</a></li>
						</c:if>
					</ul>
				</nav>
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
          		<c:if test="${not empty searchType}">
	          		<div class="sidebar">
	            		<h4>Búsqueda por ${searchType}:</h4>
	            		<span class="label label-info">${searchKeyword}</span>	            		
	          		</div>        
          		</c:if> 
          		<div class="sidebar tags">
            		<h4><span class="glyphicon glyphicon-tags"></span> Tag cloud</h4>
            		<ul class="tags">
            		<c:forEach items="${cloudTags}" var="cloudTag">
            			<li class="tags tag${cloudTag.weight}"><a href="${cloudTag.URL}">${cloudTag.tag}</a></li>
		            </c:forEach>
		            </ul>
          		</div>  
          		<div class="sidebar tags">
            		<h4><span class="glyphicon glyphicon-tags"></span> Author cloud</h4>
            		<ul class="tags">
            		<c:forEach items="${authorCloudTags}" var="cloudTag">
            			<li class="tags tag${cloudTag.weight}"><a href="${cloudTag.URL}">${cloudTag.tag}</a></li>
		            </c:forEach>
		            </ul>
          		</div>         	 		          		
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
    <c:import url="./import/footer.jsp"/>

	<c:import url="./import/js_bootstrap.jsp"/>

</body>
</html>