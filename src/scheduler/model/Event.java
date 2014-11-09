package scheduler.model;

import scheduler.model.collections.Events;

public class Event extends BaseItem<Event> {
    private static Events events = new Events();

    protected EventType eventType;
    protected Site site;
    protected Day day;
    protected Member deputy;
    protected Member commander;

    public static Event create(long id, EventType eventType, Site site, Day day, Member deputy, Member commander) {
        if (!events.containsKey(id)) {
            events.put(new Event(id, eventType, site, day, deputy, commander));
        }
        return events.get(id);
    }

    public static Event create(long id, EventType eventType, Site site, Day day) {
        return create(id, eventType, site, day, null, null);
    }

    private Event(long id, EventType eventType, Site site, Day day, Member deputy, Member commander) {
        super(id);
        this.eventType = eventType;
        this.site = site;
        this.day = day;
        this.deputy = deputy;
        this.commander = commander;
    }

    public void resetCommander() {
        if (commander != null) {
            commander.getEvents().remove(getId());
            commander = null;
        }
    }

    public void resetDeputy() {
        if (deputy != null) {
            deputy.getEvents().remove(getId());
            deputy = null;
        }
    }

    public void setCommander(Member commander) {
        resetCommander();
        this.commander = commander;
        if (commander != null) {
            commander.getEvents().put(this);
        }
    }

    public void setDeputy(Member deputy) {
        resetDeputy();
        this.deputy = deputy;
        if (deputy != null) {
            deputy.getEvents().put(this);
        }
    }

    public boolean isManned() {
        return commander != null && deputy != null;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Site getSite() {
        return site;
    }

    public Day getDay() {
        return day;
    }

    public Member getDeputy() {
        return deputy;
    }

    public Member getCommander() {
        return commander;
    }

    @Override
    public int compareTo(Event o) {
        int compare = getDay().compareTo(o.getDay());
        if (compare == 0) {
            return Integer.compare(o.getSite().getSiteType().getCertifications().size(), site.getSiteType().getCertifications().size());
        }
        return compare;
    }

    @Override
    public String toString() {
        if (commander == null || deputy == null) {
            return day + ": Site: " + site + ", Type: " + eventType + ", UNMANNED";
        }
        return day + ": Site: " + site + ", Type: " + eventType + ", Commander: " + commander.getName() + ",\tDeputy: "
                + deputy.getName();
    }
}
