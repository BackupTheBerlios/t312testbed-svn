package umo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.mule.config.i18n.CoreMessages;
//import org.mule.config.i18n.Messages;


public class GisService implements IGisService {
	
	
	static final String TEXT_XML = "text/xml";
	static final String CONTENT_TYPE = "Content-Type";
	static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	protected static final char NAME_VALUE_SEPARATOR = '=';
	protected static final char PARAMETERS_SEPARATOR = '&';
	protected static final char START_QUERY_SEPARATOR = '?';
	
	private Map<String, String> requiredParams;
	private Map<String, String> optionalParams;

	private String wmsServiceUrl;
	private String wfsServiceUrl;
	
	public GisService() {
	}
	
	protected void initParams() {
		this.requiredParams = new HashMap<String, String>();
		this.optionalParams = new HashMap<String, String>();
	}

	protected void setRESTParams(StringBuffer url, Map<String, String> args,
			boolean optional) {
		char separator;

		if (url.indexOf(String.valueOf(START_QUERY_SEPARATOR)) > -1) {
			separator = PARAMETERS_SEPARATOR;
		} else {
			separator = START_QUERY_SEPARATOR;
		}

		for (Iterator<Entry<String, String>> iterator = args.entrySet()
				.iterator(); iterator.hasNext();) {
			Entry<String, String> entry = (Entry<String, String>) iterator
					.next();
			String name = entry.getKey();
			String value = entry.getValue();

			if (value == null || value.length() == 0) {
				if (!optional) {
					throw new IllegalArgumentException(
							CoreMessages.propertyIsNotSetOnEvent(value)
							.toString());
//					throw new IllegalArgumentException(new Message(
//							Messages.X_PROPERTY_IS_NOT_SET_ON_EVENT, value)
//							.toString());
				} else {
					continue;
					// do not add null or empty parameter
				}
			}
			url.append(separator);
			separator = PARAMETERS_SEPARATOR;
			url.append(name).append(NAME_VALUE_SEPARATOR).append(value);
		}
	}
	
