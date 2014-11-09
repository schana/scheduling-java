package scheduler.da.dao;

import scheduler.model.Crew;
import scheduler.model.collections.Crews;
import scheduler.model.collections.Days;
import scheduler.model.collections.Members;
import scheduler.model.collections.Squads;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrewDAO {

    public static Crews getCrews(Connection db, Squads squads, Days days) throws SQLException {
        Crews crews = new Crews();
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT ID,SquadID from Crews");
        while (rs.next()) {
            long id = rs.getLong("ID");
            long squadId = rs.getLong("SquadID");
            Crew crew = Crew.create(id, squads.get(squadId), new Members());
            MemberDAO.populateMembers(db, crew, days);
            crews.put(crew);
        }
        return crews;
    }
}
