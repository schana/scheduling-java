package scheduler.model;

import java.text.DecimalFormat;

public class Year extends BaseItem<Year> {
    protected int ordinal;

    public Year(long id, int ordinal) {
        super(id);
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }

    @Override
    public int compareTo(Year other) {
        return Integer.compare(getOrdinal(), other.getOrdinal());
    }

    @Override
    public String toString() {
        DecimalFormat f = new DecimalFormat("00");
        return f.format(ordinal);
    }

}
