package scheduler.model;

import org.joda.time.chrono.ISOChronology;
import scheduler.model.collections.Days;

import java.text.DecimalFormat;

public class Day extends BaseItem<Day> {
    private static Days days = new Days();
    private static ISOChronology chrono = ISOChronology.getInstance();
    protected int ordinal;
    protected Month month;
    protected Weekday weekday;

    private Day(long id, int ordinal, Month month, Weekday weekday) {
        super(id);
        this.ordinal = ordinal;
        this.month = month;
        this.weekday = weekday;
    }

    public static Day create(long id, int ordinal, Month month, Weekday weekday) {
        if (!days.containsKey(id)) {
            days.put(new Day(id, ordinal, month, weekday));
        }
        return days.get(id);
    }
/*
    public static int getLength(Day start, Day end) {
        GregorianCalendar s = new GregorianCalendar(start.getMonth().getYear().getOrdinal(), start.getMonth().getOrdinal(),
                start.getOrdinal());
        GregorianCalendar e = new GregorianCalendar(end.getMonth().getYear().getOrdinal(), end.getMonth().getOrdinal(), end.getOrdinal());
        return Math.abs((int) (e.getTimeInMillis() - s.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }*/

    /*public static boolean contains(Day day, Day start, Day end) {
        return start.compareTo(day) >= 0 && day.compareTo(end) >= 0;
    }*/

/*
    public static int getLength(Day start, Day end) {
        DateTime s = new DateTime(start.getMonth().getYear().getOrdinal(), start.getMonth().getOrdinal(),
                start.getOrdinal(), 0, 0, chrono);
        DateTime e = new DateTime(end.getMonth().getYear().getOrdinal(), end.getMonth().getOrdinal(), end.getOrdinal(),
                0, 0, chrono);
        return Math.abs(org.joda.time.Days.daysBetween(s, e).getDays()) + 1;
    }

    public static boolean contains(Day day, Day start, Day end) {
        DateTime s = new DateTime(start.getMonth().getYear().getOrdinal(), start.getMonth().getOrdinal(),
                start.getOrdinal(), 0, 0, chrono);
        DateTime e = new DateTime(end.getMonth().getYear().getOrdinal(), end.getMonth().getOrdinal(), end.getOrdinal(),
                0, 0, chrono);
        DateTime d = new DateTime(day.getMonth().getYear().getOrdinal(), day.getMonth().getOrdinal(), day.getOrdinal(),
                0, 0, chrono);
        Interval interval = new Interval(s, e);
        return interval.contains(d);
    }*/

    public static int getLength(Day start, Day end) {
        int s = start.getOrdinal();
        int e = end.getOrdinal();
        if (s == e) {
            return 1;
        } else {
            return Math.abs(e - s);
        }
    }

    public static boolean contains(Day day, Day start, Day end) {
        int d = day.getOrdinal();
        int s = start.getOrdinal();
        int e = end.getOrdinal();
        if (s - 1 <= d && d <= e) {
            return true;
        }
        return false;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public Month getMonth() {
        return month;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    @Override
    public int compareTo(Day other) {
        if (other.getMonth().equals(getMonth())) {
            return Integer.compare(getOrdinal(), other.getOrdinal());
        } else {
            return getMonth().compareTo(other.getMonth());
        }
    }

    @Override
    public String toString() {
        DecimalFormat f = new DecimalFormat("00");
        return getWeekday().toString().substring(0, 3) + " " + month + f.format(ordinal);
    }

}
