package scheduler.model;

import scheduler.model.collections.Squads;

public class Squad extends BaseItem<Squad> {
    private static Squads squads = new Squads();
    protected String      name;

    public static Squad create(long id, String name) {
        if (!squads.containsKey(id)) {
            squads.put(new Squad(id, name));
        }
        return squads.get(id);
    }

    private Squad(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
