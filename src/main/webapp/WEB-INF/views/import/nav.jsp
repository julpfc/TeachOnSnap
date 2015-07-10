<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>
<nav class="navbar ${not empty user?'navbar-inverse':'navbar-default'}" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1">
        <span class="sr-only"><fmt:message key="nav.toggle.menu"/></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/">
      	<span><img src="/resources/favicon/apple-touch-icon-precomposed.png" width="22" height="22"/></span>
      	TeachOnSnap</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="navbar-collapse-1">
      <ul class="nav navbar-nav navbar-left">
      	<c:if test="${empty user}">
        	<li><a href="#"><fmt:message key="nav.menu.register"/> <span class="glyphicon glyphicon-pencil"></span></a></li>
        </c:if>
        <li class="dropdown">
        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="nav.menu.explore"/> <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
            	<li><a href="/last/"><fmt:message key="nav.menu.explore.lastVideos"/></a></li>                
                <li class="divider"></li>
                <li><a href="#"><fmt:message key="nav.menu.explore.about"/></a></li>                
			</ul>
        </li>
        <li><a href="/lesson/new/"><fmt:message key="nav.menu.upload"/> <span class="glyphicon glyphicon-cloud-upload"></span></a></li>        
      </ul>
      <ul class="nav navbar-nav navbar-right">      	
        <c:choose>
			<c:when test="${empty user}">
				<li class="dropdown">
		        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
		        		<img alt="${userLang.language}" src="/resources/img/ico/flag_${userLang.language}.jpg"/> <span class="caret"></span>
		       		</a>
		            <ul class="dropdown-menu" role="menu">            	
		            	<li><a href="?changeLang=en"><img alt="EN" src="/resources/img/ico/flag_en.jpg"/> English EN</a></li>
		            	<li><a href="?changeLang=es"><img alt="ES" src="/resources/img/ico/flag_es.jpg"/> Español ES</a></li>
					</ul>
		        </li>
				<li><a href="#" data-toggle="modal" data-target="#login"><fmt:message key="nav.menu.login"/> <span class="glyphicon glyphicon-log-in"></span></a></li>			        
        	</c:when> 
        	<c:otherwise>
        		<c:if test="${user.admin}">
	        	 	<li class="dropdown">
			        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="nav.menu.admin"/> <span class="caret"></span></a>
			            <ul class="dropdown-menu" role="menu">
			            	<li><a href="#"><fmt:message key="nav.menu.admin.users"/></a></li>			            	                
						</ul>
			        </li>
			   	</c:if>
		       	<li class="dropdown">
		        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.fullName} <span class="caret"></span></a>
		            <ul class="dropdown-menu" role="menu">
		            	<c:if test="${user.author}">
		            		<li><a href="${user.URL}"><fmt:message key="nav.menu.user.lessons"/></a></li>
		            		<li class="divider"></li>
		            	</c:if>
		            	<li><a href="/preferences/"><fmt:message key="nav.menu.user.preferences"/></a></li>                
					</ul>
		        </li>
        		<li><a href="/login/?logout=1"><fmt:message key="nav.menu.logout"/> <span class="glyphicon glyphicon-log-out"></span></a></li>
        	</c:otherwise>
        </c:choose>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<c:if test="${not empty pageStack}">
	<nav class="navbar subnav navbar-default" role="navigation">
	  <div class="container-fluid col-sm-8 col-sm-offset-2">
	      <ul class="nav subnav navbar-nav">
	      	<c:forEach items="${pageStack}" var="page" varStatus="loop">
	      		<c:choose>
	      			<c:when test="${loop.last}">
	      				<li><a><fmt:message key="${page.name}"/></a></li>
	      			</c:when>
	      			<c:otherwise>
	      				<li><a href="${page.link}"><fmt:message key="${page.name}"/></a></li>
	      			</c:otherwise>
	      		</c:choose>	        	
	        </c:forEach>	  		       
	      </ul>
	   </div>
	</nav>
</c:if>
<c:if test="${not empty errorMessageKey}">
	<c:import url="./import/alert.jsp"/>
	
</c:if>
<c:import url="./import/login.jsp"/>