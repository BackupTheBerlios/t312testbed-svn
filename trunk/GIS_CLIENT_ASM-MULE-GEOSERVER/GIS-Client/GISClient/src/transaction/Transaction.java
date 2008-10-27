package transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import transaction.actions.ActionDelete;
import transaction.actions.ActionInsert;
import transaction.actions.ActionUpdate;
import transaction.actions.IAction;
import transaction.actions.IAddAction;
import transaction.actions.IDeleteAction;
import transaction.actions.IUpdateAction;
import transaction.gis.GisCityFetcher;
import transaction.gis.GisTransactionCommiter;
import umo.GeoPort;
import umo.GeoPortProxy;

public class Transaction {

	private List<City> cities = new ArrayList<City>();

	private List<IAction<City>> history = new ArrayList<IAction<City>>();

	// Rather useless since transaction will be for one user
	public static final String FAKE_ID="fake:";
	private Object changeLock = new Object();
	private GeoPort port;

	private AtomicInteger actionCounter = new AtomicInteger(1);
	private AtomicInteger cityCounter = new AtomicInteger(1);

	public Transaction() throws Exception {
		this.port = new GeoPortProxy();
		fetchData(port);
	}

	private void fetchData(GeoPort port) throws Exception {
		List<City> fetched = GisCityFetcher.fetchCities(port);
		for (City c : fetched) {
			addCity(c);
		}
	}

	public void refetch() throws Exception {
		cities.clear();
		fetchData(port);
	}

	private void addCity(City c) {
		ensureId(c, cityCounter.getAndIncrement());
		cities.add(c);
	}

	private void setCity(int index, City c) {
		if (c.getCityId() == null) {
			ensureId(c, cityCounter.getAndIncrement());
		}
		cities.set(index, c);
	}

	private void ensureId(City c, int counter) {
		if (c.getCityId() == null) {
			c.setCityId(FAKE_ID + counter);
		}
	}

	public List<IAction<City>> getHistory() {
		return history;
	}

	public List<City> getObjects() throws Exception {
		// in order to check if something has changed on server
		// only when history is empty
		if(history.isEmpty()) {
			refetch();
		} 
		return cities;
	}

	public void deleteByID(String cityId) {
		City c = findCityById(cityId);
		if (c != null) {
			delete(c);
		}
	}

	public void delete(City object) {
		synchronized (changeLock) {
			IAction<City> act = createDeleteAction(object);
			addToHistory(act);
			cities.remove(object);
		}
	}

	public void insert(City city) {
		synchronized (changeLock) {
			IAction<City> act = createInsertAction(city);
			addToHistory(act);
			addCity(city);
		}
	}

	public void updateById(String cityId, City newValue) {
		City city = findCityById(cityId);
		if (city != null) {
			update(city, newValue);
		}
	}

	public void update(City object, City newValue) {
		synchronized (changeLock) {
			IAction<City> act = createUpdateAction(object, newValue);
			addToHistory(act);
			replaceAll(object, newValue);
		}
	}

	private void replaceAll(City object, City newValue) {
		synchronized (changeLock) {
			if (object.equals(newValue)) {
				return;
			}
			newValue.setCityId(object.getCityId());
			// TODO refactor, slow solution
			int index = cities.indexOf(object);
			while (index >= 0) {
				setCity(index, newValue);
				index = cities.indexOf(object);
			}
		}
	}

	public String getCommitString() {
		StringBuffer sb = new StringBuffer();
		sb = GisTransactionCommiter.createRequest(sb, getHistory());
		return sb.toString();
	}

	public String commit() throws Exception {
		StringBuffer reqBuff = GisTransactionCommiter.createRequest(
				new StringBuffer(), getHistory());
		return commit(reqBuff.toString());
	}

	public String commit(String req) throws Exception {
		String ans = GisTransactionCommiter.invoke(req, port);
		if (isSuccess(ans)) {
			history.clear();
			refetch();
		}
		return ans;
	}

	private boolean isSuccess(String ans) {
		return GisTransactionCommiter.isSuccess(ans);
	}

	public void rollback() throws Exception {
		synchronized (changeLock) {
			history.clear();
			refetch();
		}
	}

	public City findCityById(String cityId) {
		if (cityId == null) {
			return null;
		}
		synchronized (changeLock) {
			for (City c : cities) {
				String id = c.getCityId();
				if (id.equalsIgnoreCase(cityId)) {
					return c;
				}
			}
		}
		return null;
	}

	private void addToHistory(IAction<City> action) {
		synchronized (changeLock) {
			history.add(action);
		}
	}

	private IAction<City> createInsertAction(City c) {
		return new ActionInsert(actionCounter.getAndIncrement(), c);
	}

	private IUpdateAction<City> createUpdateAction(City c, City newC) {
		return new ActionUpdate(actionCounter.getAndIncrement(), c, newC);
	}

	private IAction<City> createDeleteAction(City c) {
		return new ActionDelete(actionCounter.getAndIncrement(), c);
	}

}
