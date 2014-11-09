package scheduler.da;

import scheduler.da.dao.*;
import scheduler.model.*;
import scheduler.model.collections.*;
import scheduler.schedule.Schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Data {
    private static HashMap<String, Data> datas = new HashMap<>();

    private Connection db;

    private Data(String fileName) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            db = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Data getData(String fileIn) {
        if (!datas.containsKey(fileIn)) {
            datas.put(fileIn, new Data(fileIn));
        }
        return datas.get(fileIn);
    }

    public Schedule getSchedule(int year, int month) throws SQLException {
        Squads squads = SquadDAO.buildSquads(db);
        Days days = getDays(year, month);
        Events events = getEvents(days, squads);
        Crews crews = CrewDAO.getCrews(db, squads, days);
        Configuration configuration = ConfigurationDAO.getConfiguration(db, 1);
        return new Schedule(events, crews, configuration);
    }

    private Days getDays(int y, int m) throws SQLException {
        Year year = DateDAO.getYear(db, y);
        Month month = DateDAO.getMonth(db, year, m);
        return DateDAO.getDays(db, month);
    }

    private Events getEvents(Days days, Squads squads) throws SQLException {
        EventType alert = EventDAO.getEventType(db, EventDAO.EVENT_ALERT);
        EventType backup = EventDAO.getEventType(db, EventDAO.EVENT_BACKUP);
        Events events = new Events();
        Sites sites = SiteDAO.getSites(db, squads);
        for (Day day : days) {
            if (day.getOrdinal() != 21) {
                for (Site site : sites) {
                    Event event = EventDAO.getEvent(db, site, day, alert);
                    events.put(event);
                }
            }
        }
        // TODO: remove this hack to get a single site to populate backups
        for (int i = 0; i > 0; i++) {
            Event event = EventDAO.getEvent(db, sites.first(), days.getRandom(), backup);
            events.put(event);
        }
        // TODO: add backup events for needed days
        return events;
    }
}
