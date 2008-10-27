package transaction.actions;

public abstract class AbstractIAction<K> implements IAction<K> {

	private K object;
	private int id;

	public int getId() {
		return id;
	}

	public K getObject() {
		return object;
	}

	public AbstractIAction(int id, K object) {
		this.id = id;
		this.object = object;
	}
}
