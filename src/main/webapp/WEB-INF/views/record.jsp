<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>
<!DOCTYPE html>
<html>
<head>	
<c:import url="./import/head_bootstrap.jsp"/>
<link rel="stylesheet" href="<c:url value="/resources/css/record.css"/>"/>
<title><fmt:message key="app.name"/> - Video RecordRTC</title>
</head>
<body>
<c:import url="./import/nav.jsp"/>
	<div class="content container-fluid">
		<div>
       		<h2 class="lesson-title">Grabar video+audio en webM</h2>       		 	
			<p class="lesson-meta">
      			 Sólo en Firefox. En Chrome sólo imagen
   			</p>
       	</div>
		<div class="row">
			<div class="col-sm-7">
				<div>										
		     		<div class="lesson-video">		     		
		     			<h2 id="download-url"></h2>
		     			<h4 id="error-messages"></h4>
	                    <video id="video"></video><hr />
	                    <button class="btn btn-default" type="button" id="start-recording">Record Audio+Video</button>
	                    <button class="btn btn-default" type="button" id="stop-recording" disabled>Stop</button>
		     		</div>
		     		<div class="lesson-audio">
                    	<audio id="audio" controls="controls"></audio>
                    	<button class="btn btn-default" type="button" id="start-recording-audio">Record</button>
                    	<button class="btn btn-default" type="button" id="stop-recording-audio" disabled>Stop</button>
                    	<h2 id="audio-url-preview"></h2>
                    	<h4 id="error-messages-audio"></h4>
                    </div>
                </div>
				<nav>
					<ul class="pager">
						<li><a href="${host}/"><span class="glyphicon glyphicon-home"></span> Home</a></li>						
					</ul>
				</nav>	
	        </div><!-- col -->

        	<div class="col-sm-4 col-sm-offset-1">
        		
        	</div><!-- sidebar -->
		</div><!-- /.row -->
    </div><!-- /.container -->
	
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>
	<!-- script used for audio/video/gif recording -->
    <script src="http://cdn.webrtc-experiment.com/RecordRTC.js"> </script>
	<script src="${host}/resources/js/record.js"></script>
	
</body>
</html>