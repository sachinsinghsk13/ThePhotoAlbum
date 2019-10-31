<%@ attribute name="file" required="true" rtexprvalue="true"%>
<%@ attribute name="cls" required="false" rtexprvalue="false"%>

<img src="${pageContext.request.contextPath}/static/images/${file}" class="${cls}"/>