package scheduler.model;

public class EventType extends BaseItem<EventType> {
    protected String description;

    public EventType(long id, String description) {
        super(id);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getDescription();
    }

}
