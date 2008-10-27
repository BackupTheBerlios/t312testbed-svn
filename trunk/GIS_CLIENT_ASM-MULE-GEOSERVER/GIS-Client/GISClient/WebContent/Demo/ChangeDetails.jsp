<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="transaction.actions.IAction"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="trans" class="transaction.Transaction" scope="session" />
<div>
	Not implemented
	<a href="HistoryTable.jsp">History</a>
</div>
<%
	String idStr=request.getParameter(IAction.ACTION_ID);
	int id=Integer.parseInt(idStr);
%>

</body>
</html>