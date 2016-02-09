<%@ page contentType="text/xml; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- XML defining available logos and tile color for Microsoft browsers/Windows menu --%>
<?xml version="1.0" encoding="utf-8"?>
<browserconfig>
  <msapplication>
    <tile>
      <square70x70logo src="${pageContext.request.contextPath}/resources/favicon/mstile-70x70.png"/>
      <square150x150logo src="${pageContext.request.contextPath}/resources/favicon/mstile-150x150.png"/>
      <square310x310logo src="${pageContext.request.contextPath}/resources/favicon/mstile-310x310.png"/>
      <wide310x150logo src="${pageContext.request.contextPath}/resources/favicon/mstile-310x150.png"/>
      <TileColor>#6f5499</TileColor>
    </tile>
  </msapplication>
</browserconfig>

