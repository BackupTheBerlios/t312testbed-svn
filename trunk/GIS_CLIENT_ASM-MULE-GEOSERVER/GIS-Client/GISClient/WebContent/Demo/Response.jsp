<%@page import="utils.Utils"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="org.eclipse.jst.ws.util.JspUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<jsp:useBean id="sampleGeoPortProxyid" scope="session" class="umo.GeoPortProxy" />
<%
request.setCharacterEncoding("UTF-8");
if (request.getParameter(Utils.ENDPOINT) != null 
		&& request.getParameter(Utils.ENDPOINT).length() > 0) {
	sampleGeoPortProxyid.setEndpoint(request.getParameter(Utils.ENDPOINT));
}

String method = request.getParameter(Utils.METHOD);

boolean gotMethod = false;
if (method != null) {
	try {
		if (method.equalsIgnoreCase(Utils.GET_MAP)) {
			response.reset();
			out.clear();
			gotMethod = true;
			String version = request.getParameter("version");
			String layers = request.getParameter("layers");
			String styles = request.getParameter("styles");
			String srs = request.getParameter("srs");
			String bbox = request.getParameter("bbox");
			String width = request.getParameter("width");
			String height = request.getParameter("height");
			String format = request.getParameter("format");
			byte[] mapArray = sampleGeoPortProxyid.getMap(
					version, layers, styles, srs, bbox,
					width, height, format);
			if (mapArray == null) {
				%>
				<%=mapArray%>
				<%
			} else {
			//	String result = "[";
			//	for (int i = 0; i < mapArray.length; i++) {
			//		result = result	+ mapArray[i] + ",";
			//	}
			//	int length = result.length();
			//	result = result.substring(0, (length - 1))	+ "]";

			
			BufferedInputStream input = null;
			BufferedOutputStream output = null;
			
			input = new BufferedInputStream(new ByteArrayInputStream(mapArray));
			int contentLength = input.available();
			
			response.reset();
			response.setContentLength(contentLength);
			response.setContentType("image/png");
			response.setHeader(
	                "Content-disposition", "inline; filename=\"map\"");
	        output = new BufferedOutputStream(response.getOutputStream());
	 		
	         // Write file contents to response.
	         while (contentLength-- > 0) {
	         	output.write(input.read());
	         	
	         }
	 
	         // Finalize task.
	         output.flush();
			}
		} else if (method.equalsIgnoreCase(Utils.GET_CAPABILITIES)) {
			response.reset();
			out.clear();
			response.setContentType("text/xml");
			
			gotMethod = true;
			String service = request.getParameter("service");
			String capabilities = sampleGeoPortProxyid
					.getCapabilities(service);
			if (capabilities == null) {
				out.write(capabilities);
			} else {
				//String markup = JspUtils.markup(String.valueOf(capabilities));
				String m = new String(capabilities.getBytes(), "UTF-8");
				out.write(m);
			}
		} else if (method.equalsIgnoreCase(Utils.DESCRIBE_FEATURE_TYPE)) {
			response.reset();
			out.clear();
			response.setContentType("text/xml");
			
			gotMethod = true;

			String service = request.getParameter("service");
			String description = sampleGeoPortProxyid
					.describeFeatureType(service);
			if (description == null) {
				out.write(description);
			} else {
				//String markup = JspUtils.markup(String.valueOf(capabilities));
				String result = new String(description.getBytes(), "UTF-8");
				out.write(result);
			}
		} else if (method.equalsIgnoreCase(Utils.GET_FEATURE)) {
			response.reset();
			out.clear();
			response.setContentType("text/xml");
			gotMethod = true;
			String service = request.getParameter("service");
			String typeNames = request.getParameter("typeName");
			String[] typeNameList = new String[1];
			typeNameList[0] = typeNames;
			String description = sampleGeoPortProxyid
					.getFeature(service, typeNameList);
			System.out.println(description);
			if (description == null) {
				out.write(description);
			} else {
				//String markup = JspUtils.markup(String.valueOf(capabilities));
				String result = description;
				//String result = new String(description.getBytes(), "UTF-8");
				out.write(result);
			}
		} else if (method.equalsIgnoreCase(Utils.INSERT)) {
			response.reset();
			out.clear();
			response.setContentType("text/xml");
			gotMethod = true;
			String service = request.getParameter("service");
			String cityname = request.getParameter("cityname");
			String latitude = request.getParameter("latitude");
			String longitude = request.getParameter("longitude");
			String[] typeNameList = new String[1];
			String body = Utils.addCityWithAuth(cityname, latitude, longitude);
			String description = sampleGeoPortProxyid
					.transaction(body);
			System.out.println(description);
			if (description == null) {
				out.write(description);
			} else {
				//String markup = JspUtils.markup(String.valueOf(capabilities));
				String result = description;
				//String result = new String(description.getBytes(), "UTF-8");
				out.write(result);
			}
		} else if (method.equalsIgnoreCase(Utils.TRANSACTION)) {
			gotMethod = true;
			String service = request.getParameter("service");
			String version = request.getParameter("version");
			String format = request.getParameter("format");
			String updateSequence = request
					.getParameter("updateSequence");
			String capablities = sampleGeoPortProxyid
					.getCapabilitiesWithOptionalParams(
					service, version,
					format,
					updateSequence);
			if (capablities == null) {
				%>
				<%=capablities%>
				<%
			} else {
				String result = JspUtils.markup(String.valueOf(capablities));
				%>
				<%=result%>
				<%
			}
		 
		// --- Added by ED ---
		} else if (method.equals("tr2")) {
	        gotMethod = true;
	        if (request.getParameter("theResp")!=null) {
	            Base64 codec = new Base64();
	            response.setContentType("text/html");
	            String encodedResponse=request.getParameter("theResp");
	            byte[] decodedArray = codec.decode(encodedResponse.getBytes());
	            String xmlText = new String(decodedArray);
	            //remove leading newline chars
	            %>
				<textarea cols="80" rows="10"><%=xmlText%></textarea>
				<%
	        }
	    // End --- Added by ED ---
	    } else {
		//TODO
		}

	} catch (Exception e) {
		%>
		exception:
		<%=e%>
		<%
		return;
	}
}
if (!gotMethod) {
	%>
	result: N/A
	<%
}
%>
