<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.alert" var="alertBundle"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>

<div class="container-fluid modal fade" id="confirm" tabindex="-1" role="dialog" aria-labelledby="confirmLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">	
    	<div class="modal-content">
	      	<div class="modal-footer">	      		
	      		<h4>	      			
					<span class="label label-warning"><fmt:message key="confirm.heading" bundle="${alertBundle}"/></span>	      			
	      		</h4>
				<p id="confirmBody" class="confirmMessage"></p>
	       		
	        	<a id="confirmOK"><button class="btn btn-primary btn-sm" type="button"><fmt:message key="confirm.button.ok" bundle="${alertBundle}"/></button></a>	        	
	        	<button class="btn btn-primary btn-sm" type="button" data-dismiss="modal"><fmt:message key="confirm.button.cancel" bundle="${alertBundle}"/></button>
	      	</div>	 
   		</div>
	</div>
    	
</div> <!-- /container -->