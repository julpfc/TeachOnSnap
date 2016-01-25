<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${userLang.language}"/>
<fmt:setBundle basename="com.julvez.pfc.teachonsnap.i18n.views.common"/>
<div class="footer">
	<div class="container-fluid">
        <div class="col-sm-3">
        	&nbsp;
        	<a href="http://it.uc3m.es"><img class="img-responsive img-rounded" src="${host}/resources/img/it_uc3m.gif" alt="it.uc3m.es"/></a>
        </div>
      	<div class="col-sm-5 text-center">
        	&nbsp;
        	<p class="text-muted"><fmt:message key="footer.text"/></p>        	
        	<p class="text-muted"><fmt:message key="footer.license"/></p>
        </div>
      	<div class="col-sm-4">
	      	<a href="http://www.uc3m.es"><img class="img-responsive img-rounded" src="${host}/resources/img/uc3m.png" alt="uc3m.es"/></a>
      	</div>
      </div>
 </div>