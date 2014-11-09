package scheduler.model;

import scheduler.model.collections.Sites;

public class Site extends BaseItem<Site> {
    private static Sites sites = new Sites();
    protected String     name;
    protected SiteType   siteType;
    protected Squad      squad;

    public static Site create(long id, String name, SiteType siteType, Squad squad) {
        if (!sites.containsKey(id)) {
            sites.put(new Site(id, name, siteType, squad));
        }
        return sites.get(id);
    }

    private Site(long id, String name, SiteType siteType, Squad squad) {
        super(id);
        this.name = name;
        this.siteType = siteType;
        this.squad = squad;
    }

    public String getName() {
        return name;
    }

    public SiteType getSiteType() {
        return siteType;
    }

    public Squad getSquad() {
        return squad;
    }

    @Override
    public String toString() {
        return getName();
    }
}
