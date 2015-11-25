<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.preferences" var="prefBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		<fmt:message key="app.name"/> - 
		<c:choose>
			<c:when test="${empty profile}">
				<fmt:message key="user.pref.heading" bundle="${prefBundle}"/>
			</c:when>
			<c:otherwise>
				<fmt:message key="admin.user.heading" bundle="${adminBundle}"/>			
			</c:otherwise>
		</c:choose>
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>	
	<c:set var="userProfile" value="${empty profile?user:profile}"></c:set>
	<div class="content container-fluid">		
		<div class="row">
			<c:if test="${not empty profile}">
				<div class="alert ${profile.banned?'alert-danger':'alert-success'} col-sm-6" role="alert">
					<label><fmt:message key="admin.user.status" bundle="${adminBundle}"/>: </label>
						<c:choose>
							<c:when test="${profile.banned}">
								<fmt:message key="admin.user.status.banned" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-ban-circle"></span>						
							</c:when>
							<c:otherwise>
								<fmt:message key="admin.user.status.active" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-ok-circle"></span>						
							</c:otherwise>
						</c:choose>
				 	<span id="span-status">
						<a class="alert-link pull-right" onclick="return showEditStatus(true);"><fmt:message key="user.pref.edit" bundle="${prefBundle}"/></a>
					</span>
					<form id="statusForm" action="" method="POST">
						<div id="div-status" class="hidden">
							<c:choose>
								<c:when test="${profile.banned}">
									<ul class="list-group">
										<li class="list-group-item">
											<fmt:message key="admin.user.status.banned.reason" bundle="${adminBundle}"/>: ${fn:escapeXml(profile.bannedInfo.reason)}									
										</li>
										<li class="list-group-item">
											<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${profile.bannedInfo.date}"/>					
										</li>
										<li class="list-group-item">
											<fmt:message key="admin.user.admin" bundle="${adminBundle}"/>: ${fn:escapeXml(profile.bannedInfo.admin.fullName)}
										</li>
									</ul>
									<label class="alert-success" for="unblockInput"><span class="glyphicon glyphicon-ok-circle"></span> <fmt:message key="admin.user.unblock" bundle="${adminBundle}"/></label>
									<input id="unblockInput" type="checkbox" name="unblockUser" value="true" required="required"/>
								</c:when>
								<c:otherwise>
									<ul class="list-group">
										<li class="list-group-item">
											<fmt:message key="admin.user.status.banned.reason" bundle="${adminBundle}"/>: <textarea name="blockUserReason" class="form-control" maxlength="255" placeholder="<fmt:message key="admin.user.status.banned.reason" bundle="${adminBundle}"/>" required="required"></textarea>									
										</li>
									</ul>
									<label class="alert-danger" for="blockInput"><span class="glyphicon glyphicon-ban-circle"></span> <fmt:message key="admin.user.block" bundle="${adminBundle}"/></label>
									<input id="blockInput" type="checkbox" name="blockUser" value="true" required="required"/>
								</c:otherwise>
							</c:choose>
					      	<span class="pull-right">
					        	<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-floppy-disk"></span></button>
					        	<button class="btn btn-default" type="button" onclick="return showEditStatus(false);"><span class="glyphicon glyphicon-remove"></span></button>
					      	</span>
					      	<span>&nbsp;</span>
					    </div>
					</form>
				</div>
			
			
				<div class="alert ${profile.admin?'alert-warning':(profile.author?'alert-info':'')} col-sm-6" role="alert">										
					<label><fmt:message key="admin.user.type" bundle="${adminBundle}"/>: </label>
				 	<span id="span-type">
						<c:if test="${profile.admin}"><fmt:message key="admin.user.admin" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-wrench"></span></c:if>
	  					<c:if test="${profile.author}"><fmt:message key="admin.user.author" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-pencil"></span></c:if>
	  					<c:if test="${not profile.author && not profile.admin}"><fmt:message key="admin.user.basic" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-user"></span></c:if>
						<a class="alert-link pull-right" onclick="return showEditType(true);"><fmt:message key="user.pref.edit" bundle="${prefBundle}"/></a>
					</span>
					<form id="typeForm" action="" method="POST">
						<div id="div-type" class="hidden">
							<ul>
								<li>
								<label for="authorInput"><span class="glyphicon glyphicon-pencil"></span> <fmt:message key="admin.user.author" bundle="${adminBundle}"/></label>
								<input id="authorInput" type="checkbox" ${profile.author?'checked="checked"':''} name="author" value="true"/>
								</li>
								<li>
								<label for="adminInput"><span class="glyphicon glyphicon-wrench"></span> <fmt:message key="admin.user.admin" bundle="${adminBundle}"/></label>
								<input id="adminInput" type="checkbox" ${profile.admin?'checked="checked"':''} name="admin" value="true"/>
								</li>
							</ul>								
					      	<span class="pull-right">
					        	<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-floppy-disk"></span></button>
					        	<button class="btn btn-default" type="button" onclick="return showEditType(false);"><span class="glyphicon glyphicon-remove"></span></button>
					      	</span>
					      	<span>&nbsp;</span>
					    </div>
					</form>
				</div>
			</c:if> 			
			<div class="alert col-sm-6 ${empty profile?'alert-info':''}" role="alert">									
				<label><fmt:message key="user.pref.username" bundle="${prefBundle}"/>: </label>
			 	<span id="span-name">
					${fn:escapeXml(userProfile.fullName)}
					<a class="alert-link pull-right" onclick="return showEditName(true);"><fmt:message key="user.pref.edit" bundle="${prefBundle}"/></a>
				</span>
				<form id="usernameForm" action="" method="POST">
					<div id="div-name" class="hidden">
						<span class="input-group-addon"><fmt:message key="user.pref.firstname" bundle="${prefBundle}"/>:</span>
						<input name="firstname" type="text" class="form-control" placeholder="${fn:escapeXml(userProfile.firstName)}" required="required">
						<span class="input-group-addon"><fmt:message key="user.pref.lastname" bundle="${prefBundle}"/>:</span>
						<input name="lastname" type="text" class="form-control" placeholder="${fn:escapeXml(userProfile.lastName)}" required="required">
				      	<span class="pull-right">
				        	<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-floppy-disk"></span></button>
				        	<button class="btn btn-default" type="button" onclick="return showEditName(false);"><span class="glyphicon glyphicon-remove"></span></button>
				      	</span>
				      	<span>&nbsp;</span>
				    </div>
				</form>
			</div>
			<div class="alert col-sm-6 ${empty profile?'alert-warning':''}" role="alert">					
				<label><fmt:message key="user.pref.password" bundle="${prefBundle}"/>: </label>
			 	<span id="span-password">					
					<span class="glyphicon glyphicon-asterisk"></span><span class="glyphicon glyphicon-asterisk"></span><span class="glyphicon glyphicon-asterisk"></span><span class="glyphicon glyphicon-asterisk"></span><span class="glyphicon glyphicon-asterisk"></span>
					<a class="alert-link pull-right" onclick="return showEditPassword(true);"><fmt:message key="user.pref.changepassword" bundle="${prefBundle}"/></a>
				</span>
				<form id="passwordForm" action="" method="POST">
					<div id="div-password" class="hidden">
						<c:if test="${empty profile}">
							<span class="input-group-addon"><fmt:message key="user.pref.oldpassword" bundle="${prefBundle}"/>:</span>
							<input id="pwo" name="pwo" type="password" class="form-control" required="required">
						</c:if>
						<span class="input-group-addon"><fmt:message key="user.pref.newpassword" bundle="${prefBundle}"/>:</span>
						<input id="pwn1" name="pwn" type="password" class="form-control" required="required">
						<span class="input-group-addon"><fmt:message key="user.pref.repeatpassword" bundle="${prefBundle}"/>:</span>
						<input id="pwn2" type="password" class="form-control" required="required">
				      	<span class="pull-right">
				        	<button id="passwordFormButton" class="btn btn-default" type="submit" ><span class="glyphicon glyphicon-floppy-disk"></span></button>
				        	<button class="btn btn-default" type="button" onclick="return showEditPassword(false);"><span class="glyphicon glyphicon-remove"></span></button>
				      	</span>
						<c:if test="${not empty profile}">
							<a class="alert-link" href="?sendPassword=true"><fmt:message key="admin.user.password.resendPassword" bundle="${adminBundle}"/></a>
						</c:if>
				      	<input id="pwnMatch" type="hidden" class="form-control" value="<fmt:message key="user.pref.validator.matchpasswords" bundle="${prefBundle}"/>">					      	
					</div>
			    </form>				
			</div>		
			<c:if test="${not empty profile.URIName}">
				<div class="alert alert-default col-sm-6" role="alert">					
				 	<span>
						<a class="alert-link pull-left" href="/author/${profile.URIName}"><fmt:message key="admin.user.lessons" bundle="${adminBundle}"/></a>
						<a class="alert-link pull-right" href="/drafts/${profile.id}"><fmt:message key="admin.user.drafts" bundle="${adminBundle}"/></a>
					</span>					
				</div>
			</c:if>	
			<c:if test="${not empty profile}">
				<div class="alert alert-info col-sm-6" role="alert">					
				 	<label><fmt:message key="admin.user.groups" bundle="${adminBundle}"/>: </label>
			 	<span id="span-groups">
					<span class="badge">${fn:length(groups)}</span>
					<c:if test="${not empty groups}">
						<a class="alert-link pull-right" onclick="return showGroups(true);"><fmt:message key="user.pref.edit" bundle="${prefBundle}"/></a>
					</c:if>
				</span>
				
					<div id="div-groups" class="hidden">
						<ul class="list-group">
						<c:forEach items="${groups}" var="group">
							<li class="list-group-item"><a href="/admin/group/${group.id}">${group.groupName} <span class="badge pull-right">${fn:length(group.users)}</span></a></li>
						</c:forEach>
						</ul>
				      	<span class="pull-right">				        	
				        	<button class="btn btn-default" type="button" onclick="return showGroups(false);"><span class="glyphicon glyphicon-chevron-up"></span></button>
				      	</span>
				      	<span>&nbsp;</span>
				    </div>			
				</div>
			</c:if>	
		</div><!-- /.row -->
		<div class="row">
			<nav>
				<ul class="pager">
					<li><a href="${prevPage}"><span class="glyphicon glyphicon-chevron-left"></span>
					 <fmt:message key="pager.back"/></a></li>						 						
				</ul>
			</nav>	
		</div><!-- /.row -->
	 </div><!-- /.container -->		
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="/resources/js/preferences.js"></script>	
</body>
</html>