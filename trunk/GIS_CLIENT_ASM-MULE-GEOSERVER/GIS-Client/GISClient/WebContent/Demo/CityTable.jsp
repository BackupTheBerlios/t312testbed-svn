<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List"%>
<%@page import="transaction.City"%>
<%@page import="utils.Utils"%>
<%!
	String emptyIfNull(String str){
		if(str==null){
			return "";
		}else{
			return str;
		}
	}
%>
<jsp:useBean id="trans" class="transaction.Transaction" scope="session" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
<div>
<strong> 
To history and commit/rollback:<a href="HistoryTable.jsp">History</a>
</strong>
</div>
<br>
<table border="1">
	<tr>
		<td>Name</td>
		<td>Country</td>
		<td>Administration</td>
		<td>Pop class</td>
		<td>Edit</td>
	</tr>
	<%
		List<City> cities = trans.getObjects();
		for (int i = 0; i < cities.size(); i++) {
			City city = cities.get(i);
	%>
	<TR>
		<td><%=emptyIfNull(city.getName())%></td>
		<td><%=emptyIfNull(city.getCountryName())%></td>
		<td><%=emptyIfNull(city.getAdminName())%></td>
		<td><%=emptyIfNull(city.getPopClass())%></td>
		<td><a
			href="CityEdit.jsp?<%=Utils.EDITED_CITY%>=<%=city.getCityId()%>">Edit/Remove</a></td>
	</TR>
	<%
	}
	%>


</table>
<a href="CityEdit.jsp">Add new city</a>

</body>
</html>
