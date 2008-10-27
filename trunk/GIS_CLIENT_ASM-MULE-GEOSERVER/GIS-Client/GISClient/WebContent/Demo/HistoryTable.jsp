<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List"%>
<%@page import="transaction.actions.IAction"%>
<%@page import="transaction.City"%>
<%@page import="transaction.actions.ActionDelete"%>
<%@page import="transaction.actions.ActionInsert"%>
<%@page import="transaction.actions.IUpdateAction"%>
<%@page import="utils.Utils"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%!String getActionName(IAction<?> act) {
		if (act instanceof ActionDelete) {
			return "Delete";
		} else if (act instanceof ActionInsert) {
			return "Insert";
		} else if (act instanceof IUpdateAction) {
			return "Update";
		} else {
			return "Unknown";
		}
	}%>
<body>
<div>
<strong> 
To table of cities: <a href="CityTable.jsp">Cities</a>
</strong>
</div>
<br>
<jsp:useBean id="trans" class="transaction.Transaction" scope="session" />

<table border="1">
	<tr>
		<td>Action</td>
		<td>City Id</td>
		<td>City Name</td>
	</tr>
	<%
		List<IAction<City>> actions = trans.getHistory();
		for (IAction<City> action : actions) {
			City city = action.getObject();
	%>
	<TR>
		<td><%=getActionName(action)%></td>
		<td><%=city.getCityId()%></td>
		<td><%=city.getName()%></td>
	</TR>
	<%
	}
	%>
</table>
<form action="HistorySubmit.jsp"><input type="submit"
	name="<%=Utils.METHOD%>" value="<%=Utils.COMMIT%>"> <input
	type="submit" name="<%=Utils.METHOD%>" value="<%=Utils.ROLLBACK%>">
</form>

</body>
</html>