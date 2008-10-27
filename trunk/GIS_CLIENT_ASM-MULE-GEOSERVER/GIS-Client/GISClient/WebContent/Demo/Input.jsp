<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="utils.Utils"%>
<jsp:useBean id="sampleGeoPortProxyid" scope="session" class="umo.GeoPortProxy" />
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.ByteArrayInputStream"%>
<HTML>
<HEAD>
<TITLE>Inputs</TITLE>
</HEAD>
<BODY>
<H1>Inputs</H1>

<%
String method = request.getParameter(Utils.METHOD);
//int methodID = 0;
//if (method == null) methodID = -1;

boolean valid = true;

//if(methodID != -1) methodID = Integer.parseInt(method);

if(method != null) {
	out.write("<H3>" + method + "</H3>");
	if (method.equalsIgnoreCase(Utils.TRANSACTION)){
		response.sendRedirect("CityTable.jsp");
	}
	else if (method.equalsIgnoreCase(Utils.TRANSACTION_ACTIONS)){
		response.sendRedirect("HistoryTable.jsp");
	} else if (method.equalsIgnoreCase(Utils.GET_CAPABILITIES)){ 
		valid = false;
		%>
		<FORM METHOD="POST" ACTION="Response.jsp" TARGET="result">
		<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
		<TABLE>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">service:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="service" SIZE=20 VALUE="WFS"></TD>
		</TR>
		</TABLE>
		<BR>
		<INPUT TYPE="SUBMIT" VALUE="Invoke">
		<INPUT TYPE="RESET" VALUE="Clear">
		</FORM>
		<%
	} else if (method.equalsIgnoreCase(Utils.GET_MAP)){
		valid = false;
		%>
		<FORM METHOD="POST" ACTION="Response.jsp" TARGET="result">
		<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
		<TABLE>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">version:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="version" SIZE=20 VALUE="1.1.1"></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">layers:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="layers" SIZE=20 
			VALUE="topp:tasmania_state_boundaries,topp:tasmania_water_bodies,topp:tasmania_roads,topp:tasmania_cities" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">styles:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="styles" SIZE=20 
			VALUE="green,cite_lakes,simple_roads,NamedCapitals" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">srs:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="srs" SIZE=20 VALUE="EPSG:4326" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">bbox:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="bbox" SIZE=20 VALUE="143.87,-43.65,148.46,-39.62" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">width:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="width" SIZE=20 VALUE="400" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">height:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="height" SIZE=20 VALUE="400" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">format:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="format" SIZE=20 VALUE="image/png" ></TD>
		</TR>
		</TABLE>
		<BR>
		<INPUT TYPE="SUBMIT" VALUE="Invoke">
		<INPUT TYPE="RESET" VALUE="Clear">
		</FORM>
		<%
	} else if (method.equalsIgnoreCase(Utils.DESCRIBE_FEATURE_TYPE)){
	
		valid = false;
		%>
		<FORM METHOD="POST" ACTION="Response.jsp" TARGET="result">
		<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
		<INPUT TYPE="TEXT" NAME="service" VALUE="wfs">
		<BR>
		<INPUT TYPE="SUBMIT" VALUE="Invoke">
		<INPUT TYPE="RESET" VALUE="Clear">
		</FORM>
		<%
	} else if (method.equalsIgnoreCase(Utils.GET_FEATURE)){
	
		valid = false;
		%>
		<FORM METHOD="POST" ACTION="Response.jsp" TARGET="result">
		<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
		<TABLE>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">service:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="service" SIZE=20 VALUE="wfs" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">typeName:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="typeName" SIZE=20 VALUE="topp:tasmania_cities" ></TD>
		</TR>
		</TABLE>
		<BR>
		<INPUT TYPE="SUBMIT" VALUE="Invoke">
		<INPUT TYPE="RESET" VALUE="Clear">
		</FORM>
		<%
	} else if (method.equalsIgnoreCase(Utils.INSERT)){
		
		valid = false;
		%>
		<FORM METHOD="POST" ACTION="Response.jsp" TARGET="result">
		<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
		<TABLE>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">service:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="service" SIZE=20 VALUE="wfs" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">city name:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="cityname" SIZE=20 VALUE="new city" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">latitude:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="latitude" SIZE=20 VALUE="145.23" ></TD>
		</TR>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">longitude:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="longitude" SIZE=20 VALUE="-41.36" ></TD>
		</TR>
		</TABLE>
		<BR>
		<BR>
		<INPUT TYPE="SUBMIT" VALUE="Invoke">
		<INPUT TYPE="RESET" VALUE="Clear">
		</FORM>
		<%
		
	} else if (method.equalsIgnoreCase(Utils.TRANSACTION)){
	
		valid = false;
		%>
		<FORM METHOD="POST" ACTION="Response.jsp" TARGET="result">
		<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
		<TABLE>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">service:</TD>
		<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="service" SIZE=20 VALUE="wfs" ></TD>
		</TR>
	
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT" valign="top">body:</TD>
		<TD ALIGN="left">
		   <TEXTAREA name="thetext" rows="10" cols="80">
	   First line of initial text.
	   Second line of initial text.
	   </TEXTAREA>
		</TD>
		</TR>
		</TABLE>
		<BR>
		<BR>
		<INPUT TYPE="SUBMIT" VALUE="Invoke">
		<INPUT TYPE="RESET" VALUE="Clear">
		</FORM>
		<%
	} else {
		//TODO hh
	}
}

if (valid) {
%>
Select a method to test.
<%
    return;
}
%>

</BODY>
</HTML>
