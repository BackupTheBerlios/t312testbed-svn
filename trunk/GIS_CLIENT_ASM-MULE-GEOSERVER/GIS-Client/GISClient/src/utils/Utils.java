package utils;

import java.util.Map;

import transaction.CityBean;

public class Utils {

	public static String GET_CAPABILITIES = "GetCapabilities";
	public static String GET_MAP = "GetMap";
	public static String TRANSACTION = "Transaction";
	public static String TRANSACTION_ACTIONS = "Transaction_Actions";
	public static String INSERT = "Insert";
	public static String UPDATE = "Update";
	public static String DELETE = "Delete";
	public static String ROLLBACK = "Rollback";
	public static String COMMIT = "Commit";

	public static String DESCRIBE_FEATURE_TYPE = "DescribeFeatureType";
	public static String DESCRIBE_FEATURE_TYPE_OPT = "DescribeFeatureTypeWithOptionalParams";
	public static String GET_FEATURE = "GetFeature";

	public static String EDITED_CITY = "editedCity";
	public static String METHOD = "method";
	public static String ENDPOINT = "endpoint";

	public static String COMMIT_STRING = "commitString";

	public static String getCoordinates(Map<String, String> cityMap) {
		String longitude = cityMap.get(CityBean.LONGITUDE);
		String latitude = cityMap.get(CityBean.LATITUDE);
		//TODO mistake return latitude + "," + longitude;
		return longitude + "," + latitude;
	}

	public static StringBuffer addCityData(Map<String, String> cityMap,
			StringBuffer sb) {
		cityBegins(sb);
		geomBegins(sb);
		addCoordinates(cityMap, sb);
		geomEnd(sb);
		addValue(cityMap, CityBean.CITY_NAME, sb);
		addValueIfPresent(cityMap, CityBean.ADMIN_NAME, sb);
		addValueIfPresent(cityMap, CityBean.CNTRY_NAME, sb);
		addValueIfPresent(cityMap, CityBean.STATUS, sb);
		addValueIfPresent(cityMap, CityBean.POP_CLASS, sb);
		cityEnd(sb);
		return sb;
	}

	public static StringBuffer addCoordinates(Map<String, String> cityMap,
			StringBuffer sb) {
		String coordinates = getCoordinates(cityMap);

		sb
				.append("<gml:MultiPoint xmlns:gml='http://www.opengis.net/gml' srsName='epsg:4326'>\n");
		sb.append("<gml:pointMember>\n");
		sb.append("<gml:Point>\n");
		sb.append("<gml:coordinates decimal='.' cs=',' ts=' '>");
		sb.append(coordinates);
		sb.append("</gml:coordinates>\n");
		sb.append("</gml:Point>\n");
		sb.append("</gml:pointMember>\n");
		sb.append("</gml:MultiPoint>\n");
		return sb;
	}

	public static StringBuffer geomBegins(StringBuffer sb) {
		sb.append("<topp:the_geom>\n");
		return sb;
	}

	public static StringBuffer geomEnd(StringBuffer sb) {
		sb.append("</topp:the_geom>\n");
		return sb;
	}

	public static StringBuffer cityBegins(StringBuffer sb) {
		sb.append("<topp:tasmania_cities \n");
		sb.append("  xmlns:topp='http://www.openplans.org/topp'>\n");
		return sb;
	}

	public static StringBuffer cityEnd(StringBuffer sb) {
		sb.append("</topp:tasmania_cities>\n");
		return sb;
	}

	public static StringBuffer addValueIfPresent(Map<String, String> cityMap,
			String name, StringBuffer sb) {
		String value = cityMap.get(name);
		if (value != null) {
			return addValue(name, value, sb);
		}
		return sb;
	}

	public static StringBuffer addValue(Map<String, String> cityMap,
			String name, StringBuffer sb) {
		String value = cityMap.get(name);
		return addValue(name, value, sb);
	}

	public static StringBuffer addValue(String name, String value,
			StringBuffer sb) {
		sb.append("<" + name + ">");
		sb.append(value);
		sb.append("</" + name + ">\n");
		return sb;
	}

	public static StringBuffer addPropertyIfPresent(
			Map<String, String> cityMap, String name, StringBuffer sb) {
		String value = cityMap.get(name);
		if (value != null) {
			return addProperty(name, value, sb);
		}
		return sb;
	}

