<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="${host}/resources/js/ext/ie10-viewport-bug-workaround.js"></script>
    
    <!-- TeachOnSnap JS -->
    <c:if test="${not empty loginError}">
    	<script src="${host}/resources/js/login.js"></script>
    </c:if>
    <c:if test="${not empty errorMessageKey}">
    	<script src="${host}/resources/js/alert.js"></script>
    </c:if>