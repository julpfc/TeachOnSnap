<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.alert" var="alertBundle"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Alert modal: Portion of code to be included into a page --%>
	<!-- Alert modal for displaying info/error messages to the user -->
	<div class="container-fluid modal fade" id="alert" tabindex="-1" role="dialog" aria-labelledby="alertLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">	
	    	<div class="modal-content">
		      	<div class="modal-footer">
		      		<c:if test="${not empty errorMessageKey}">
			      		<h4>	      			
			      			<c:choose>
			      				<c:when test="${not empty errorType}">
			      					<span class="label label-warning"><fmt:message key="alert.heading.error" bundle="${alertBundle}"/></span>
			      				</c:when>
			      				<c:otherwise>
			      					<span class="label label-info"><fmt:message key="alert.heading.info" bundle="${alertBundle}"/></span>
			      				</c:otherwise>
			      			</c:choose>
			      		</h4>
						<p class="alertMessage">
		  					 <fmt:message key="${errorMessageKey}" bundle="${alertBundle}"/>
					  	 </p>
		       		</c:if>	       		
		        	<button class="btn btn-primary btn-sm" type="button" data-dismiss="modal"><fmt:message key="alert.button.ok" bundle="${alertBundle}"/></button>	        	
		      	</div>	 
	   		</div>
		</div>    	
	</div> <!-- /alert -->
