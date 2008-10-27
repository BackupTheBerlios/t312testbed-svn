package transaction.actions;

import transaction.City;

public class ActionDelete extends AbstractIAction<City> implements IDeleteAction {

	public ActionDelete(int id, City object) {
		super(id, object);
	}

}
