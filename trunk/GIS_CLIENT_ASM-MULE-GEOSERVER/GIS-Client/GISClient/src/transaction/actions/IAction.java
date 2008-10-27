package transaction.actions;

public interface IAction<K> {
	static final String ACTION_ID="actionId";

	K getObject();

	int getId();
}
