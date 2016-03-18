<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Application menu: portion of code to be included into a page --%>
	<!-- Menu -->
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
	      		<a class="navbar-brand" href="${host}/">
	      			<span><img src="${host}/resources/favicon/apple-touch-icon-precomposed.png" width="22" height="22"/></span>
	      			<fmt:message key="app.name"/>
	      		</a>
	    	</div>
	
	    	<!-- Collect the nav links, forms, and other content for toggling -->
	    	<div class="collapse navbar-collapse" id="navbar-collapse-1">	    
				<!-- Left menu -->
				<ul class="nav navbar-nav navbar-left">
					<c:if test="${empty user}">
						<!-- Not logged in -->
			 			<li><a href="#" data-toggle="modal" data-target="#register"><fmt:message key="nav.menu.register"/> <span class="glyphicon glyphicon-pencil"></span></a></li>
			 		</c:if>
			 		<li class="dropdown">
			 			<!-- Explore menu -->
			 			<a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="nav.menu.explore"/> <span class="caret"></span></a>
			     		<ul class="dropdown-menu" role="menu">
			     			<li><a href="${host}/last/"><fmt:message key="nav.menu.explore.lastVideos"/></a></li>                
			         		<li class="divider"></li>
			         		<li><a href="#" data-toggle="modal" data-target="#about"><fmt:message key="nav.menu.explore.about"/></a></li>                
						</ul>
			    	</li>
			    	<c:if test="${user.author}">
			    		<!-- Upload is only for authors -->
			 			<li><a href="${host}/lesson/new/"><fmt:message key="nav.menu.upload"/> <span class="glyphicon glyphicon-cloud-upload"></span></a></li>
			 		</c:if>        
				</ul><!-- /Left menu -->
				<!-- Right menu -->				
				<ul class="nav navbar-nav navbar-right">      	
		        	<c:choose>
						<c:when test="${empty user}">
		        			<!-- Not logged in -->
							<li class="dropdown">
				        		<a href="#" class="dropdown-toggle" data-toggle="dropdown">
				        			<img alt="${userLang.language}" src="${host}/resources/img/ico/flag_${userLang.language}.jpg"/> <span class="caret"></span>
				       			</a>
				            	<ul class="dropdown-menu" role="menu">
					            	<c:forEach items="${languages}" var="lang" varStatus="loop">
					            		<fmt:setLocale value="${lang.language}"/>
					            		<fmt:setBundle basename="i18n.views.common"/>
					            		<li><a href="?changeLang=${lang.language}"><img alt="${lang.language}" src="${host}/resources/img/ico/flag_${lang.language}.jpg"/> <fmt:message key="nav.menu.lang"/></a></li>		            	
					            	</c:forEach>
				            		<fmt:setLocale value="${userLang.language}"/>
				            		<fmt:setBundle basename="i18n.views.common"/>
								</ul>
				        	</li>
							<li><a href="#" data-toggle="modal" data-target="#login"><fmt:message key="nav.menu.login"/> <span class="glyphicon glyphicon-log-in"></span></a></li>			        
		        		</c:when> 
		        		<c:otherwise>
		        			<!-- Logged in -->
		        			<c:if test="${user.admin}">
		        				<!-- Admin menu -->
			        	 		<li class="dropdown">
						        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="nav.menu.admin"/> <span class="caret"></span></a>
						            <ul class="dropdown-menu" role="menu">
						            	<li><a href="${host}/admin/users/"><fmt:message key="nav.menu.admin.users"/></a></li>			            	                
						            	<li><a href="${host}/admin/groups/"><fmt:message key="nav.menu.admin.groups"/></a></li>
						            	<li class="divider"></li>
						            	<li><a href="${host}/admin/broadcast/"><fmt:message key="nav.menu.admin.broadcast"/></a></li>
						            	<li class="divider"></li>
						            	<li><a href="${host}/admin/stats/month/"><fmt:message key="nav.menu.admin.stats"/></a></li>
									</ul>
						        </li>
					   		</c:if>
					       	<li class="dropdown">
					        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.fullName} <span class="caret"></span></a>
					            <ul class="dropdown-menu" role="menu">
					            	<c:if test="${user.author}">
					            		<!-- Author menu -->
					            		<li><a href="${user.URL}"><fmt:message key="nav.menu.user.lessons"/></a></li>
					            		<li><a href="${user.draftsURL}"><fmt:message key="nav.menu.user.lessons.draft"/></a></li>
					            		<li><a href="${host}/stats/author/month/${user.id}"><fmt:message key="nav.menu.user.stats"/></a></li>
					            		<li class="divider"></li>
					            	</c:if>
					            	<c:if test="${not empty user.authorFollowed || not empty user.lessonFollowed}">
					            		<!-- Followings menu -->		            		
					            		<li><a href="${host}/follow/"><fmt:message key="nav.menu.user.follow"/></a></li>
					            		<li class="divider"></li>
					            	</c:if>
					            	<li><a href="${host}/profile/"><fmt:message key="nav.menu.user.profile"/></a></li>                
								</ul>
					        </li>
					        <li class="dropdown">
					        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					        		<img alt="${userLang.language}" src="${host}/resources/img/ico/flag_${userLang.language}.jpg"/> <span class="caret"></span>
					       		</a>		           
								<ul class="dropdown-menu" role="menu">
					            	<c:forEach items="${languages}" var="lang" varStatus="loop">
					            		<fmt:setLocale value="${lang.language}"/>    
					            		<fmt:setBundle basename="i18n.views.common"/>        	
					            		<li><a href="?changeLang=${lang.language}"><img alt="${lang.language}" src="${host}/resources/img/ico/flag_${lang.language}.jpg"/> <fmt:message key="nav.menu.lang"/></a></li>		            	
					            	</c:forEach>
					            	<fmt:setLocale value="${userLang.language}"/>
					            	<fmt:setBundle basename="i18n.views.common"/>
								</ul>
					        </li>
		        			<li><a href="${host}/login/?logout=1"><fmt:message key="nav.menu.logout"/> <span class="glyphicon glyphicon-log-out"></span></a></li>
		        		</c:otherwise>
		        	</c:choose>
				</ul><!-- /right menu -->
			</div>
		</div>
	</nav><!-- /menu -->

<c:if test="${not empty pageStack}">
	<!-- Page stack -->
	<nav class="navbar subnav navbar-default" role="navigation">
	  <div class="container-fluid col-sm-8 col-sm-offset-2">
	      <ul class="nav subnav navbar-nav">
	      	<c:forEach items="${pageStack}" var="page" varStatus="loop">
	      		<c:choose>
	      			<c:when test="${loop.last}">
	      				<li><a><fmt:message key="${page.name}"/></a></li>	      				
	      			</c:when>
	      			<c:otherwise>
	      				<li><a href="${page.link}"><fmt:message key="${page.name}"/><c:if test="${not empty page.extraName}">: <span class="label label-info navlabel">${fn:escapeXml(page.extraName)}</span></c:if></a></li>
	      			</c:otherwise>
	      		</c:choose>	        	
	        </c:forEach>	  		       
	      </ul>
	   </div>
	</nav><!-- /page stack -->
</c:if>
<c:if test="${not empty errorMessageKey}">
	<c:import url="./import/alert.jsp"/>	
</c:if>
<c:import url="./import/login.jsp"/>