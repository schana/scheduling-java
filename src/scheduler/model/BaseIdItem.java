package scheduler.model;

public abstract class BaseIdItem {
    protected long id;

    protected BaseIdItem(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(this.getClass())) {
            return false;
        }
        BaseIdItem b = (BaseIdItem) o;
        return b.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        // of course...
        // there won't be any errors since values won't grow that big
        // but maybe...
        // I'll have to use (int) (getId() ^ (getId() >>> 32))
        return (int) getId();
    }
}
