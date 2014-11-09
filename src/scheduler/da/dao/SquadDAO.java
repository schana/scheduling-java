package scheduler.da.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import scheduler.model.Squad;
import scheduler.model.collections.Squads;

public class SquadDAO {

    public static Squads buildSquads(Connection db) throws SQLException {
        Squads squads = new Squads();
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT ID,Name from Squads");

        while (rs.next()) {
            long id = rs.getLong("ID");
            String name = rs.getString("Name");
            squads.put(Squad.create(id, name));
        }
        return squads;
    }
}
