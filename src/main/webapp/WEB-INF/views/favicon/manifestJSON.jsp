<%@ page contentType="text/json; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- JSON defining available favicons for Google Chrome/Android --%>
{
	"name": "TeachOnSnap",
	"icons": [
		{
			"src": "${fn:replace(pageContext.request.contextPath,'/','\\/')}\/resources\/favicon\/android-chrome-36x36.png",
			"sizes": "36x36",
			"type": "image\/png",
			"density": "0.75"
		},
		{
			"src": "${fn:replace(pageContext.request.contextPath,'/','\\/')}\/resources\/favicon\/android-chrome-48x48.png",
			"sizes": "48x48",
			"type": "image\/png",
			"density": "1.0"
		},
		{
			"src": "${fn:replace(pageContext.request.contextPath,'/','\\/')}\/resources\/favicon\/android-chrome-72x72.png",
			"sizes": "72x72",
			"type": "image\/png",
			"density": "1.5"
		},
		{
			"src": "${fn:replace(pageContext.request.contextPath,'/','\\/')}\/resources\/favicon\/android-chrome-96x96.png",
			"sizes": "96x96",
			"type": "image\/png",
			"density": "2.0"
		},
		{
			"src": "${fn:replace(pageContext.request.contextPath,'/','\\/')}\/resources\/favicon\/android-chrome-144x144.png",
			"sizes": "144x144",
			"type": "image\/png",
			"density": "3.0"
		},
		{
			"src": "${fn:replace(pageContext.request.contextPath,'/','\\/')}\/resources\/favicon\/android-chrome-192x192.png",
			"sizes": "192x192",
			"type": "image\/png",
			"density": "4.0"
		}
	]
}
