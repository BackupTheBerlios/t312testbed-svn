package transaction.actions;

public abstract class AbstractIUpdateAction<K> extends AbstractIAction<K> implements IUpdateAction<K> {

	private K newValue;

	public K getNewValue() {
		return newValue;
	}

	public AbstractIUpdateAction(int id, K object, K newValue) {
		super(id,object);
		this.newValue = newValue;
	}
}
