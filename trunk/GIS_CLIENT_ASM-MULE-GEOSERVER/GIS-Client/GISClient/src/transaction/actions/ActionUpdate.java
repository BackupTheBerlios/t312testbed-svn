package transaction.actions;

import transaction.City;

public class ActionUpdate extends AbstractIUpdateAction<City> {

	public ActionUpdate(int id, City object, City newValue) {
		super(id, object, newValue);
	}

}
