<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.stats" var="statsBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/stats.css"/>"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="admin.stats.${statsType}.heading" bundle="${statsBundle}"/>
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>		
	<div class="content container-fluid">		
		<div class="row">
			<div class="panel panel-default">
     			<div class="panel-heading violetButton">	
     				&nbsp;
     				<c:set var="statsNextType" value="${statsType eq 'year'?'month':'year'}"/>     			
	     			<div class="pull-right">
	     				<a href="/admin/stats/${statsNextType}/">
	     					<button class="btn btn-primary btn-xs " type="button">											
								<span class="glyphicon glyphicon-dashboard"></span>	<fmt:message key="stats.show.${statsNextType}" bundle="${statsBundle}"/>			 	
							</button>
						</a> 
					</div>
				</div>
				<div class="graph panel-body">
					<div class="alert col-sm-10 col-sm-offset-1">
						<label for="myLineChart"><fmt:message key="admin.stats.views" bundle="${statsBundle}"/> <fmt:message key="stats.in.last.${statsType}" bundle="${statsBundle}"/></label>
						<canvas id="myLineChart"></canvas>						
						<p class="help-block">
							<fmt:message key="stats.export.csv.tip" bundle="${statsBundle}"/>
							<a href="?export=0">
								<button type="button" class="btn btn-success btn-xs pull-right"><span class="glyphicon glyphicon-save-file"></span> <fmt:message key="stats.export.csv" bundle="${statsBundle}"/></button>
							</a>
		    			</p>					
					</div>
					<c:if test="${not empty statsExtra}">
						<div class="alert col-sm-10 col-sm-offset-1">
							<label for="myBarChart"><fmt:message key="admin.stats.most.viewed.lesson" bundle="${statsBundle}"/> <fmt:message key="stats.in.last.${statsType}" bundle="${statsBundle}"/></label>
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
					<c:if test="${not empty statsExtra2}">
						<div class="alert col-sm-10 col-sm-offset-1">
							<label for="myBarChart2"><fmt:message key="admin.stats.most.viewed.author" bundle="${statsBundle}"/> <fmt:message key="stats.in.last.${statsType}" bundle="${statsBundle}"/></label>
							<p class="help-block">							
								<span class="glyphicon glyphicon-signal"></span> <span class="glyphicon glyphicon-search"></span> <fmt:message key="stats.author.bar.tip" bundle="${statsBundle}"/>
			    			</p>	
							<canvas id="myBarChart2"></canvas>					
							<p class="help-block">
								<a href="?export=2">
									<button type="button" class="btn btn-success btn-xs pull-right"><span class="glyphicon glyphicon-save-file"></span> <fmt:message key="stats.export.csv" bundle="${statsBundle}"/></button>
								</a>
								 <fmt:message key="stats.export.csv.tip" bundle="${statsBundle}"/>
		    				</p>					
						</div>						
					</c:if>
				</div>				
   			</div>
		</div><!-- /.row -->		
	 </div><!-- /.container -->		
	<c:import url="./import/footer.jsp"/>
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
	
		var dataBar2 = {
			    labels: [<c:forEach var="stat" items="${statsExtra2}" varStatus="loop">"${fn:substring(fn:escapeXml(stat.key),0,50)}${fn:length(fn:escapeXml(stat.key)) > 50?'...':''}"${loop.last?'':','}</c:forEach>],
			    datasets: [
			        {
			            label: "Autores más vistos",		           
			            fillColor: "rgba(86,62,124,0.5)",
			            strokeColor: "rgba(86,62,124,0.8)",
			            HighlightFill: "rgba(86,62,124,0.75)",
			            HighlightStroke: "rgba(86,62,124,1)",			            
			            data: [<c:forEach var="stat" items="${statsExtra2}" varStatus="loop">${stat.value}${loop.last?'':','}</c:forEach>],
			            pointColor: "rgba(86,62,124,1)"
			        }
			    ]
			};			
		
		var statsType = "${statsType}";
		var barIDs = {};
		<c:forEach var="stat" items="${statsExtra}" varStatus="loop">barIDs['${fn:substring(fn:escapeXml(stat.key),0,50)}${fn:length(fn:escapeXml(stat.key)) > 50?'...':''}']=${stat.id};</c:forEach>
		
		var bar2IDs = {};
		<c:forEach var="stat" items="${statsExtra2}" varStatus="loop">bar2IDs['${fn:substring(fn:escapeXml(stat.key),0,50)}${fn:length(fn:escapeXml(stat.key)) > 50?'...':''}']=${stat.id};</c:forEach>
		
		//-->
	</script>	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js"></script>
	<script src="/resources/js/ext/Chart.HorizontalBar.js"></script>
 	<script src="/resources/js/adminStats.js"></script>
</body>
</html>