	protected URLConnection prepareUrlConnection(String urlString,
			String httpMethod) throws MalformedURLException, IOException,
			ProtocolException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		((HttpURLConnection) connection).setRequestMethod(httpMethod);
		connection.setDoOutput(true);
		connection.setDoInput(true); // Only if you expect to read a response
		connection.setUseCaches(false); // Highly recommended
		connection.setRequestProperty(CONTENT_TYPE, TEXT_XML);
		return connection;
	}

	protected void printData(URLConnection connection, String data)
			throws IOException {
		PrintWriter output = new PrintWriter(new OutputStreamWriter(connection
				.getOutputStream()));
		output.print(data);
		output.flush();
		output.close();
	}

	protected String readResponse(URLConnection connection) throws IOException {
		//System.out.println(connection.getInputStream().available());
		StringBuffer sb = new StringBuffer();
		int ch = 0;
		while ((ch = connection.getInputStream().read()) != -1) {
			sb.append((char) ch);
		}
		String response = sb.toString();
		//System.out.println(sb);
		if(response.startsWith(XML_HEADER.substring(0, 5))) {
			return response;
		} else { 
			return XML_HEADER + sb.toString();
		}
	}

	//**************************************************************************
		
	public String sendRequest(String service, String requestBody)
			throws MalformedURLException, ProtocolException, IOException {
		URLConnection connection = prepareUrlConnection(getServiceUrl(service) + "/service="
				+ service, "POST");
		printData(connection, requestBody);
		String readResponse = readResponse(connection);
		return readResponse;
	}
	
	public String getCapabilities(String service) {
		initParams();
		requiredParams.put(SERVICE, service);
		requiredParams.put(REQUEST, GET_CAPABILITIES);
		

		StringBuffer urlBuffer = new StringBuffer(getServiceUrl(service));
		setRESTParams(urlBuffer, requiredParams, false);
		setRESTParams(urlBuffer, optionalParams, true);
		//System.out.println("Prepare getCapabilities request");
		//System.out.println(urlBuffer);

		// String urlString = SERVICE_URL + "?service=" + service
		// + "&Request=GetCapabilities";
		try {
			URL url = new URL(urlBuffer.toString());
			// URL url2 = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			br.close();
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Could not connect";
	}

	public String getCapabilitiesWithOptionalParams(String service,
			String version, String format, String updateSequence) {

		initParams();

		requiredParams.put(SERVICE, service);
		requiredParams.put(REQUEST, GET_CAPABILITIES);

		optionalParams.put(VERSION, version);
		optionalParams.put(FORMAT, format);
		optionalParams.put(UPDATESEQUENCE, updateSequence);

		StringBuffer urlBuffer = new StringBuffer(getServiceUrl(service));
		setRESTParams(urlBuffer, requiredParams, false);
		setRESTParams(urlBuffer, optionalParams, true);
		//System.out.println(urlBuffer);

		// String urlString = SERVICE_URL + "?service=" + service
		// + "&Request=GetCapabilities";
		try {
			URL url = new URL(urlBuffer.toString());
			// URL url2 = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			br.close();
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Could not connect";
	}
	
	//************ wfs ********************
	
	/**
	 * Invokes DescribeFeatureType request with required parameters only
	 * 
	 * @return a string with a response
	 */
	public String describeFeatureType(String service) throws IOException {
		return describeFeatureTypeWithOptionalParams(service, null, null, null,
				null);
	}

	/**
	 * Invokes DescribeFeatureType request with additional optional parameters
	 * 
	 * @return a string with a response
	 */
	public String describeFeatureTypeWithOptionalParams(String service,
			String outputFormat, List<String> typenameList, String version,
			Map<String, String> namespaces) throws MalformedURLException,
			ProtocolException, IOException {

		StringBuffer postRequest = prepareDescribeFeatureTypeRequest(service,
				outputFormat, typenameList, version, namespaces);
		//System.out.println(postRequest);
		URLConnection connection = prepareUrlConnection(getWfsServiceUrl(),
				"POST");
		printData(connection, postRequest.toString());
		String readResponse = readResponse(connection);
		return readResponse;
	}
	
	/**
	 * Prepares a DescribeFeatureType request that will be send to an OGC server
	 */
	private StringBuffer prepareDescribeFeatureTypeRequest(String service,
			String version, List<String> typenames, String outputFormat,
			Map<String, String> namespaces) {
		StringBuffer postRequestBody = new StringBuffer();
		postRequestBody.append("<?xml version=\"1.0\" ?>");
		postRequestBody.append("<DescribeFeatureType");
		postRequestBody.append(" version=\"" + version + "\"");
		postRequestBody.append(" service=\"" + service + "\"");
		if (outputFormat != null && outputFormat.length() > 0) {
			// if (outputFormat.matches("(XMLSCHEMA)|(text/xml;
			// subtype=gml/2\\.1\\.2)|(text/xml; subtype=gml/3\\.1\\.)")) {
			postRequestBody.append(" outputFormat=");
			postRequestBody.append(outputFormat);
			// }
		}
		postRequestBody.append(" xmlns=\"http://www.opengis.net/wfs\"");
		if (namespaces != null) {
			Set<Entry<String, String>> eSet = namespaces.entrySet();
			for (Entry<String, String> entry : eSet) {
				String name = entry.getKey();
				String url = entry.getValue();
				postRequestBody.append(" xmlns:" + name + "=\"" + url + "\"");
			}
		}
		postRequestBody
				.append(XMLSCHEMA_INSTANCE_NS);
		postRequestBody
				.append(WFS_SCHEMA_LOCATION);
		if(typenames != null) {
		for (String type : typenames) {
			postRequestBody.append("<TypeName>");
			postRequestBody.append(type);
			postRequestBody.append("</TypeName>");
		}
		}
		postRequestBody.append("</DescribeFeatureType>");
		return postRequestBody;
	}

	// ---------------------- GET FEATURE ----------------------

	public String getFeature(String service, String[] typenames)
			throws MalformedURLException, ProtocolException, IOException {
		return getFeatureWithOptionalParams(service, typenames, null, null,
				null, null, "GML2", null, null, null);
	}

	public String getFeatureWithOptionalParams(String service,
			String[] typenames, String bbox, String[] featureIds,
			String filter, String maxFeatures, String outputFormat,
			String[] propertyNames, String version, String resultType) throws MalformedURLException,
			ProtocolException, IOException {
		
		List<String> typenameList = Arrays.asList(typenames);
		List<String> featureIdList = null;//TODO Arrays.asList(featureIds);
		List<String> propertyNameList = null; //TODO Arrays.asList(propertyNames);
		
		StringBuffer getRequest = prepareGetFeatureRequest(service,
				typenameList, bbox, featureIdList, filter, maxFeatures,
				outputFormat, propertyNameList, version, resultType);
		//System.out.println("\n get request ...\n" + getRequest);
		URLConnection connection = prepareUrlConnection(getRequest.toString(),
				"GET");
		String readResponse = readResponse(connection);
		return readResponse;
	}

	private StringBuffer prepareGetFeatureRequest(String service,
			List<String> typenameList, String bbox, List<String> featureIdList,
			String filter, String maxFeatures, String outputFormat,
			List<String> propertyNameList, String version, String resultType) {

		initParams();

		requiredParams.put(REQUEST, GET_FEATURE);
		requiredParams.put(SERVICE, service);
		
		StringBuffer typenamesBuffer = new StringBuffer();
		for (String typename : typenameList) {
			typenamesBuffer.append(typename);
			typenamesBuffer.append(',');
		}
		typenamesBuffer.setLength(typenamesBuffer.length() - 1);
		requiredParams.put(TYPENAME, typenamesBuffer.toString());

		optionalParams.put(BBOX, bbox);
		optionalParams.put(FILTER, filter);
		optionalParams.put(MAX_FEATURES, maxFeatures);
		optionalParams.put(OUTPUT_FORMAT, outputFormat);
		optionalParams.put(RESULT_TYPE, resultType);
		
		optionalParams.put(VERSION, version);

		if(featureIdList != null) {
			StringBuffer featureIdsBuffer = new StringBuffer();
			for (String feature : featureIdList) {
				featureIdsBuffer.append(feature);
				featureIdsBuffer.append(',');
			}
			featureIdsBuffer.setLength(featureIdsBuffer.length() - 1);
			optionalParams.put(FEATURE_ID, featureIdsBuffer.toString());
		}
		
		if(propertyNameList != null) {
			StringBuffer propertyNameBuffer = new StringBuffer();
			for (String property : propertyNameList) {
				propertyNameBuffer.append(property);
				propertyNameBuffer.append(',');
			}
			propertyNameBuffer.setLength(propertyNameBuffer.length() - 1);
			optionalParams.put(PROPERTY_NAME, propertyNameBuffer.toString());
		}
		
		return prepareGetRequest(getWfsServiceUrl());
	}

	protected StringBuffer prepareGetRequest(String url) {
		StringBuffer requestUrl = new StringBuffer(url);
		setRESTParams(requestUrl, requiredParams, false);
		setRESTParams(requestUrl, optionalParams, true);
		return requestUrl;
	}
	
	/* (non-Javadoc)
	 * @see umo.IWFSTAdapter#transaction(java.lang.String)
	 */
	public String transaction(String requestBody) throws MalformedURLException,
			ProtocolException, IOException {

		URLConnection connection = prepareUrlConnection(getWfsServiceUrl(),
				"POST");
		printData(connection, requestBody);
		String readResponse = readResponse(connection);
		return readResponse;
	}

	public void setWfsServiceUrl(String wfsServiceUrl) {
		this.wfsServiceUrl = wfsServiceUrl;
	}

	public String getWfsServiceUrl() {
		return wfsServiceUrl;
	}

	public void setWmsServiceUrl(String wmsServiceUrl) {
		this.wmsServiceUrl = wmsServiceUrl;
	}

	public String getWmsServiceUrl() {
		return wmsServiceUrl;
	}
	
	private String getServiceUrl(String serviceName) {
		if(serviceName.equalsIgnoreCase(WFS)) {
			return wfsServiceUrl;
		} else if(serviceName.equalsIgnoreCase(WMS)) {
			return wmsServiceUrl;
		} else {
			return null;
		}
	}
	
	public byte[] getMap(String version, String layers, String styles,
			String srs, String bbox, String width, String height,
			String format) throws Exception {

		initParams();
	
		requiredParams.put(VERSION, version);
		requiredParams.put(REQUEST, GET_MAP);
		requiredParams.put(LAYERS, layers);
		requiredParams.put(STYLES, styles);
		requiredParams.put(SRS, srs);
		requiredParams.put(BBOX, bbox);
		requiredParams.put(WIDTH, width);
		requiredParams.put(HEIGHT, height);
		requiredParams.put(FORMAT, format);

		StringBuffer urlBuffer = new StringBuffer(getWmsServiceUrl());
		setRESTParams(urlBuffer, requiredParams, false);
		setRESTParams(urlBuffer, optionalParams, true);
		
		//System.out.println("\n%%% Wysylam zapytanie getMap %%%\n");
		//System.out.println(urlBuffer);

		URL url = new URL(urlBuffer.toString());
		BufferedImage image = ImageIO.read(url);
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), 1);
		Graphics gc = bufferedImage.createGraphics();
		gc.drawImage(image, 0, 0, null);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpeg", bos);
		return bos.toByteArray();
	}
	
	public byte[] getMapWithOptionalParams(String version, String layers, String styles,
			String srs, String bbox, String width, String height,
			String format, String transparent, String bgColor,
			String exceptions, String time, String elevation) throws Exception {

		initParams();
	
		requiredParams.put(VERSION, version);
		requiredParams.put(REQUEST, GET_MAP);
		requiredParams.put(LAYERS, layers);
		requiredParams.put(STYLES, styles);
		requiredParams.put(SRS, srs);
		requiredParams.put(BBOX, bbox);
		requiredParams.put(WIDTH, width);
		requiredParams.put(HEIGHT, height);
		requiredParams.put(FORMAT, format);

		optionalParams.put(TRANSPARENT, transparent);
		optionalParams.put(BGCOLOR, bgColor);
		optionalParams.put(EXCEPTIONS, exceptions);
		optionalParams.put(TIME, time);
		optionalParams.put(ELEVATION, elevation);

		StringBuffer urlBuffer = new StringBuffer(getWmsServiceUrl());
		setRESTParams(urlBuffer, requiredParams, false);
		setRESTParams(urlBuffer, optionalParams, true);
		
		//System.out.println("%%% Sending getMap request %%%");
		//System.out.println(urlBuffer);

		URL url = new URL(urlBuffer.toString());
		BufferedImage image = ImageIO.read(url);
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), 1);
		Graphics gc = bufferedImage.createGraphics();
		gc.drawImage(image, 0, 0, null);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpeg", bos);
		return bos.toByteArray();
	}



	
	
}
