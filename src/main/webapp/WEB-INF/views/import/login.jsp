<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.login" var="loginBundle"/>
<fmt:setBundle basename="i18n.views.userprofile" var="profBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Application login/register/about modals: portion of code to be included into a page --%>
	<!-- Login modal for displaying login form -->
	<div class="container-fluid modal fade" id="login" tabindex="-1" role="dialog" aria-labelledby="loginLabel" aria-hidden="true">
		<div class="modal-dialog">
			<c:if test="${not empty loginError}">
				<!-- Login error message -->
				<div class="alert alert-danger alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				  	<strong><fmt:message key="login.form.error.message.strong" bundle="${loginBundle}"/></strong>
				  	 <fmt:message key="login.form.error.message" bundle="${loginBundle}"/>
				</div>
			</c:if>
    		<div class="modal-content">
      			<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
        				<span class="sr-only">X</span>
       				</button>
       				<h2 class="modal-title" id="loginLabel">
	       				<span class="modal-icons">
	       					<span class="glyphicon glyphicon-facetime-video"></span>
			        		<span class="glyphicon glyphicon-book"></span>
			        	</span>
       					<fmt:message key="app.name"/>
       				</h2>
     		 	</div>
     		 	<!-- login -->
    			<form action="${host}/login/" method="post" class="form-signin" id="loginForm" >
		      		<div class="modal-body">
		      			<h2 class="form-signin-heading"><fmt:message key="login.form.title" bundle="${loginBundle}"/></h2>
		        		<label for="inputEmail" class="sr-only"><fmt:message key="login.form.email" bundle="${loginBundle}"/></label>
				    	<input type="email" name="email" id="inputEmail" class="form-control" placeholder="<fmt:message key="login.form.email" bundle="${loginBundle}"/>" required autofocus>
				    	<label for="inputPassword" class="sr-only"><fmt:message key="login.form.password" bundle="${loginBundle}"/></label>
				    	<input type="password" name="password" id="inputPassword" class="form-control" placeholder="<fmt:message key="login.form.password" bundle="${loginBundle}"/>" required>			    	 
				    	<c:if test="${pageContext.request.scheme eq 'http' and (not fn:contains(fn:replace(host,'http://',''),':'))}">				    							    	
				    		<a href="${fn:replace(host,'http://','https://')}/profile"><span class="glyphicon glyphicon-lock"></span> <fmt:message key="login.form.securelogin" bundle="${loginBundle}"/></a>				   			
				   		</c:if>
			    	</div> 
      				<a data-toggle="collapse" href="#collapseForgotPass" aria-expanded="false" aria-controls="collapseForgotPass"><fmt:message key="login.form.forgotpass" bundle="${loginBundle}"/></a>      			     		
	      			<div class="modal-footer">	  
	        			<button class="btn btn-primary" type="submit"><fmt:message key="nav.menu.login"/> <span class="glyphicon glyphicon-log-in"></span></button>
	      			</div>      			
		  		</form><!-- /login -->
		  		<!-- forgot -->	  
		  		<form action="${host}/login/" method="post" class="form-signin">
	    			<div class="collapse" id="collapseForgotPass">
						<div class="well">
							<label for="inputEmailRemind" class="sr-only"><fmt:message key="login.form.email" bundle="${loginBundle}"/></label>
		    				<input type="email" name="emailRemind" id="inputEmailRemind" class="form-control" required="required" placeholder="<fmt:message key="login.form.email" bundle="${loginBundle}"/>">
		    				<div>
	  							<button class="btn btn-primary form-control" type="submit"><fmt:message key="login.form.forgotpass.remind" bundle="${loginBundle}"/> <span class="glyphicon glyphicon-envelope"></span></button>
	  						</div>
						</div>
					</div> 
				</form><!-- /forgot -->
   			</div>
		</div>
	</div> <!-- /login modal -->
	
	<!-- Register modal -->
	<div class="container-fluid modal fade" id="register" tabindex="-1" role="dialog" aria-labelledby="registerLabel" aria-hidden="true">
		<div class="modal-dialog">		
    		<div class="modal-content">
      			<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
        				<span class="sr-only">X</span>
       				</button>
       				<h2 class="modal-title" id="registerLabel">
		       			<span class="modal-icons">
		       				<span class="glyphicon glyphicon-facetime-video"></span>
				        	<span class="glyphicon glyphicon-book"></span>
				        </span>
	       				<fmt:message key="app.name"/>
       				</h2>
     		 	</div>
     		 	<!-- Register --> 
    			<form action="${host}/login/" method="post" class="form-signin">		  		    	
    				<div class="modal-body">
	      				<h2 class="form-signin-heading"><fmt:message key="nav.menu.register"/></h2>
			    		<label for="inputFirstName" class="sr-only"><fmt:message key="user.profile.firstname" bundle="${profBundle}"/></label>
			    		<input type="text" name="firstname" id="inputFirstName" class="form-control" placeholder="<fmt:message key="user.profile.firstname" bundle="${profBundle}"/>" required>
			    		<label for="inputLastName" class="sr-only"><fmt:message key="user.profile.lastname" bundle="${profBundle}"/></label>
			    		<input type="text" name="lastname" id="inputLastName" class="form-control" placeholder="<fmt:message key="user.profile.lastname" bundle="${profBundle}"/>" required>
			    		<label for="inputEmailRegister" class="sr-only"><fmt:message key="login.form.email" bundle="${loginBundle}"/></label>
			    		<input type="email" name="emailRegister" id="inputEmailRegister" class="form-control" placeholder="<fmt:message key="login.form.email" bundle="${loginBundle}"/>" required>
	    			</div>				 	
	      			<div class="modal-footer"> 
		    			<button class="btn btn-primary" type="submit"><fmt:message key="nav.menu.register"/> <span class="glyphicon glyphicon-edit"></span></button>
	      			</div>      			
		  		</form><!-- /register -->	  
   			</div>
		</div>	
	</div> <!-- /register modal -->
	
	<!-- About modal -->
	<div class="container-fluid modal fade" id="about" tabindex="-1" role="dialog" aria-labelledby="aboutLabel" aria-hidden="true">
		<div class="modal-dialog">		
    		<div class="modal-content">
      			<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
        				<span class="sr-only">X</span>
       				</button>
       				<h2 class="modal-title" id="aboutLabel">
	       				<span class="modal-icons">
	       					<span class="glyphicon glyphicon-facetime-video"></span>
			        		<span class="glyphicon glyphicon-book"></span>
			        	</span>
       					<fmt:message key="nav.menu.explore.about"/>
       				</h2>
     		 	</div> 
    			<div class="modal-body form-about">      			
      				<div class="form-about"><fmt:message key="about"/></div>		    	
      				<div class="form-about"><fmt:message key="about.glyphicons"/><a href="http://glyphicons.com">Glyphicons.com</a></div>
      				<div class="form-about text-center">
      					<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">
	        				<img alt="Creative Commons license" src="https://licensebuttons.net/l/by-sa/4.0/88x31.png" />
	        			</a>
	        		</div>
      				<div class="form-about">
	        			<fmt:message key="footer.license"/>
	        		</div>
    			</div>
   			</div>
		</div>	
	</div> <!-- /About modal -->