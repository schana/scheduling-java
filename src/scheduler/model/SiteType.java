package scheduler.model;

import scheduler.model.collections.Certifications;
import scheduler.model.collections.SiteTypes;

public class SiteType extends BaseItem<SiteType> {
    private static SiteTypes siteTypes = new SiteTypes();
    protected String         description;
    protected Certifications certifications;

    public static SiteType create(long id, String description, Certifications certifications) {
        if (!siteTypes.containsKey(id)) {
            siteTypes.put(new SiteType(id, description, certifications));
        }
        return siteTypes.get(id);
    }

    private SiteType(long id, String description, Certifications certifications) {
        super(id);
        this.description = description;
        this.certifications = certifications;
    }

    public String getDescription() {
        return description;
    }

    public Certifications getCertifications() {
        return certifications;
    }

}
