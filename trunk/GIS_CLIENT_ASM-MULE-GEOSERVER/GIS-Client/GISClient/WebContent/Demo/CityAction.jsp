<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="trans" class="transaction.Transaction" scope="session" />
<%!public String getEditedCityId(HttpServletRequest request) {
		return request.getParameter(Utils.EDITED_CITY);
	}

	public City getCity(HttpServletRequest request) {
		return CityBean.createCity(request);
	}%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="utils.Utils"%>
<%@page import="transaction.CityBean"%>
<%@page import="transaction.City"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
	String method = request.getParameter(Utils.METHOD);
	String id = getEditedCityId(request);
%><div>method=<%=method%><br>
city=<%=id%></div>
<%
	if (method.equalsIgnoreCase(Utils.DELETE)) {
		trans.deleteByID(id);
	} else if (method.equalsIgnoreCase(Utils.INSERT)) {
		City city=getCity(request);
		trans.insert(city);
	} else if (method.equalsIgnoreCase(Utils.UPDATE)) {
		City city=getCity(request);
		trans.updateById(id,city);
	}

	response.sendRedirect("CityTable.jsp");
%>
<a href="CityTable.jsp">return to table</a>

</body>
</html>