	public static StringBuffer addProperty(Map<String, String> cityMap,
			String name, StringBuffer sb) {
		String value = cityMap.get(name);
		return addProperty(name, value, sb);
	}

	public static StringBuffer addProperty(String name, String value,
			StringBuffer sb) {
		sb.append("<wfs:Property>\n");
		sb.append("  <wfs:Name>" + name + "</wfs:Name>\n");
		if ((value == null) || (value.length() == 0)) {
			sb.append("  <wfs:Value/>\n");
		} else {
			sb.append("  <wfs:Value>" + (value == null ? "" : value)
					+ "</wfs:Value>\n");
		}
		sb.append("</wfs:Property>\n");
		return sb;
	}

	public static StringBuffer addFilterIfPresent(Map<String, String> cityMap,
			String name, StringBuffer sb) {
		String value = cityMap.get(name);
		if ((value != null) && (value.length() > 0)) {
			return addIsEqualFilter(name, value, sb);
		}
		return sb;
	}

	public static StringBuffer addCoordfinatesUpdate(
			Map<String, String> cityMap, StringBuffer sb) {
		sb.append("<wfs:Property>\n");
		sb.append("<wfs:Name>the_geom</wfs:Name>\n");
		sb.append("  <ogc:Value>");
		addCoordinates(cityMap, sb);
		sb.append("</ogc:Value>\n");
		sb.append("</wfs:Property>\n");
		return sb;
	}

	public static StringBuffer addIsEqualFilter(String name, String value,
			StringBuffer sb) {
		sb.append("<ogc:PropertyIsEqualTo>\n");
		sb.append("  <ogc:PropertyName>topp:" + name + "</ogc:PropertyName>\n");
		sb.append("  <ogc:Literal>" + value + "</ogc:Literal>\n");
		sb.append("</ogc:PropertyIsEqualTo>\n");
		return sb;
	}

	public static StringBuffer addFidFilter(String value, StringBuffer sb) {
		sb.append("<ogc:FeatureId fid=\"" + value + "\"/>\n");
		return sb;
	}

	public static StringBuffer filterBegin(StringBuffer sb) {
		sb.append("<ogc:Filter>\n");
		sb.append(" <ogc:And>\n");
		return sb;
	}

	public static StringBuffer filterEnd(StringBuffer sb) {
		sb.append(" </ogc:And>\n");
		sb.append("</ogc:Filter>\n");
		return sb;
	}

	public static StringBuffer addInsertCityCommand(Map<String, String> city,
			StringBuffer sb) {
		if (sb == null) {
			sb = new StringBuffer();
		}
		addMethodStart(INSERT, sb);
		addCityData(city, sb);
		addMethodEnd(INSERT, sb);
		return sb;
	}

	public static StringBuffer addDeleteCityCommand(Map<String, String> city,
			StringBuffer sb) {
		if (sb == null) {
			sb = new StringBuffer();
		}
		addDeleteStart(sb);
		addCityAsFilter(city, sb);
		addMethodEnd(DELETE, sb);
		return sb;
	}

	public static StringBuffer addUpdateCityCommand(Map<String, String> city,
			Map<String, String> newValue, StringBuffer sb) {
		if (sb == null) {
			sb = new StringBuffer();
		}
		addUpdateStart(sb);
		addCityAsProperties(newValue, sb);// TODO
		addCityAsFilter(city, sb);
		addMethodEnd(UPDATE, sb);
		return sb;
	}

	public static StringBuffer addCityAsProperties(Map<String, String> cityMap,
			StringBuffer sb) {
		addCoordfinatesUpdate(cityMap, sb);
		addProperty(cityMap, CityBean.CITY_NAME, sb);
		addProperty(cityMap, CityBean.ADMIN_NAME, sb);
		addProperty(cityMap, CityBean.CNTRY_NAME, sb);
		addProperty(cityMap, CityBean.STATUS, sb);
		addProperty(cityMap, CityBean.POP_CLASS, sb);
		return sb;
	}

