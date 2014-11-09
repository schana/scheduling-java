package scheduler.model;

public class Weekday extends BaseItem<Weekday> {
    protected String name;
    protected int    energy;

    public Weekday(long id, String name, int energy) {
        super(id);
        this.name = name;
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return getName();
    }

}
