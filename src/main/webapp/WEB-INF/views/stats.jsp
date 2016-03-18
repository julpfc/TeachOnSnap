<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.stats" var="statsBundle"/>
<fmt:setBundle basename="i18n.views.test" var="testBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Author stats --%>
<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/stats.css"/>"/>
	<title>
		<fmt:message key="app.name"/> - 
		<c:choose>
			<c:when test="${not empty lesson}">
				<fmt:message key="stats.lesson.${statsType}.heading" bundle="${statsBundle}"/>
			</c:when>
			<c:otherwise>
				<fmt:message key="stats.author.${statsType}.heading" bundle="${statsBundle}"/>
			</c:otherwise>
		</c:choose>
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>		
	<div class="content container-fluid">		
		<div class="row">
			<div class="panel panel-default">
				<!-- heading: links to more stats -->
     			<div class="panel-heading violetButton">	
     				&nbsp;
     				<c:set var="statsNextType" value="${statsType eq 'year'?'month':'year'}"/>     			
	     			<c:if test="${not empty lesson}">
	     				<div class="pull-right">
	     				<a href="${host}/stats/${empty statsAdmin?'':'admin/'}${empty profile?'':'author/'}lesson/${statsNextType}/${lesson.id}">
	     					<button class="btn btn-primary btn-xs " type="button">											
								<span class="glyphicon glyphicon-dashboard"></span>	<fmt:message key="stats.show.${statsNextType}" bundle="${statsBundle}"/>			 	
							</button>
						</a> 
						</div>
						<c:if test="${not empty test}">
							<div class="pull-left">
							<a href="${host}/stats/${empty statsAdmin?'':'admin/'}${empty profile?'':'author/'}lesson/test/${test.id}">
								<button class="btn btn-primary btn-xs" type="button">											
									<span class="glyphicon glyphicon-dashboard"></span>
									<fmt:message key="lesson.test.stats.show" bundle="${testBundle}"/>			 	
								</button>
							</a>
							</div>
						</c:if>
					</c:if>	     			
					<c:if test="${empty lesson && not empty profile}">
	     				<a href="${host}/stats/${empty statsAdmin?'':'admin/'}author/${statsNextType}/${profile.id}">
	     					<button class="btn btn-primary btn-xs pull-right" type="button">											
								<span class="glyphicon glyphicon-dashboard"></span>	<fmt:message key="stats.show.${statsNextType}" bundle="${statsBundle}"/>
							</button>
						</a>
					</c:if>
				</div>
				<div class="graph panel-body">
					<c:if test="${not empty statsExtra}">
						<!-- Bar chart -->
						<div class="alert col-sm-10 col-sm-offset-1">
							<label for="myBarChart"><fmt:message key="stats.most.viewed.lesson.from" bundle="${statsBundle}"/> <span class="label label-info">${fn:escapeXml(profile.fullName)}</span> <fmt:message key="stats.in.last.${statsType}" bundle="${statsBundle}"/></label>
							<p class="help-block">							
								<span class="glyphicon glyphicon-signal"></span> <span class="glyphicon glyphicon-search"></span> <fmt:message key="stats.lesson.bar.tip" bundle="${statsBundle}"/>
			    			</p>	
							<canvas id="myBarChart"></canvas>					
							<p class="help-block">
								<a href="?export=1">
									<button type="button" class="btn btn-success btn-xs pull-right"><span class="glyphicon glyphicon-save-file"></span> <fmt:message key="stats.export.csv" bundle="${statsBundle}"/></button>
								</a>
								 <fmt:message key="stats.export.csv.tip" bundle="${statsBundle}"/>
		    				</p>					
						</div>						
					</c:if>
					<div class="alert col-sm-10 col-sm-offset-1">
						<!-- Line chart -->
						<c:if test="${empty lesson && not empty profile}">
							<label for="myLineChart"><fmt:message key="stats.lesson.views.from" bundle="${statsBundle}"/> <span class="label label-info">${fn:escapeXml(profile.fullName)}</span> <fmt:message key="stats.in.last.${statsType}" bundle="${statsBundle}"/></label>
						</c:if>
						<c:if test="${not empty lesson}">
							<label for="myLineChart"><fmt:message key="stats.lesson.views.to" bundle="${statsBundle}"/> <span class="label label-info">${fn:escapeXml(lesson.title)}</span> <fmt:message key="stats.in.last.${statsType}" bundle="${statsBundle}"/></label>
						</c:if>
						<canvas id="myLineChart"></canvas>						
						<p class="help-block">
							<fmt:message key="stats.export.csv.tip" bundle="${statsBundle}"/>
							<a href="?export=0">
								<button type="button" class="btn btn-success btn-xs pull-right"><span class="glyphicon glyphicon-save-file"></span> <fmt:message key="stats.export.csv" bundle="${statsBundle}"/></button>
							</a>
		    			</p>					
					</div>
					<c:if test="${not empty statsExtra2}">
						<div class="alert col-sm-10 col-sm-offset-1">	
							<!-- Pie chart -->					
							<label for="myPieChart"><fmt:message key="stats.lesson.views.media.dist" bundle="${statsBundle}"/> <span class="label label-info">${fn:escapeXml(profile.fullName)}</span> <fmt:message key="stats.in.last.${statsType}" bundle="${statsBundle}"/></label>
							<canvas id="myPieChart"></canvas>						
							<p class="help-block">
								<fmt:message key="stats.export.csv.tip" bundle="${statsBundle}"/>
								<a href="?export=2">
									<button type="button" class="btn btn-success btn-xs pull-right"><span class="glyphicon glyphicon-save-file"></span> <fmt:message key="stats.export.csv" bundle="${statsBundle}"/></button>
								</a>
		    				</p>					
						</div>
					</c:if>
				</div>
				<c:if test="${not empty backPage}">
					<div class="panel-footer">
						<nav>
							<ul class="pager">
								<li><a href="${backPage}"><span class="glyphicon glyphicon-chevron-left"></span>
						 		<fmt:message key="pager.back"/></a></li>						 						
							</ul>
						</nav>	
	     			</div>
				</c:if>
   			</div>
		</div><!-- /.row -->		
	</div><!-- /.content -->
	<c:import url="./import/footer.jsp"/>
	<!-- Javascript -->
	<c:import url="./import/js_bootstrap.jsp"/>
	<script type="text/javascript">
		<!-- 
		var data = {
		    labels: [<c:forEach var="stat" items="${stats}" varStatus="loop">"${stat.key}"${loop.last?'':','}</c:forEach>],
		    datasets: [
		        {
		            label: "Visitas",		           
		            fillColor: "rgba(86,62,124,0.2)",
		            strokeColor: "rgba(86,62,124,1)",
		            pointColor: "rgba(86,62,124,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [<c:forEach var="stat" items="${stats}" varStatus="loop">${stat.value}${loop.last?'':','}</c:forEach>]
		        }
		    ]
		};
		<c:if test="${not empty statsExtra}">
			var dataBar = {
			    labels: [<c:forEach var="stat" items="${statsExtra}" varStatus="loop">"${fn:substring(fn:escapeXml(stat.key),0,50)}${fn:length(fn:escapeXml(stat.key)) > 50?'...':''}"${loop.last?'':','}</c:forEach>],
			    datasets: [
			        {
			            label: "Lecciones más vistas",		           
			            fillColor: "rgba(86,62,124,0.5)",
			            strokeColor: "rgba(86,62,124,0.8)",
			            HighlightFill: "rgba(86,62,124,0.75)",
			            HighlightStroke: "rgba(86,62,124,1)",			            
			            data: [<c:forEach var="stat" items="${statsExtra}" varStatus="loop">${stat.value}${loop.last?'':','}</c:forEach>],
			            pointColor: "rgba(86,62,124,1)"
			        }
			    ]
			};			
		
			var statsAdmin = "${statsAdmin}";
			var statsType = "${statsType}";
			
			var barIDs = {};
			<c:forEach var="stat" items="${statsExtra}" varStatus="loop">barIDs['${fn:substring(fn:escapeXml(stat.key),0,50)}${fn:length(fn:escapeXml(stat.key)) > 50?'...':''}']=${stat.id};</c:forEach>
			
			var dataPie = [
				<c:forEach var="stat" items="${statsExtra2}" varStatus="loop">{value: ${stat.value},color:"rgba(86,62,124,${(10-loop.index*2)/10})",highlight: "rgba(86,62,124,${(9-loop.index*2)/10})",label:"${fn:substring(fn:escapeXml(stat.key),0,50)}${fn:length(fn:escapeXml(stat.key)) > 50?'...':''}"}${loop.last?'':','}</c:forEach>
			];
		</c:if>
		var appHost = "${host}";
		//-->
	</script>	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js"></script>
	<script src="${host}/resources/js/ext/Chart.HorizontalBar.js"></script>
 	<script src="${host}/resources/js/stats.js"></script>
 	<!-- /Javascript -->
</body>
</html>