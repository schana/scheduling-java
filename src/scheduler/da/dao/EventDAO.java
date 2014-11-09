package scheduler.da.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import scheduler.model.Day;
import scheduler.model.Event;
import scheduler.model.EventType;
import scheduler.model.Site;

public class EventDAO {
    public static final String EVENT_ALERT  = "Alert";
    public static final String EVENT_BACKUP = "Backup";

    public static Event getEvent(Connection db, Site site, Day day, EventType eventType) throws SQLException {
        long id;
        PreparedStatement st = db
                .prepareStatement("SELECT ID FROM Events WHERE SiteID=? AND DayID=? AND EventTypeID=?");
        st.setLong(1, site.getId());
        st.setLong(2, day.getId());
        st.setLong(3, eventType.getId());
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            id = rs.getLong("ID");
        } else {
            st = db.prepareStatement("INSERT INTO Events (SiteID, DayID, EventTypeID) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setLong(1, site.getId());
            st.setLong(2, day.getId());
            st.setLong(3, eventType.getId());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            } else {
                return null;
            }
        }
        return Event.create(id, eventType, site, day);
    }

    public static EventType getEventType(Connection db, String description) throws SQLException {
        EventType eventType = null;
        PreparedStatement st = db.prepareStatement("SELECT ID FROM EventTypes WHERE Description=?");
        st.setString(1, description);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            long id = rs.getLong("ID");
            eventType = new EventType(id, description);
        }
        return eventType;
    }
}
