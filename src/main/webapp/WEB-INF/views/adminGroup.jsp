<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="i18n.views.userprofile" var="profBundle"/>
<fmt:setBundle basename="i18n.views.login" var="loginBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Manage user group --%>
<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		<fmt:message key="app.name"/> - <fmt:message key="admin.group.heading" bundle="${adminBundle}"/>			
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<c:import url="./import/confirm.jsp"/>
	<div class="content container-fluid">		
		<div class="row">
			<div class="panel panel-default">
				<!-- Group name -->
     			<div class="panel-heading">
			 		<span id="span-name">
     					<label><fmt:message key="admin.group.name" bundle="${adminBundle}"/>: </label>
						${fn:escapeXml(group.groupName)}
						<a onclick="return showEditName(true);"> (<fmt:message key="user.profile.edit" bundle="${profBundle}"/>)</a>
					</span>
					<button class="btn btn-sm btn-danger pull-right" type="button" onclick="confirm('?removeGroup=true','admin.group.remove.confirm');"><span class="glyphicon glyphicon-remove"></span> <fmt:message key="admin.group.remove" bundle="${adminBundle}"/></button>
					<div id="div-name" class="hidden">
						<form action="" method="post">
			    			<div class="col-sm-5 input-group">
					    		<input type="text" name="groupName" value="${group.groupName}" id="inputGroupName" class="form-control" placeholder="<fmt:message key="admin.group.name" bundle="${adminBundle}"/>" required>			      					
		      					<div class="input-group-btn">
		        					<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-floppy-disk"></span></button>
		        					<button class="btn btn-default" type="button" onclick="return showEditName(false);"><span class="glyphicon glyphicon-remove"></span></button>
		      					</div><!-- /btn-group -->		      					
		    				</div><!-- /input-group -->
						</form>
				    </div>				    
				</div>
				<!-- Group users -->
				<div class="panel-body">
					<c:if test="${empty group.users}">
						<h4><fmt:message key="admin.group.users.empty" bundle="${adminBundle}"/></h4>
					</c:if>
					<c:if test="${not empty group.users}">
	    			<div class="table-responsive">
						<table class="table table-hover">
	      					<thead>
	        					<tr>
	          						<th></th>
	          						<th><fmt:message key="user.profile.username" bundle="${profBundle}"/></th>
	          						<th><fmt:message key="login.form.email" bundle="${loginBundle}"/></th>
	          						<th></th>
	          						<th></th>          						
	        					</tr>
	      					</thead>
	      					<tbody>
		        			<c:forEach items="${group.users}" var="profile"> 		
		  						<tr class="${profile.banned?'danger':(profile.admin?'warning':(profile.author?'info':''))}">
			  						<td>
			  							<img alt="${profile.language.language}" src="../../resources/img/ico/flag_${profile.language.language}.jpg"/>
			  							<c:if test="${profile.banned}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.user.banned" bundle="${adminBundle}"/>" class="glyphicon glyphicon-ban-circle"></span></c:if>
			  							<c:if test="${not profile.banned && profile.admin}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.user.admin" bundle="${adminBundle}"/>" class="glyphicon glyphicon-wrench"></span></c:if>
			  							<c:if test="${not profile.banned && profile.author}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.user.author" bundle="${adminBundle}"/>" class="glyphicon glyphicon-pencil"></span></c:if>
			  							<c:if test="${not profile.banned && not profile.author && not profile.admin}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.user.basic" bundle="${adminBundle}"/>" class="glyphicon glyphicon-user"></span></c:if>
			  						</td>
			  						<td>${fn:escapeXml(profile.fullName)}</td>
		  							<td>${profile.email}</td>
		  							<td><a href="${host}/admin/user/${profile.id}"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.show.user" bundle="${adminBundle}"/>" class="glyphicon glyphicon-eye-open"></span></a></td>
		  							<td><a onclick="confirm('?removeUser=${profile.id}','admin.group.remove.user.confirm');"><span data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.group.remove.user" bundle="${adminBundle}"/>" class="glyphicon glyphicon-remove"></span></a></td>	  							  							
		  						</tr>
		  						<c:set var="emailList" value="${emailList}${empty emailList?'':','}${profile.email}"/>
							</c:forEach>	
	      					</tbody>
	    				</table>
					</div>	
					<span id="span-export">
					 	<button class="btn btn-xs btn-primary pull-right" type="button" onclick="return showExport(true);"><span class="glyphicon glyphicon-copy"></span> <fmt:message key="admin.group.export" bundle="${adminBundle}"/></button>     																	
					</span>
					<div id="div-export" class="hidden">						
			    			<div class="col-sm-7 col-sm-offset-5 input-group">
					    		<input type="text" value="${emailList}" class="inputExport form-control"/>			      					
		      					<div class="input-group-btn">
		        					<button class="btn btn-default" type="button" onclick="document.querySelector('.inputExport').select();document.execCommand('copy');return showExport(false);"><span class="glyphicon glyphicon-copy"></span></button>
		        					<button class="btn btn-default" type="button" onclick="return showExport(false);"><span class="glyphicon glyphicon-remove"></span></button>
		      					</div><!-- /btn-group -->		      					
		    				</div><!-- /input-group -->
						
				    </div>	
					</c:if>					 
				</div>
				<!-- Group add multiple users -->
				<div class="panel-footer">					 
					<label for="emailListInput"><fmt:message key="admin.users.new.user.multiple.tip" bundle="${adminBundle}"/> :</label>					    	
						<form action="" method="post">
			    			<div class="input-group violetButton">
			  					<input class="form-control" id="emailListInput" name="emailList" type="email" placeholder="<fmt:message key="admin.users.new.user.multiple.placeholder" bundle="${adminBundle}"/>" multiple pattern="^([\w+-.%]+@[\w-.]+\.[A-Za-z]{2,4},*[\W]*)+$" value="" required="required"/>
		      					<div class="input-group-btn">
		        					<button class="btn btn-primary" type="submit"><fmt:message key="admin.group.add.user.multiple.add" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-save"></span></button>        
		      					</div><!-- /btn-group -->		      					
		    				</div><!-- /input-group -->
						</form>  				
  					<div class="help-block">
  						<fmt:message key="admin.group.add.user.multiple.tip" bundle="${adminBundle}"/>
  					</div>
     			</div>     
     		</div> 
     		<!-- Group followings & broadcast -->
     		<div class="alert alert-default">
	     		<span class="pull-right">
	     			<a href="${host}/admin/group/follow/${group.id}"><button class="btn btn-info btn-sm" type="button"><span class="glyphicon glyphicon-star"></span> <fmt:message key="admin.group.follow.heading" bundle="${adminBundle}"/></button></a>     		    			
	     		</span>
	     		<span class="pull-right">
	     			<a href="${host}/admin/broadcast/${group.id}"><button class="btn btn-warning btn-sm" type="button"><span class="glyphicon glyphicon-bullhorn"></span> <fmt:message key="admin.group.broadcast.button" bundle="${adminBundle}"/></button></a>
	     			&nbsp;
	     		</span>
     			&nbsp;
     		</div>    		
     	</div><!-- /.row -->     	
		<div class="row">
			<nav>
				<ul class="pager">
					<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-left"></span>
					 <fmt:message key="pager.back"/></a></li>						 						
				</ul>
			</nav>	
		</div><!-- /.row -->
	 </div><!-- /.content -->		
	<c:import url="./import/footer.jsp"/>
	<!-- Javascript -->
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script type="text/javascript">
		<!--	    
		var msg = {};
		msg['admin.group.remove.user.confirm'] = 	"<fmt:message key="admin.group.remove.user.confirm" bundle="${adminBundle}"/>";
		msg['admin.group.remove.confirm'] = 		"<fmt:message key="admin.group.remove.confirm" bundle="${adminBundle}"/>";
		//-->
	</script>
	<script src="${host}/resources/js/adminGroup.js"></script>	
	<script src="${host}/resources/js/confirm.js"></script>	
	<!-- /Javascript -->
</body>
</html>