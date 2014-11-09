package scheduler.da.dao;

import scheduler.model.*;
import scheduler.model.collections.*;

import java.sql.*;

public class MemberDAO {

    public static Jobs getJobs(Connection db) throws SQLException {
        Jobs jobs = new Jobs();
        Positions positions = getPositions(db);
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT ID,Description,PositionID,AlertCount,BackupCount,EventCount FROM Jobs");
        while (rs.next()) {
            long id = rs.getLong("ID");
            String description = rs.getString("Description");
            long positionId = rs.getLong("PositionID");
            int alertCount = rs.getInt("AlertCount");
            int backupCount = rs.getInt("BackupCount");
            int eventCount = rs.getInt("EventCount");
            jobs.put(new Job(id, description, positions.get(positionId), alertCount, backupCount, eventCount));
        }
        return jobs;
    }

    public static Positions getPositions(Connection db) throws SQLException {
        Positions positions = new Positions();
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT ID,Description FROM Positions");
        while (rs.next()) {
            long id = rs.getLong("ID");
            String description = rs.getString("Description");
            positions.put(new Position(id, description));
        }
        return positions;
    }

    public static void populateMembers(Connection db, Crew crew, Days days) throws SQLException {
        Members members = crew.getMembers();
        Jobs jobs = getJobs(db);
        PreparedStatement st = db.prepareStatement("SELECT ID,Name,JobID FROM Members WHERE CrewID=?");
        st.setLong(1, crew.getId());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            long id = rs.getLong("ID");
            String name = rs.getString("Name");
            long jobId = rs.getLong("JobID");
            members.put(Member.create(id, name, jobs.get(jobId), new Events(), crew, getLeaveForMember(db, id, days),
                    CertificationDAO.getCertificationsForMember(db, id)));
        }
    }

    public static Leave getLeaveForMember(Connection db, long memberId, Days days) throws SQLException {
        Leave leave = new Leave();
        PreparedStatement st = db.prepareStatement("SELECT LeaveID,StartDayID,EndDayID FROM Leave_Members JOIN Leave ON LeaveID=Leave.ID WHERE MemberID=?");
        st.setLong(1, memberId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            long leaveId = rs.getLong("LeaveID");
            long startId = rs.getLong("StartDayID");
            long endId = rs.getLong("EndDayID");
            LeaveSection ls = LeaveSection.create(leaveId, days.get(startId), days.get(endId));
            leave.put(ls);
        }
        return leave;
    }
}
