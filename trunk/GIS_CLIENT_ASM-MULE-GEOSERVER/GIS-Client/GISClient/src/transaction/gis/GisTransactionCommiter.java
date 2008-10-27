package transaction.gis;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import transaction.City;
import transaction.CityBean;
import transaction.actions.IAction;
import transaction.actions.IAddAction;
import transaction.actions.IDeleteAction;
import transaction.actions.IUpdateAction;
import umo.GeoPort;
import utils.Utils;

public abstract class GisTransactionCommiter {
	public static final String SUCCESS_TAG = "wfs:SUCCESS";

	public static StringBuffer createRequest(StringBuffer sb,
			List<IAction<City>> actions) {
		if (sb == null) {
			sb = new StringBuffer();
		}

		Utils.addHeader(sb);
		Utils.addTransactionStart(sb);

		for (IAction<City> action : actions) {
			toRequest(action, sb);
		}
		Utils.addTransactionEnd(sb);
		return sb;
	}

	public static String invoke(String str, GeoPort port)
			throws RemoteException {
		String ans = port.transaction(str);
		System.out.println("got ans:");
		System.out.println(ans);
		return ans;
	}

	private static StringBuffer toRequest(IAction<City> action, StringBuffer sb) {
		Map<String, String> cityMap = CityBean.asMap(action.getObject());
		if (action instanceof IAddAction) {
			Utils.addInsertCityCommand(cityMap, sb);
		} else if (action instanceof IDeleteAction) {
			Utils.addDeleteCityCommand(cityMap, sb);
		} else if (action instanceof IUpdateAction) {
			IUpdateAction<City> uAction = (IUpdateAction<City>) action;
			Map<String, String> newCityMap = CityBean.asMap(uAction
					.getNewValue());
			Utils.addUpdateCityCommand(cityMap, newCityMap, sb);
		}
		return sb;
	}

	public static boolean isSuccess(String ans) {
		try {
			Document doc = DOMHelper.getDocument(ans);
			NodeList nodes=doc.getElementsByTagName(SUCCESS_TAG);
			return nodes.getLength()==1;
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return false;
	}

}
