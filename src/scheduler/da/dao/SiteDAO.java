package scheduler.da.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import scheduler.model.Site;
import scheduler.model.SiteType;
import scheduler.model.collections.Certifications;
import scheduler.model.collections.SiteTypes;
import scheduler.model.collections.Sites;
import scheduler.model.collections.Squads;

public class SiteDAO {

    public static Sites getSites(Connection db, Squads squads) throws SQLException {
        SiteTypes siteTypes = getSiteTypes(db);
        Sites sites = new Sites();
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT ID,Name,SiteTypeID,SquadID from Sites");
        while (rs.next()) {
            long id = rs.getLong("ID");
            String name = rs.getString("Name");
            long siteTypeId = rs.getLong("SiteTypeID");
            long squadId = rs.getLong("SquadID");
            sites.put(Site.create(id, name, siteTypes.get(siteTypeId), squads.get(squadId)));
        }
        return sites;
    }

    public static SiteTypes getSiteTypes(Connection db) throws SQLException {
        SiteTypes siteTypes = new SiteTypes();
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT ID,Description FROM SiteTypes");
        while (rs.next()) {
            long id = rs.getLong("ID");
            String description = rs.getString("Description");
            Certifications certifications = CertificationDAO.getCertificationsForSiteType(db, id);
            siteTypes.put(SiteType.create(id, description, certifications));
        }
        return siteTypes;
    }

}
