<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.preferences" var="prefBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.login" var="loginBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="admin.users.heading" bundle="${adminBundle}"/>			
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<div class="content container-fluid">		
		<div class="row">
			<div class="panel panel-default">
     			<div class="panel-heading">
     				<c:choose>
     					<c:when test="${not empty param['searchQuery']}">
     						<fmt:message key="admin.users.search.result.heading" bundle="${adminBundle}"/>: <span class="label label-info">${param['searchQuery']}</span>     						    
     					</c:when>
     					<c:otherwise>
		     				<form action="" method="post" role="form">
				    			<div class="col-sm-4 col-sm-offset-8 input-group">
			      					<input type="text" class="form-control" name="searchQuery" placeholder="<fmt:message key="admin.users.search.placeholder" bundle="${adminBundle}"/>"/>
			      					<span class="input-group-addon">      
			         					<input id="emailRadio" type="radio" name="searchType" value="email" required checked="checked" />
			        					<label for="emailRadio"><span data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="admin.users.search.tooltip.mail" bundle="${adminBundle}"/>" class="glyphicon glyphicon-envelope"></span></label>
			       						<input id="fullNameRadio" type="radio" name="searchType" value="name" required />
			        					<label for="fullNameRadio"><span data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="admin.users.search.tooltip.name" bundle="${adminBundle}"/>" class="glyphicon glyphicon-user"></span></label>
			      					</span>
			      					<div class="input-group-btn">
			        					<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>        
			      					</div><!-- /btn-group -->
			    				</div><!-- /input-group -->
							</form>     				
     					</c:otherwise>
     				</c:choose>
				</div>
				<div class="panel-body">
					<c:if test="${empty users}">
						<h2><fmt:message key="admin.users.empty" bundle="${adminBundle}"/></h2>
					</c:if>
					<c:if test="${not empty users}">
	    			<div class="table-responsive">
						<table class="table table-hover">
	      					<thead>
	        					<tr>
	          						<th></th>
	          						<th><fmt:message key="user.pref.username" bundle="${prefBundle}"/></th>
	          						<th><fmt:message key="login.form.email" bundle="${loginBundle}"/></th>
	          						<th></th>          						
	        					</tr>
	      					</thead>
	      					<tbody>
		        			<c:forEach items="${users}" var="profile"> 		
		  						<tr onclick="window.location='/admin/user/${profile.id}'" class="${profile.banned?'danger':(profile.admin?'warning':(profile.author?'info':''))}">
			  						<td>
			  							<c:if test="${profile.banned}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.user.banned" bundle="${adminBundle}"/>" class="glyphicon glyphicon-ban-circle"></span></c:if>
			  							<c:if test="${not profile.banned && profile.admin}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.user.admin" bundle="${adminBundle}"/>" class="glyphicon glyphicon-wrench"></span></c:if>
			  							<c:if test="${not profile.banned && profile.author}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.user.author" bundle="${adminBundle}"/>" class="glyphicon glyphicon-pencil"></span></c:if>
			  							<c:if test="${not profile.banned && not profile.author && not profile.admin}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.user.basic" bundle="${adminBundle}"/>" class="glyphicon glyphicon-user"></span></c:if>
			  						</td>
			  						<td>${fn:escapeXml(profile.fullName)}</td>
		  							<td>${profile.email}</td>
		  							<td><img alt="${profile.language.language}" src="../../resources/img/ico/flag_${profile.language.language}.jpg"/></td>	  							  							
		  						</tr>
							</c:forEach>	
	      					</tbody>
	    				</table>
					</div>		 	
					</c:if>
										<nav>
						<ul class="pager">
							<c:if test="${not empty prevPage && not empty users}">
								<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-left"></span>
								 <fmt:message key="pager.previous"/></a></li>
							</c:if>
							<c:if test="${not empty nextPage}">
								<li><a href="${nextPage}"><span class="glyphicon glyphicon-chevron-right"></span>
								 <fmt:message key="pager.next"/></a></li>
							</c:if>						
						</ul>
					</nav>     				
					
				</div>
				<div class="panel-footer">
					 <c:if test="${empty param['searchQuery']}">
						<div class="pull-right">
	     				<a data-toggle="collapse" href="#collapseNewUser" aria-expanded="false" aria-controls="collapseNewUser" class="violetButton">
	     					<button class="btn btn-xs btn-primary" type="button">
	     					<span class="glyphicon glyphicon-user"></span> <fmt:message key="admin.users.new.user" bundle="${adminBundle}"/>
	     					</button>
	     				</a>
	     				<a data-toggle="collapse" href="#collapseNewMultipleUsers" aria-expanded="false" aria-controls="collapseNewMultipleUsers" class="violetButton">
	     					<button class="btn btn-xs btn-primary" type="button">
	     					<span class="glyphicon glyphicon-tasks"></span> <fmt:message key="admin.users.new.user.multiple" bundle="${adminBundle}"/>
	     					</button>
	     				</a>
						</div>
     				</c:if>
     				&nbsp;
     			</div>     				   
     			<div class="col-sm-10 col-sm-offset-1">
     				<div class="collapse" id="collapseNewUser">
						<form action="" method="post" class="form-signin" role="form">		  		    	
    						<div class="panel panel-primary">
	      						<div class="panel-heading">
		      						<fmt:message key="admin.users.new.user" bundle="${adminBundle}"/>	      				
	      						</div>
	      						<div class="panel-body">	      					
					    			<label for="inputFirstName" class="sr-only"><fmt:message key="user.pref.firstname" bundle="${prefBundle}"/></label>
					    			<input type="text" name="firstname" id="inputFirstName" class="form-control" placeholder="<fmt:message key="user.pref.firstname" bundle="${prefBundle}"/>" required>
					    			<label for="inputLastName" class="sr-only"><fmt:message key="user.pref.lastname" bundle="${prefBundle}"/></label>
					    			<input type="text" name="lastname" id="inputLastName" class="form-control" placeholder="<fmt:message key="user.pref.lastname" bundle="${prefBundle}"/>" required>
					    			<label for="inputEmail" class="sr-only"><fmt:message key="login.form.email" bundle="${loginBundle}"/></label>
					    			<input type="email" name="emailRegister" id="inputEmail" class="form-control" placeholder="<fmt:message key="login.form.email" bundle="${loginBundle}"/>" required autofocus>
	      							<ul class="list-group">
					    				<li class="list-group-item">
											<label for="radioLang"><fmt:message key="admin.user.language" bundle="${adminBundle}"/></label>
		    								<c:forEach items="${languages}" var="lang" varStatus="loop">
			    								<span>  
													<input type="radio" name="lang" id="radioLessonLang${lang.language}" value="${lang.id}" required />
													<img alt="${lang.language}" src="/resources/img/ico/flag_${lang.language}.jpg"/>
												</span>						
		    								</c:forEach>
										</li>
										<li class="list-group-item">
											<label for="authorInput"><span class="glyphicon glyphicon-pencil"></span> <fmt:message key="admin.user.author" bundle="${adminBundle}"/></label>
											<input class="pull-right" id="authorInput" type="checkbox" name="author" value="true"/>
										</li>
										<li class="list-group-item">
											<label for="adminInput"><span class="glyphicon glyphicon-wrench"></span> <fmt:message key="admin.user.admin" bundle="${adminBundle}"/></label>
											<input class="pull-right" id="adminInput" type="checkbox" name="admin" value="true"/>
										</li>
									</ul>
									<div class="help-block">
										<input class="" id="sendPaswordInput" type="checkbox" name="sendPassword" value="true" checked="checked"/> <small><fmt:message key="admin.users.new.send.password" bundle="${adminBundle}"/></small>
							</div>
	      				</div>
	      				<div class="panel-footer">
	      					<button class="btn btn-primary" type="submit"><fmt:message key="admin.users.new.user.create" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-save"></span></button>
	      				</div>
	      			</div>
		  		</form>	 
	    		</div>				 	
    			<div class="collapse" id="collapseNewMultipleUsers">
		  		<form action="" method="post" class="" role="form">		  		    	
    				<div class="panel panel-primary">
	      				<div class="panel-heading">
		      				<fmt:message key="admin.users.new.user.multiple" bundle="${adminBundle}"/>      				
	      				</div>
	      				<div class="panel-body">	      					
					    	<label for="emailListInput"><fmt:message key="admin.users.new.user.multiple.tip" bundle="${adminBundle}"/> :</label>					    	
		  					<input class="form-control" id="emailListInput" name="emailList" type="email" placeholder="<fmt:message key="admin.users.new.user.multiple.placeholder" bundle="${adminBundle}"/>" multiple pattern="^([\w+-.%]+@[\w-.]+\.[A-Za-z]{2,4},*[\W]*)+$" value="" required autofocus/>
		  					<ul class="list-group">
					    		<li class="list-group-item">
									<label for="radioLang"><fmt:message key="admin.user.language" bundle="${adminBundle}"/></label>
		    						<c:forEach items="${languages}" var="lang" varStatus="loop">
			    						<span>  
											<input type="radio" name="lang" id="radioLessonLang${lang.language}" value="${lang.id}" required />
											<img alt="${lang.language}" src="/resources/img/ico/flag_${lang.language}.jpg"/>
										</span>						
		    						</c:forEach>
								</li>
							</ul>
							<div class="help-block">
								<input class="" id="sendPaswordInput" type="checkbox" name="sendPassword" value="true" checked="checked"/> <small><fmt:message key="admin.users.new.send.password" bundle="${adminBundle}"/></small>
							</div>
	      				</div>
	      				<div class="panel-footer">
	      					<button class="btn btn-primary" type="submit"><fmt:message key="admin.users.new.user.multiple.create" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-save"></span></button>
	      				</div>
	      			</div>
		  		</form>	 
	    		</div>
  				</div>  				
				

   			</div>
		</div><!-- /.row -->
		<c:if test="${empty param['searchQuery']}">
			<div class="row">
				<nav>
					<ul class="pager">
						<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-left"></span>
						 <fmt:message key="pager.back"/></a></li>						 						
					</ul>
				</nav>	
			</div><!-- /.row -->
		</c:if>
	 </div><!-- /.container -->		
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="/resources/js/adminUsers.js"></script>	
</body>
</html>