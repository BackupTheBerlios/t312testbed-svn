package transaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CityBean {
	public static final String CITY_NAME = "CITY_NAME";
	public static final String CITY_ID = "CITY_ID";
	public static final String LATITUDE = "LATITUDE";
	public static final String LONGITUDE = "LONGITUDE";
	public static final String ADMIN_NAME = "ADMIN_NAME";
	public static final String CNTRY_NAME = "CNTRY_NAME";
	public static final String STATUS = "STATUS";
	public static final String POP_CLASS = "POP_CLASS";
	private static final String[] values = { CITY_ID, CITY_NAME, LATITUDE,
			LONGITUDE, ADMIN_NAME, CNTRY_NAME, STATUS, POP_CLASS };

	public static String[] getValues() {
		return Arrays.copyOf(values, values.length);
	}

	public static String[] getValuesNoId() {
		// TODO i know it's bad, but it's easy ;)
		return Arrays.copyOfRange(values, 1, values.length);
	}

	public static String getCityId(Map<String, String> cityMap) {
		return cityMap.get(CITY_ID);
	}

	public static boolean isFakeId(String str) {
		return str.startsWith(Transaction.FAKE_ID);
	}

	public static Map<String, String> retrieveCityMap(HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();
		copyParam(CITY_ID, map, request);
		copyParam(CITY_NAME, map, request);
		copyParam(LATITUDE, map, request);
		copyParam(LONGITUDE, map, request);
		copyParam(ADMIN_NAME, map, request);
		copyParam(CNTRY_NAME, map, request);
		copyParam(STATUS, map, request);
		copyParam(POP_CLASS, map, request);
		return map;
	}

	public static void copyParam(String key, Map<String, String> map,
			HttpServletRequest request) {
		String value = request.getParameter(key);
		if ((value != null) && (value.length() > 0)) {
			map.put(key, value);
		}
	}

	public static City createCity(HttpServletRequest request) {
		Map<String, String> map = retrieveCityMap(request);
		return createCity(map);
	}

	public static City createCity(Map<String, String> map) {
		City c = new City();
		c.setCityId(map.get(CITY_ID));
		c.setName(map.get(CITY_NAME));
		c.setLatitude(map.get(LATITUDE));
		c.setLongitude(map.get(LONGITUDE));
		c.setAdminName(map.get(ADMIN_NAME));
		c.setCountryName(map.get(CNTRY_NAME));
		c.setStatus(map.get(STATUS));
		c.setPopClass(map.get(POP_CLASS));
		return c;
	}

	public static City createCity(CityBean cityBean) {
		// TODO
		return null;
	}

	public static Map<String, String> asMap(City city) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(CITY_ID, city.getCityId());
		map.put(CITY_NAME, city.getName());
		map.put(LATITUDE, city.getLatitude());
		map.put(LONGITUDE, city.getLongitude());
		map.put(ADMIN_NAME, city.getAdminName());
		map.put(CNTRY_NAME, city.getCountryName());
		map.put(STATUS, city.getStatus());
		map.put(POP_CLASS, city.getPopClass());
		return map;

	}
}
