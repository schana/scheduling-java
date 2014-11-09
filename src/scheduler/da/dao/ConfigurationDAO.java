package scheduler.da.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import scheduler.model.Configuration;

public class ConfigurationDAO {

    public static Configuration getConfiguration(Connection db, int configId) throws SQLException {
        Configuration config = null;
        PreparedStatement st = db
                .prepareStatement("SELECT ID,AlertLengthDays,CrewCountPerAlert,RestrictSitesToSquad FROM Configuration WHERE ID=?");
        st.setLong(1, configId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            long id = rs.getLong("ID");
            int alertLengthDays = rs.getInt("AlertLengthDays");
            int crewCountPerAlert = rs.getInt("CrewCountPerAlert");
            boolean restrictSitesToSquad = rs.getInt("RestrictSitesToSquad") == 1;
            config = new Configuration(id, alertLengthDays, crewCountPerAlert, restrictSitesToSquad);
        }
        return config;
    }
}