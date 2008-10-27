package transaction.actions;

import transaction.City;

public class ActionInsert extends AbstractIAction<City> implements IAddAction{

	public ActionInsert(int id, City object) {
		super(id, object);
	}

}
