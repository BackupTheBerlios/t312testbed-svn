package testing;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class Tests {
	public void addCityWithAuth(String name, float x, float y, String user,
			String pass) throws Exception {
		// String name = "auth czarna dziura";
		String coordinates = "145.91,-42.60";
		// String username = "wfst"; TODO
		// String password = "wfst"; TODO

		coordinates = "" + x + "," + y;

		String urlString = "http://localhost:8080/geoserver-acegi/wfs?service=WFS";

		String param = "<?xml version=\'1.0\' encoding=\'UTF-8\'?>"
				+ "<wfs:Transaction xmlns:wfs='http://www.opengis.net/wfs'>"
				+ "<wfs:Insert><topp:tasmania_cities xmlns:topp='http://www.openplans.org/topp'>"
				+ "	<topp:the_geom>"
				+ "		<gml:MultiPoint xmlns:gml='http://www.opengis.net/gml' srsName='epsg:4326'>"
				+ "		<gml:pointMember>" + "          <gml:Point>"
				+ "            <gml:coordinates decimal='.' cs=',' ts=' '>"
				+ coordinates + "</gml:coordinates>" + "          </gml:Point>"
				+ "        </gml:pointMember>" + "      </gml:MultiPoint>"
				+ "    </topp:the_geom>" + "    <topp:CITY_NAME>" + name
				+ "</topp:CITY_NAME>"
				+ "    <topp:ADMIN_NAME>Tasmania</topp:ADMIN_NAME>"
				+ "    <topp:CNTRY_NAME>Australia</topp:CNTRY_NAME>"
				+ "    <topp:STATUS>other</topp:STATUS>"
				+ "    <topp:POP_CLASS/>"
				+ "  </topp:tasmania_cities></wfs:Insert></wfs:Transaction>";

		HttpURLConnection connection = (HttpURLConnection) prepareUrlConnection(
				urlString, "POST");

		// addBasicAuthorization(connection, username, password);
		addBasicAuthorization(connection, user, pass);

		connection.connect();
		printData(connection, param);

		System.out.println("response code: " + connection.getResponseCode());
		readResponse(connection);
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
		connection.setRequestProperty("Content-Type", "text/xml");
		return connection;
	}

	protected void addBasicAuthorization(HttpURLConnection connection,
			String user, String pass) {

		// <username>:<password>
		String userPass = user + ":" + pass;

		// Base64 encode the authentication string
		String encoding = new sun.misc.BASE64Encoder().encode(userPass
				.getBytes());

		// set basic authentication parameters
		connection.setRequestProperty("Authorization", "Basic " + encoding);
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
		System.out.println(connection.getInputStream().available());
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		int ch = 0;
		while ((ch = connection.getInputStream().read()) != -1) {
			sb.append((char) ch);
		}
		System.out.println(sb);
		return sb.toString();
	}

}
