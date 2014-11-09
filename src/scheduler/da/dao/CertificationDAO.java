package scheduler.da.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import scheduler.model.Certification;
import scheduler.model.collections.Certifications;

public class CertificationDAO {

    public static Certifications getCertifications(Connection db) throws SQLException {
        Certifications certs = new Certifications();
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT ID,Description FROM Certifications");
        while (rs.next()) {
            long id = rs.getLong("ID");
            String description = rs.getString("Description");
            Certification cert = Certification.create(id, description);
            certs.put(cert);
        }
        return certs;
    }

    public static Certifications getCertificationsForSiteType(Connection db, long siteTypeId) throws SQLException {
        Certifications certs = new Certifications();
        PreparedStatement st = db
                .prepareStatement("SELECT CertificationID,Description FROM Certifications_SiteTypes JOIN Certifications ON CertificationID=Certifications.ID WHERE SiteTypeID=?");
        st.setLong(1, siteTypeId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            long id = rs.getLong("CertificationID");
            String description = rs.getString("Description");
            Certification cert = Certification.create(id, description);
            certs.put(cert);
        }
        return certs;
    }

    public static Certifications getCertificationsForMember(Connection db, long memberId) throws SQLException {
        Certifications certs = new Certifications();
        PreparedStatement st = db
                .prepareStatement("SELECT CertificationID,Description FROM Certifications_Members JOIN Certifications ON CertificationID=Certifications.ID WHERE MemberID=?");
        st.setLong(1, memberId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            long id = rs.getLong("CertificationID");
            String description = rs.getString("Description");
            Certification cert = Certification.create(id, description);
            certs.put(cert);
        }
        return certs;
    }
}
