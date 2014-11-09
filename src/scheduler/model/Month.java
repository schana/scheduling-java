package scheduler.model;

import java.text.DecimalFormat;

public class Month extends BaseItem<Month> {
    protected int ordinal;
    protected Year year;

    public Month(long id, int ordinal, Year year) {
        super(id);
        this.ordinal = ordinal;
        this.year = year;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public Year getYear() {
        return year;
    }

    @Override
    public int compareTo(Month other) {
        if (other.getYear().equals(getYear())) {
            return Integer.compare(getOrdinal(), other.getOrdinal());
        } else {
            return getYear().compareTo(other.getYear());
        }
    }

    @Override
    public String toString() {
        DecimalFormat f = new DecimalFormat("00");
        return year + f.format(ordinal);
    }

}
