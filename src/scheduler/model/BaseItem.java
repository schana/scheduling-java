package scheduler.model;

public abstract class BaseItem<T extends BaseIdItem> extends BaseIdItem implements Comparable<T> {

    protected BaseItem(long id) {
        super(id);
    }

    @Override
    public int compareTo(T o) {
        return Long.compare(this.getId(), o.getId());
    }

}
