package scheduler.da.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import scheduler.model.Day;
import scheduler.model.Month;
import scheduler.model.Weekday;
import scheduler.model.Year;
import scheduler.model.collections.Days;

public class DateDAO {
    public static final String SUNDAY    = DateFormatSymbols.getInstance().getWeekdays()[Calendar.SUNDAY];
    public static final String MONDAY    = DateFormatSymbols.getInstance().getWeekdays()[Calendar.MONDAY];
    public static final String TUESDAY   = DateFormatSymbols.getInstance().getWeekdays()[Calendar.TUESDAY];
    public static final String WEDNESDAY = DateFormatSymbols.getInstance().getWeekdays()[Calendar.WEDNESDAY];
    public static final String THURSDAY  = DateFormatSymbols.getInstance().getWeekdays()[Calendar.THURSDAY];
    public static final String FRIDAY    = DateFormatSymbols.getInstance().getWeekdays()[Calendar.FRIDAY];
    public static final String SATURDAY  = DateFormatSymbols.getInstance().getWeekdays()[Calendar.SATURDAY];

    public static Year getYear(Connection db, int ordinal) throws SQLException {
        long id;
        PreparedStatement st = db.prepareStatement("SELECT ID FROM Years WHERE Ordinal=?");
        st.setInt(1, ordinal);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            id = rs.getLong("ID");
        } else {
            st = db.prepareStatement("INSERT INTO Years (Ordinal) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, ordinal);
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            } else {
                return null;
            }
        }
        return new Year(id, ordinal);
    }

    public static Month getMonth(Connection db, Year year, int ordinal) throws SQLException {
        long id;
        PreparedStatement st = db.prepareStatement("SELECT ID FROM Months WHERE YearID=? AND Ordinal=?");
        st.setLong(1, year.getId());
        st.setInt(2, ordinal);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            id = rs.getLong("ID");
        } else {
            st = db.prepareStatement("INSERT INTO Months (YearID, Ordinal) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setLong(1, year.getId());
            st.setInt(2, ordinal);
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            } else {
                return null;
            }
        }
        return new Month(id, ordinal, year);
    }

    public static Days getDays(Connection db, Month month) throws SQLException {
        Days days = new Days();
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, month.getYear().getOrdinal());
        c.set(Calendar.MONTH, month.getOrdinal() - 1);
        c.set(Calendar.DATE, 1); // TODO: accommodate months not starting on the first
        // enumerate days in month to build
        while (c.get(Calendar.MONTH) == month.getOrdinal() - 1) {
            Weekday weekday = getWeekday(db, DateFormatSymbols.getInstance().getWeekdays()[c.get(Calendar.DAY_OF_WEEK)]);
            Day day = getDay(db, month, weekday, c.get(Calendar.DATE));
            days.put(day);
            c.add(Calendar.DATE, 1); // TODO: accommodate alert lengths longer than 1 day
        }
        return days;
    }

    public static Day getDay(Connection db, Month month, Weekday weekday, int ordinal) throws SQLException {
        long id;
        PreparedStatement st = db.prepareStatement("SELECT ID FROM Days WHERE MonthID=? AND Ordinal=?");
        st.setLong(1, month.getId());
        st.setInt(2, ordinal);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            id = rs.getLong("ID");
        } else {
            st = db.prepareStatement("INSERT INTO Days (MonthID, Ordinal, WeekdayID) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setLong(1, month.getId());
            st.setInt(2, ordinal);
            st.setLong(3, weekday.getId());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            } else {
                return null;
            }
        }
        return Day.create(id, ordinal, month, weekday);
    }

    public static Weekday getWeekday(Connection db, String name) throws SQLException {
        Weekday weekday = null;
        PreparedStatement st = db.prepareStatement("SELECT ID,Energy FROM Weekdays WHERE Name=?");
        st.setString(1, name);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            long id = rs.getLong("ID");
            int energy = rs.getInt("Energy");
            weekday = new Weekday(id, name, energy);
        }
        return weekday;
    }
}
