package umo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;
import java.util.Map;

public interface IGisService {

	static final String GET_CAPABILITIES = "GetCapabilities";
	static final String GET_MAP = "GetMap";
	static final String DESCRIBE_FEATURE_TYPE = "DescribeFeatureType";
	static final String GET_FEATURE = "GetFeature";
	
	static final String WFS = "WFS";
	static final String WMS = "WMS";
	static final String OUTPUT_FORMAT = "OutputFormat";
	static final String TYPENAME = "Typename";
	static final String BBOX = "BBox";
	static final String PROPERTY_NAME = "PropertyName";
	static final String MAX_FEATURES = "MaxFeatures";
	static final String FILTER = "Filter";
	static final String FEATURE_ID = "FeatureId";
	static final String RESULT_TYPE = "resultType";
	static final String WFS_SCHEMA_LOCATION = " xsi:schemaLocation=\"http://www.opengis.net/wfs ../wfs/1.1.0/WFS.xsd\">";
	static final String XMLSCHEMA_INSTANCE_NS = " xmlns:xsi=\"http:// www.w3.org/2001/XMLSchema-instance\"";
	static final String LAYERS = "LAYERS";
	static final String STYLES = "STYLES";
	static final String SRS = "SRS";
	static final String WIDTH = "WIDTH";
    static final String HEIGHT = "HEIGHT";
	static final String VERSION = "VERSION"; // "1.3.0"; M version of
	static final String REQUEST = "REQUEST"; // "GetMap"; // M
	static final String SERVICE = "SERVICE";
	static final String FORMAT = "FORMAT";
	static final String UPDATESEQUENCE = "UPDATESEQUENCE";
	
	// optional getMap parameters
	 static final String TRANSPARENT = "TRANSPARENT";
	 static final String BGCOLOR = "BGCOLOR";
	 static final String EXCEPTIONS = "EXCEPTIONS";
	 static final String TIME = "TIME";
	 static final String ELEVATION = "ELEVATION";
	
	 
	
	String getCapabilities(String service);

	String getCapabilitiesWithOptionalParams(String service, String version,
			String format, String updateSequence);

	String sendRequest(String service, String requestBody)
			throws MalformedURLException, ProtocolException, IOException;

	String describeFeatureType(String service) throws IOException;

	String describeFeatureTypeWithOptionalParams(String service,
			String outputFormat, List<String> typenameList, String version,
			Map<String, String> namespaces) throws MalformedURLException,
			ProtocolException, IOException;

	String getFeature(String service, String[] typenameList)
			throws MalformedURLException, ProtocolException, IOException;

	String getFeatureWithOptionalParams(String service,
			String[] typenameList, String bbox, String[] featureIdList,
			String filter, String maxFeatures, String outputFormat,
			String[] propertyNameList, String version, String resultType)
			throws MalformedURLException, ProtocolException, IOException;

	String transaction(String requestBody) throws MalformedURLException,
			ProtocolException, IOException;
	
	abstract byte[] getMap(String version, String layers, String styles,
			String srs, String bbox, String width, String height,
			String format) throws Exception;
	
	abstract byte[] getMapWithOptionalParams(String version, String layers, String styles,
			String srs, String bbox, String width, String height,
			String format, String transparent, String bgColor,
			String exceptions, String time, String elevation) throws Exception;
}
