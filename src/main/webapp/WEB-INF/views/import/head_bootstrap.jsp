<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Common page header: portion of code to be included into page's <head> --%>
	<!-- Common head -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<!-- To ensure proper rendering and touch zooming -->
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<!-- Disabled zooming
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> -->
	<!-- Bootstrap: Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
	<!-- Bootstrap: Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css"/>	
	<!-- App's common CSS --> 	
  	<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>"/>
 	<!-- Favicons -->
 	<link rel="apple-touch-icon" sizes="57x57" href="${host}/resources/favicon/apple-touch-icon-57x57.png">
	<link rel="apple-touch-icon" sizes="60x60" href="${host}/resources/favicon/apple-touch-icon-60x60.png">
	<link rel="apple-touch-icon" sizes="72x72" href="${host}/resources/favicon/apple-touch-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="76x76" href="${host}/resources/favicon/apple-touch-icon-76x76.png">
	<link rel="apple-touch-icon" sizes="114x114" href="${host}/resources/favicon/apple-touch-icon-114x114.png">
	<link rel="apple-touch-icon" sizes="120x120" href="${host}/resources/favicon/apple-touch-icon-120x120.png">
	<link rel="apple-touch-icon" sizes="144x144" href="${host}/resources/favicon/apple-touch-icon-144x144.png">
	<link rel="apple-touch-icon" sizes="152x152" href="${host}/resources/favicon/apple-touch-icon-152x152.png">
	<link rel="apple-touch-icon" sizes="180x180" href="${host}/resources/favicon/apple-touch-icon-180x180.png">
	<link rel="icon" type="image/png" href="${host}/resources/favicon/favicon-32x32.png" sizes="32x32">
	<link rel="icon" type="image/png" href="${host}/resources/favicon/favicon-194x194.png" sizes="194x194">
	<link rel="icon" type="image/png" href="${host}/resources/favicon/android-chrome-192x192.png" sizes="192x192">
	<link rel="icon" type="image/png" href="${host}/resources/favicon/favicon-96x96.png" sizes="96x96">
	<link rel="icon" type="image/png" href="${host}/resources/favicon/favicon-16x16.png" sizes="16x16">
	<link rel="manifest" href="${host}/resources/favicon/manifest.json">
	<link rel="mask-icon" href="${host}/resources/favicon/safari-pinned-tab.svg" color="#6f5499">
	<link rel="shortcut icon" href="${host}/resources/favicon/favicon.ico">
	<meta name="msapplication-TileColor" content="#6f5499">
	<meta name="msapplication-TileImage" content="${pageContext.request.contextPath}/resources/favicon/mstile-144x144.png">
	<meta name="msapplication-config" content="${pageContext.request.contextPath}/resources/favicon/browserconfig.xml">
	<meta name="theme-color" content="#6f5499">
	<!-- /common head -->	 