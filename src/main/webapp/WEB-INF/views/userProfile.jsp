<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.userprofile" var="profBundle"/>
<fmt:setBundle basename="i18n.views.admin" var="adminBundle"/>
<fmt:setBundle basename="i18n.views.stats" var="statsBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Edit user profile --%>
<!DOCTYPE html>
<html> 
<head>	
	<c:import url="./import/head_bootstrap.jsp"/>
	<title>
		<fmt:message key="app.name"/> - 
		<c:choose>
			<c:when test="${empty profile}">
				<fmt:message key="user.profile.heading" bundle="${profBundle}"/>
			</c:when>
			<c:otherwise>
				<fmt:message key="admin.user.heading" bundle="${adminBundle}"/>			
			</c:otherwise>
		</c:choose>
	</title>
</head>
<body>
	<c:import url="./import/nav.jsp"/>
	<%-- Variable for common fields/forms --%>	
	<c:set var="userProfile" value="${empty profile?user:profile}"></c:set>
	<div class="content container-fluid">		
		<div class="row">
			<c:if test="${not empty profile}">
				<!-- Block user -->
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
						<a class="alert-link pull-right" onclick="return showEditStatus(true);"><fmt:message key="user.profile.edit" bundle="${profBundle}"/></a>
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
				</div><!-- /Block user -->	
				
				<!-- User roles -->
				<div class="alert ${profile.admin?'alert-warning':(profile.author?'alert-info':'')} col-sm-6" role="alert">										
					<label><fmt:message key="admin.user.type" bundle="${adminBundle}"/>: </label>
				 	<span id="span-type">
						<c:if test="${profile.admin}"><fmt:message key="admin.user.admin" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-wrench"></span></c:if>
	  					<c:if test="${profile.author}"><fmt:message key="admin.user.author" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-pencil"></span></c:if>
	  					<c:if test="${not profile.author && not profile.admin}"><fmt:message key="admin.user.basic" bundle="${adminBundle}"/> <span class="glyphicon glyphicon-user"></span></c:if>
						<a class="alert-link pull-right" onclick="return showEditType(true);"><fmt:message key="user.profile.edit" bundle="${profBundle}"/></a>
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
				</div><!-- User roles -->
			</c:if> 
			<!-- User name -->			
			<div class="alert col-sm-6 ${empty profile?'alert-info':''}" role="alert">									
				<label><fmt:message key="user.profile.username" bundle="${profBundle}"/>: </label>
			 	<span id="span-name">
					${fn:escapeXml(userProfile.fullName)}
					<a class="alert-link pull-right" onclick="return showEditName(true);"><fmt:message key="user.profile.edit" bundle="${profBundle}"/></a>
				</span>
				<form id="usernameForm" action="" method="POST">
					<div id="div-name" class="hidden">
						<span class="input-group-addon"><fmt:message key="user.profile.firstname" bundle="${profBundle}"/>:</span>
						<input name="firstname" type="text" class="form-control" placeholder="${fn:escapeXml(userProfile.firstName)}" required="required">
						<span class="input-group-addon"><fmt:message key="user.profile.lastname" bundle="${profBundle}"/>:</span>
						<input name="lastname" type="text" class="form-control" placeholder="${fn:escapeXml(userProfile.lastName)}" required="required">
				      	<span class="pull-right">
				        	<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-floppy-disk"></span></button>
				        	<button class="btn btn-default" type="button" onclick="return showEditName(false);"><span class="glyphicon glyphicon-remove"></span></button>
				      	</span>
				      	<span>&nbsp;</span>
				    </div>
				</form>
			</div><!-- /User name -->
			<!-- User password -->
			<div class="alert col-sm-6 ${empty profile?'alert-warning':''}" role="alert">					
				<label><fmt:message key="user.profile.password" bundle="${profBundle}"/>: </label>
			 	<span id="span-password">					
					<span class="glyphicon glyphicon-asterisk"></span><span class="glyphicon glyphicon-asterisk"></span><span class="glyphicon glyphicon-asterisk"></span><span class="glyphicon glyphicon-asterisk"></span><span class="glyphicon glyphicon-asterisk"></span>
					<a class="alert-link pull-right" onclick="return showEditPassword(true);"><fmt:message key="user.profile.changepassword" bundle="${profBundle}"/></a>
				</span>
				<form id="passwordForm" action="" method="POST">
					<div id="div-password" class="hidden">
						<c:if test="${empty profile}">
							<span class="input-group-addon"><fmt:message key="user.profile.oldpassword" bundle="${profBundle}"/>:</span>
							<input id="pwo" name="pwo" type="password" class="form-control" required="required">
						</c:if>
						<span class="input-group-addon"><fmt:message key="user.profile.newpassword" bundle="${profBundle}"/>:</span>
						<input id="pwn1" name="pwn" type="password" class="form-control" required="required">
						<span class="input-group-addon"><fmt:message key="user.profile.repeatpassword" bundle="${profBundle}"/>:</span>
						<input id="pwn2" type="password" class="form-control" required="required">
				      	<span class="pull-right">
				        	<button id="passwordFormButton" class="btn btn-default" type="submit" ><span class="glyphicon glyphicon-floppy-disk"></span></button>
				        	<button class="btn btn-default" type="button" onclick="return showEditPassword(false);"><span class="glyphicon glyphicon-remove"></span></button>
				      	</span>
				      	<span>&nbsp;</span>
						<c:if test="${not empty profile}">
							<a class="alert-link" href="?sendPassword=true"><fmt:message key="admin.user.password.resendPassword" bundle="${adminBundle}"/></a>
						</c:if>
				      	<input id="pwnMatch" type="hidden" class="form-control" value="<fmt:message key="user.profile.validator.matchpasswords" bundle="${profBundle}"/>">					      	
					</div>
			    </form>				
			</div><!-- /User password -->
			<c:if test="${empty profile}">
			<!-- Avatar -->	
			<div class="alert alert-info col-sm-6" role="alert">
					<label><fmt:message key="user.profile.avatar" bundle="${profBundle}"/>: </label>						
				 	<span id="span-avatar">
				 		<span class="author-avatar"><img alt="avatar" src="https://www.gravatar.com/avatar/${user.MD5}?s=20&d=identicon" width="20" height="20"></span>
						<a class="alert-link pull-right" onclick="return showEditAvatar(true);"><fmt:message key="user.profile.edit" bundle="${profBundle}"/></a>
					</span>
					<div id="div-avatar" class="hidden">
					<span class="author-avatar pull-left"><img alt="avatar" src="https://www.gravatar.com/avatar/${user.MD5}?s=60&d=identicon" width="60" height="60"></span>															
						<fmt:message key="user.profile.avatar.tip" bundle="${profBundle}"/> <a href="https://es.gravatar.com">Gravatar <span class="glyphicon glyphicon-new-window"></span></a>
				      	<span class="pull-right">				        	
				        	<button class="btn btn-default" type="button" onclick="return showEditAvatar(false);"><span class="glyphicon glyphicon-chevron-up"></span></button>
				      	</span>
					</div>
				</div><!-- /avatar -->
			</c:if>
			<c:if test="${not empty profile}">
			<!-- extra info -->	
			<div class="alert alert-info col-sm-6" role="alert">
				<label><fmt:message key="admin.user.extra" bundle="${adminBundle}"/>: </label>						
				 	<span id="span-extra">
				 		${fn:substring(fn:escapeXml(profile.extraInfo),0,30)}
				 		${fn:length(fn:escapeXml(profile.extraInfo)) > 30?'...':''}
						<a class="alert-link pull-right" onclick="return showEditExtra(true);"><fmt:message key="user.profile.show" bundle="${profBundle}"/></a>
					</span>
					<div id="div-extra" class="hidden">
						<form id="extraForm" action="" method="POST">
							<textarea name="extraInfo" class="form-control" maxlength="255" placeholder="<fmt:message key="admin.user.extra.info.placeholder" bundle="${adminBundle}"/>" required="required">${fn:escapeXml(profile.extraInfo)}</textarea>									
							<fmt:message key="admin.user.extra.info.tip" bundle="${adminBundle}"/>															
				      		<span class="pull-right">
				        		<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-floppy-disk"></span></button>
				        		<button class="btn btn-default" type="button" onclick="return showEditExtra(false);"><span class="glyphicon glyphicon-remove"></span></button>
				      		</span>					      	
						</form>
					</div>
				</div><!-- /extra info -->
			</c:if>
			<c:if test="${not empty userProfile.URIName}">
				<!-- author -->
				<div class="alert alert-default col-sm-6" role="alert">					
					<label>&nbsp;</label>					
				 	<span>
						<a class="alert-link pull-left" href="${host}/author/${userProfile.URIName}"><fmt:message key="admin.user.lessons" bundle="${adminBundle}"/></a>
						<a class="alert-link pull-right" href="${host}/drafts/${userProfile.id}"><fmt:message key="admin.user.drafts" bundle="${adminBundle}"/></a>
					</span>
				 </div><!-- /author -->
			</c:if>	
			<c:if test="${not empty profile}">
				<!-- groups -->
				<div class="alert alert-warning col-sm-6" role="alert">					
				 	<label><fmt:message key="admin.user.groups" bundle="${adminBundle}"/>: </label>
			 	<span id="span-groups">
					<span class="badge">${fn:length(groups)}</span>
					<c:if test="${not empty groups}">
						<a class="alert-link pull-right" onclick="return showGroups(true);"><fmt:message key="user.profile.edit" bundle="${profBundle}"/></a>
					</c:if>
				</span>
				
					<div id="div-groups" class="hidden">
						<ul class="list-group">
						<c:forEach items="${groups}" var="group">
							<li class="list-group-item"><a href="${host}/admin/group/${group.id}">${group.groupName} <span class="badge pull-right">${fn:length(group.users)}</span></a></li>
						</c:forEach>
						</ul>
				      	<span class="pull-right">				        	
				        	<button class="btn btn-default" type="button" onclick="return showGroups(false);"><span class="glyphicon glyphicon-chevron-up"></span></button>
				      	</span>
				      	<span>&nbsp;</span>
				    </div>			
				</div><!-- /groups -->
			</c:if>
			<c:if test="${not empty userProfile.authorFollowed || not empty userProfile.lessonFollowed}">
				<!-- followings -->
				<div class="alert alert-info col-sm-6" role="alert">					
				 	<label><fmt:message key="user.follow.heading" bundle="${profBundle}"/>: </label>
			 		<span class="label label-info">${fn:length(userProfile.authorFollowed)} <fmt:message key="user.author.follows" bundle="${profBundle}"/></span>			 		
			 		<span class="label label-warning">${fn:length(userProfile.lessonFollowed)} <fmt:message key="user.lesson.follows" bundle="${profBundle}"/></span>
			 		<span class="pull-right">				        	
				    	<a class="alert-link pull-right" href="${host}/follow/${userProfile.id}"><fmt:message key="user.profile.follow" bundle="${profBundle}"/></a>
				     </span>
				</div><!-- /followings -->
			</c:if>
			<c:if test="${not empty userProfile.URIName}">
				<!-- Stats -->
				<div class="alert alert-default col-sm-6" role="alert">					
					<label>&nbsp;</label>					
				 	<span>
						<a class="alert-link pull-left" href="${host}/stats/author/month/${userProfile.id}"><fmt:message key="stats.show.month" bundle="${statsBundle}"/></a>
						<a class="alert-link pull-right" href="${host}/stats/author/year/${userProfile.id}"><fmt:message key="stats.show.year" bundle="${statsBundle}"/></a>
					</span>
				 </div><!-- /stats -->
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
	</div><!-- /.content -->
	<c:import url="./import/footer.jsp"/>
	<!-- Javascript -->
	<c:import url="./import/js_bootstrap.jsp"/>	
	<script src="${host}/resources/js/userProfile.js"></script>
	<!-- /Javascript -->	
</body>
</html>