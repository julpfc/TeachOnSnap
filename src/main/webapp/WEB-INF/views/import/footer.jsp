<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Common page footer: portion of code to be included into page's bottom --%>
	<!-- Footer -->
	<div class="footer">
		<div class="container-fluid">
	        <div class="col-sm-3">
	        	&nbsp;
	        	<a href="http://it.uc3m.es"><img class="img-responsive img-rounded" src="${host}/resources/img/it_uc3m.gif" alt="it.uc3m.es"/></a>
	        </div>
	      	<div class="col-sm-5 text-center">
	        	&nbsp;
	        	<p class="text-muted"><fmt:message key="footer.text"/></p>        	
	        	<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">
	        		<img alt="Creative Commons license" src="https://licensebuttons.net/l/by-sa/4.0/88x31.png" />
	        	</a>
	        	<p class="text-muted"><fmt:message key="footer.license"/></p>
	        </div>
	      	<div class="col-sm-4">
				<a href="http://www.uc3m.es"><img class="img-responsive img-rounded" src="${host}/resources/img/uc3m.png" alt="uc3m.es"/></a>
			</div>
		</div>
	</div><!-- /footer -->