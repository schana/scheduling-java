package scheduler.model;

public class Position extends BaseItem<Position> {
    public static final String DEPUTY    = "Deputy";
    public static final String COMMANDER = "Commander";
    protected String           description;

    public Position(long id, String description) {
        super(id);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDeputy() {
        return description.equals(DEPUTY);
    }

}
