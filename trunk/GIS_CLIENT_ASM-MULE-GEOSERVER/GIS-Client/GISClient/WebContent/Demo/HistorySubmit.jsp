<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="utils.Utils"%>
<%@page import="transaction.gis.GisTransactionCommiter"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="trans" class="transaction.Transaction" scope="session" />

<%
	String action = request.getParameter(Utils.METHOD);
	//String commitStr = request.getParameter(Utils.COMMIT_STRING);
	String commitStrEncoded = request.getParameter(Utils.COMMIT_STRING);
    Base64 codec = new Base64();
%>
<%
	//if (commitStr != null) {
	//Commit to do
	//String ans = trans.commit(commitStr);
	if (commitStrEncoded != null) {
        //decode the commit string from Base64
        byte[] commitStrArray = codec.decode(commitStrEncoded.getBytes());
        String commitString = new String(commitStrArray);
        String ans = trans.commit(commitString);
        String encodedAns = "";
        if (ans!=null) {
            byte[] encodedReq = codec.encode(ans.getBytes());
            encodedAns = new String(encodedReq);
        }
	
	boolean isSuccess=GisTransactionCommiter.isSuccess(ans);
	if(isSuccess){
		%><div style="color: #00AA00">SUCCESS</div><%
	}else{
		%><div style="color: #FF0000">ERROR</div><%
	}
%>
<div><a href="CityTable.jsp">To cities</a></div>
<div><a href="HistoryTable.jsp">To history</a></div>
<!--
 <div><textarea cols="100" rows="20" readonly="readonly"><%=ans%></textarea></div> 
 -->
<div>
    <form action="Response.jsp" target="result">
        <input type="hidden" name="theResp" value="<%=encodedAns%>">
        <input type="hidden" name="<%=Utils.METHOD%>" value="tr2">
        <input type="submit" value="Display answer">
    </form>
</div>
<%
		} else {
		//if (doIt.equalsIgnoreCase("yes")) {
			if (action.equalsIgnoreCase(Utils.ROLLBACK)) {
				trans.rollback();
				response.sendRedirect("CityTable.jsp");
			} else if (action.equalsIgnoreCase(Utils.COMMIT)) {
				String req = trans.getCommitString();
				
				String encodedReq = "";
				if (req!=null) {
					byte[] enc = codec.encode(req.getBytes());
					encodedReq = new String(enc);
				}
				%>
					<form method="POST" action="HistorySubmit.jsp">
						<!-- <input type="submit" value="Commit" /> -->
						<input type="submit" value="Commit">
						<input type="hidden" name="<%=Utils.COMMIT_STRING%>" value="<%=encodedReq %>">
						<div><a href="CityTable.jsp">Back to cities</a></div>
						<div><a href="HistoryTable.jsp">Back to history</a></div>
						<!-- 
						<div><textarea name="<%=Utils.COMMIT_STRING%>" rows="20" cols="100"><%=req%></textarea></div> 
						-->
                      </form>
                      <form method="POST" action="Response.jsp" target="result">
                          <input type="hidden" name="theResp" value="<%=encodedReq %>">
                          <input type="hidden" name="<%=Utils.METHOD%>" value="tr2">
                          <input type="submit" value="Display answer">						
					</form>
				<%
			}
%>
<%
}
%>
</body>
</html>