<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
pageContext.setAttribute("result", "hello");
%>
<body>
	<%-- <%=request.getAttribute("result") %> 입니다. --%>
	${result}<br />
	${requestScope.result}<br />
	${names[1]}<br />
	${notice.title}<br />
	${empty param.n? '값이비어있음' : param.n}<br />
	${param.n / 2}<br />
	${header.accept}<br />
	${pageContext.request.method}
</body>
</html>