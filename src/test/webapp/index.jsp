<%@ taglib prefix="contarini" uri="http://stefanbirkner.github.com/contarini/tags" %>
<%
	pageContext.setAttribute(
		"info",
		new com.github.stefanbirkner.contarini.WebCrawlerInfo().withCanonical("/index.html"));
%>
<contarini:webCrawlerInfo info="${info}"/>