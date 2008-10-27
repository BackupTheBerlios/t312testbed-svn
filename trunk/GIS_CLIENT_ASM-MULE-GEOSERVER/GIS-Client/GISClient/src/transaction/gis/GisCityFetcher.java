package transaction.gis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import transaction.City;
import transaction.CityBean;
import umo.GeoPort;

public class GisCityFetcher {

	private static final String FID = "fid";
	private static final String SERVICE = "wfs";
	private static final String TYPE_NAME = "topp:tasmania_cities";
	private static final String GML_COORDINATES = "gml:coordinates";

	public static List<City> fetchCities(GeoPort port) throws Exception {
		String[] typeNameList = new String[] { TYPE_NAME };
		String description = port.getFeature(SERVICE, typeNameList);
		return parseList(description);
	}

	private static List<City> parseList(String str) throws SAXException,
			IOException, ParserConfigurationException {
		Document dom = DOMHelper.getDocument(str);
		return getCities(dom);
	}

	private static List<City> getCities(Document dom) {
		Element docEle = dom.getDocumentElement();
		List<City> cities = new ArrayList<City>();

		NodeList nl = docEle.getElementsByTagName(TYPE_NAME);
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				Element el = (Element) nl.item(i);
				City c = getCity(el);
				cities.add(c);
			}
		}
		return cities;
	}

	private static City getCity(Element el) {
		Map<String, String> map = new HashMap<String, String>();
		String fid = el.getAttribute(FID);
		map.put(CityBean.CITY_ID, fid);
		addValue(map, el, CityBean.ADMIN_NAME);
		addValue(map, el, CityBean.CITY_NAME);
		addValue(map, el, CityBean.CNTRY_NAME);
		addValue(map, el, CityBean.POP_CLASS);
		addValue(map, el, CityBean.STATUS);
		addCoordinates(map, el);
		return CityBean.createCity(map);
	}

	private static void addCoordinates(Map<String, String> map, Element el) {
		NodeList nl = el.getElementsByTagName(GML_COORDINATES);
		if (nl != null && nl.getLength() > 0) {
			Element elem = (Element) nl.item(0);
			Node node = elem.getFirstChild();
			if (node != null) {
				String str = node.getNodeValue();
				str = str.trim();
				String cs = elem.getAttribute("cs");
				String[] values = str.split(cs);
				map.put(CityBean.LONGITUDE, values[0]);
				map.put(CityBean.LATITUDE, values[1]);
			}
		}
	}

	private static void addValue(Map<String, String> map, Element el, String tag) {
		String topptag = "topp:" + tag;
		String value = DOMHelper.getTextValue(el, topptag);
		map.put(tag, value);
	}

}
