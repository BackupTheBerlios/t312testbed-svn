<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="utils.Utils"%>
<HTML>
<HEAD>
<TITLE>Menu</TITLE>
</HEAD>
<BODY background="#E8E8E8">
<H1>Menu</H1>
<UL>
	<LI><A HREF="Input.jsp?method=<%= Utils.GET_CAPABILITIES %>"
		TARGET="inputs"> GetCapabilities</A></LI>
	<LI><A HREF="Input.jsp?method=<%= Utils.GET_MAP %>"
		TARGET="inputs"> GetMap</A></LI>
	<LI><A
		HREF="Input.jsp?method=<%= Utils.DESCRIBE_FEATURE_TYPE %>"
		TARGET="inputs"> DescribeFeatureType</A></LI>
	<LI><A
		HREF="Input.jsp?method=<%= Utils.GET_FEATURE %>"
		TARGET="inputs"> GetFeature</A></LI>
	<LI><A
		HREF="Input.jsp?method=<%= Utils.TRANSACTION %>"
		TARGET="inputs"> Transaction</A>
		<UL>
		<LI><A
		HREF="Input.jsp?method=<%= Utils.TRANSACTION %>"
		TARGET="inputs"> Cities</A></LI>
		<LI><A
		HREF="Input.jsp?method=<%= Utils.TRANSACTION_ACTIONS %>"
		TARGET="inputs"> Actions</A></LI>
		</UL>
	</LI>

</UL>
</BODY>
</HTML>