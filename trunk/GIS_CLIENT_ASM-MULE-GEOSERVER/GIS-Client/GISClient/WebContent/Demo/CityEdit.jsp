<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="transaction.CityBean"%>
<%@page import="java.util.Map"%>
<%@page import="transaction.City"%>
<%@page import="utils.Utils"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="trans" class="transaction.Transaction" scope="session" />
<%
	String editedId = request.getParameter(Utils.EDITED_CITY);
	String addOrEdit = null;
	String[] values = CityBean.getValuesNoId();
	Map<String, String> vals = null;
	if (editedId == null) {
		addOrEdit = Utils.INSERT;

		//insert new
	} else {
		addOrEdit = Utils.UPDATE;
		City city = trans.findCityById(editedId);
		if (city != null) {
			vals = CityBean.asMap(city);
		}
		//edit current
	}
%>
<form method="GET" action="CityAction.jsp">
<input type="hidden" name="<%=Utils.EDITED_CITY%>" value="<%=editedId%>"> 
<table>
	<%
			for (String name : values) {
			String value = null;
			if (vals != null) {
				value = vals.get(name);
			}
			if (value == null) {
				value = "";
			}
	%>
	<tr>
		<td><%=name%></td>
		<td><input type="text" name="<%=name%>" value="<%=value%>" /></td>
	</tr>
	<%
	}
	%>
</table>
	<input type="submit" name="<%=Utils.METHOD%>" value="<%=addOrEdit%>"></form>
<a href="CityTable.jsp">Back</a>
<%
if (editedId != null) {
%>
<form method="GET" action="CityAction.jsp">
<input type="hidden" name="<%=Utils.EDITED_CITY%>" value="<%=editedId%>">
<input type="submit" name="<%=Utils.METHOD%>" value="<%=Utils.DELETE%>">
</form>
<%
}
%>

</body>
</html>