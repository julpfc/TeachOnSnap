<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="i18n.views.common"/>
<%-- Common page javascript scritps: portion of code to be included into page's bottom --%>
 	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="${host}/resources/js/ext/ie10-viewport-bug-workaround.js"></script>
    
    <!-- Begin Cookie Consent plugin by Silktide - http://silktide.com/cookieconsent -->
	<script type="text/javascript">
    	window.cookieconsent_options = {
    			"message":"<fmt:message key='cookie.consent.message'/>","dismiss":"<fmt:message key='cookie.consent.accept'/>","learnMore":"More info","link":null,"theme":"dark-bottom"};
	</script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/cookieconsent2/1.0.9/cookieconsent.min.js"></script>
	<!-- End Cookie Consent plugin -->
    
    <!-- TeachOnSnap common JS -->
    <c:if test="${not empty loginError}">
    	<script src="${host}/resources/js/login.js"></script>
    </c:if>
    <c:if test="${not empty errorMessageKey}">
    	<script src="${host}/resources/js/alert.js"></script>
    </c:if>