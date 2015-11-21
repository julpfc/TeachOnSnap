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
					<label>Estado: </label>
						<c:choose>
							<c:when test="${profile.banned}">
								Bloqueada <span class="glyphicon glyphicon-ban-circle"></span>						
							</c:when>
							<c:otherwise>
								Activa <span class="glyphicon glyphicon-ok-circle"></span>						
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
											Razón del bloqueo: ${fn:escapeXml(profile.bannedInfo.reason)}									
										</li>
										<li class="list-group-item">
											<fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${profile.bannedInfo.date}"/>					
										</li>
										<li class="list-group-item">
											Administrador: ${fn:escapeXml(profile.bannedInfo.admin.fullName)}
										</li>
									</ul>
									<label class="alert-success" for="unblockInput"><span class="glyphicon glyphicon-ok-circle"></span> Desbloquear usuario</label>
									<input id="unblockInput" type="checkbox" name="unblockUser" value="true" required="required"/>
								</c:when>
								<c:otherwise>
									<ul class="list-group">
										<li class="list-group-item">
											Razón del bloqueo: <textarea name="blockUserReason" class="form-control" maxlength="255" placeholder="Razón" required="required"></textarea>									
										</li>
									</ul>
									<label class="alert-danger" for="blockInput"><span class="glyphicon glyphicon-ban-circle"></span> Bloquear usuario</label>
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
					<label>Tipo de usuario: </label>
				 	<span id="span-type">
						<c:if test="${profile.admin}">Administrator <span class="glyphicon glyphicon-wrench"></span></c:if>
	  					<c:if test="${profile.author}">Author <span class="glyphicon glyphicon-pencil"></span></c:if>
	  					<c:if test="${not profile.author && not profile.admin}">Basic user <span class="glyphicon glyphicon-user"></span></c:if>
						<a class="alert-link pull-right" onclick="return showEditType(true);"><fmt:message key="user.pref.edit" bundle="${prefBundle}"/></a>
					</span>
					<form id="typeForm" action="" method="POST">
						<div id="div-type" class="hidden">
							<ul>
								<li>
								<label for="authorInput"><span class="glyphicon glyphicon-pencil"></span> Author</label>
								<input id="authorInput" type="checkbox" ${profile.author?'checked="checked"':''} name="author" value="true"/>
								</li>
								<li>
								<label for="adminInput"><span class="glyphicon glyphicon-wrench"></span> Administrator</label>
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
							<a class="alert-link" href="?sendPassword=true">Reenviar correo de cambio de contraseña</a>
						</c:if>
				      	<input id="pwnMatch" type="hidden" class="form-control" value="<fmt:message key="user.pref.validator.matchpasswords" bundle="${prefBundle}"/>">					      	
					</div>
			    </form>				
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
	 </div><!-- /.container -->		
	<c:import url="./import/footer.jsp"/>
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="/resources/js/preferences.js"></script>	
</body>
</html>