	public static StringBuffer addCityAsFilter(Map<String, String> cityMap,
			StringBuffer sb) {
		filterBegin(sb);
		String id = CityBean.getCityId(cityMap);
		if (CityBean.isFakeId(id)) {
			addFilterIfPresent(cityMap, CityBean.CITY_NAME, sb);
			addFilterIfPresent(cityMap, CityBean.ADMIN_NAME, sb);
			addFilterIfPresent(cityMap, CityBean.CNTRY_NAME, sb);
			addFilterIfPresent(cityMap, CityBean.STATUS, sb);
			addFilterIfPresent(cityMap, CityBean.POP_CLASS, sb);
		} else {
			addFidFilter(id, sb);
		}
		filterEnd(sb);
		return sb;
	}

	public static StringBuffer addHeader(StringBuffer sb) {
		sb.append("<?xml version=\'1.0\' encoding=\'UTF-8\'?>\n");
		return sb;
	}

	public static StringBuffer addTransactionStart(StringBuffer sb) {
		sb.append("<wfs:Transaction xmlns:wfs='http://www.opengis.net/wfs'");
		sb.append(" xmlns:cdf='http://www.opengis.net/cite/data'");
		sb.append(" xmlns:ogc='http://www.opengis.net/ogc'");
		sb.append(" xmlns:topp='http://www.openplans.org/topp'>\n");
		return sb;
	}

	public static StringBuffer addTransactionEnd(StringBuffer sb) {
		sb.append("</wfs:Transaction>\n");
		return sb;
	}

	public static StringBuffer addDeleteStart(StringBuffer sb) {
		sb.append("<wfs:" + DELETE + " typeName='topp:tasmania_cities'>\n");
		return sb;
	}

	public static StringBuffer addUpdateStart(StringBuffer sb) {
		sb.append("<wfs:" + UPDATE + " typeName='topp:tasmania_cities'>\n");
		return sb;
	}

	public static StringBuffer addMethodStart(String method, StringBuffer sb) {
		sb.append("<wfs:" + method + ">\n");
		return sb;
	}

	public static StringBuffer addMethodEnd(String method, StringBuffer sb) {
		sb.append("</wfs:" + method + ">\n");
		return sb;
	}

	public static String addCityWithAuth(String name, String x, String y) {
		// String name = "auth czarna dziura";
		// String coordinates = "145.91,-42.60";
		String coordinates = "" + x + "," + y;
		// String username = "wfst"; TODO
		// String password = "wfst"; TODO

		// String urlString =
		// "http://localhost:8080/geoserver-acegi/wfs?service=WFS";
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\'1.0\' encoding=\'UTF-8\'?>");
		sb.append("<wfs:Transaction xmlns:wfs='http://www.opengis.net/wfs'>");
		sb
				.append("<wfs:Insert><topp:tasmania_cities xmlns:topp='http://www.openplans.org/topp'>");
		sb.append("	<topp:the_geom>");
		sb
				.append("		<gml:MultiPoint xmlns:gml='http://www.opengis.net/gml' srsName='epsg:4326'>");
		sb.append("		<gml:pointMember>");
		sb.append("          <gml:Point>");
		sb.append("            <gml:coordinates decimal='.' cs=',' ts=' '>");
		sb.append(coordinates + "</gml:coordinates>");
		sb.append("          </gml:Point>");
		sb.append("        </gml:pointMember>" + "      </gml:MultiPoint>");
		sb.append("    </topp:the_geom>");
		sb.append("    <topp:CITY_NAME>" + name + "</topp:CITY_NAME>");
		sb.append("    <topp:ADMIN_NAME>Tasmania</topp:ADMIN_NAME>");
		sb.append("    <topp:CNTRY_NAME>Australia</topp:CNTRY_NAME>");
		sb.append("    <topp:STATUS>other</topp:STATUS>");
		sb.append("    <topp:POP_CLASS/>");
		sb.append("  </topp:tasmania_cities></wfs:Insert></wfs:Transaction>");
		return sb.toString();
		// HttpURLConnection connection = (HttpURLConnection)
		// prepareUrlConnection(
		// urlString, "POST");
		//
		// // addBasicAuthorization(connection, username, password);
		// addBasicAuthorization(connection, user, pass);
		//
		// connection.connect();
		// printData(connection, param);
		//
		// System.out.println("response code: " + connection.getResponseCode());
		// readResponse(connection);
	}
}
