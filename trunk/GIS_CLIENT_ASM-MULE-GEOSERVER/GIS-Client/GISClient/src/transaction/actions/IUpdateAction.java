package transaction.actions;

public interface IUpdateAction<K> extends IAction<K> {

	K getNewValue();
}
