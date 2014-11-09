package scheduler.schedule;

import scheduler.model.*;
import scheduler.model.collections.Crews;
import scheduler.model.collections.Events;
import scheduler.model.collections.Members;

public class Schedule {
    public static final int CHECK_GOOD = 0;
    public static final int CHECK_NULL = 1;
    public static final int CHECK_COUNT = 2;
    public static final int CHECK_SQUAD = 3;
    public static final int CHECK_CERTS = 4;
    public static final int CHECK_LEAVE = 5;
    public static final int CHECK_DAY_CONFLICT = 6;
    private static boolean DEBUG = false;
    private Crews crews;
    private Members floaters = new Members();
    private Crews availableCrews = new Crews();
    private Events events;
    private Configuration configuration;

    public Schedule(Events events, Crews crews, Configuration configuration) {
        this.events = events;
        this.configuration = configuration;
        this.crews = crews;
    }

    private void debugCheckString(int check) {
        switch (check) {
            case CHECK_GOOD:
                System.out.println("Good");
                break;
            case CHECK_NULL:
                System.out.println("Null");
                break;
            case CHECK_COUNT:
                System.out.println("Count");
                break;
            case CHECK_SQUAD:
                System.out.println("Squad");
                break;
            case CHECK_CERTS:
                System.out.println("Certs");
                break;
            case CHECK_LEAVE:
                System.out.println("Leave");
                break;
            case CHECK_DAY_CONFLICT:
                System.out.println("Day Conflict");
                break;
        }
    }

    private int decide(Member member, Event event) {
        if (member == null) {
            return CHECK_NULL;
        }
        int eventCount = member.getEvents().size();
        int maxEventCount = member.getJob().getEventCount() + member.getEventModifier();
        /* Less than max events */
        if (eventCount >= maxEventCount) {
            return CHECK_COUNT;
        }
        /* In squad */
        if (configuration.getRestrictSitesToSquad()) {
            if (!member.getCrew().getSquad().equals(event.getSite().getSquad())) {
                return CHECK_SQUAD;
            }
        }
        /* Certs */
        if (!member.getCertifications().meetsRequirements(event.getSite().getSiteType().getCertifications())) {
            return CHECK_CERTS;
        }
        /* Leave */
        if (member.getLeave().contains(event.getDay())) {
            return CHECK_LEAVE;
        }
        /* Overlapping alerts */
        for (Event e : member.getEvents()) {
            int length = Day.getLength(e.getDay(), event.getDay());
            if (length < 3) {
                return CHECK_DAY_CONFLICT;
            }
        }
        /* Flt Commanders weekends */
        if (member.getJob().getId() == 4) {
            if (event.getDay().getWeekday().getEnergy() > 1) {
                return CHECK_DAY_CONFLICT;
            }
        }
        return CHECK_GOOD;
    }

    public boolean check(Member member, Event event) {
        int value = decide(member, event);
        if (DEBUG && value != CHECK_GOOD) {
            System.out.println(member);
            System.out.println(event);
            debugCheckString(value);
        }
        return value == CHECK_GOOD;
    }

    private void fillEventByJob(Member member, Member other, Event event) {
        if (member.getJob().getPosition().isDeputy()) {
            event.setDeputy(member);
            event.setCommander(other);
        } else {
            event.setCommander(member);
            event.setDeputy(other);
        }
    }

    private void fillEventForCrew(Crew crew, Event event) {
        boolean found = false;
        Member a = null;
        Member b = null;
        for (Member m : crew.getMembers()) {
            if (check(m, event)) {
                for (Member o : crew.getMatched(m)) {
                    if (check(o, event)) {
                        a = m;
                        b = o;
                        found = true;
                    }
                    if (found) {
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }
        if (found) {
            fillEventByJob(a, b, event);
        }
    }

    public void fill() {
        while (!events.areManned()) {
            for (Event event : events) {
                System.out.println(event);
                availableCrews.clear();
                floaters.clear();
                for (Crew c : crews) {
                    Members tempFloaters = new Members();
                    boolean putCrew = false;
                    for (Member m : c.getMembers()) {
                        if (check(m, event)) {
                            tempFloaters.put(m);
                            for (Member o : c.getMatched(m)) {
                                if (check(o, event)) {
                                    putCrew = true;
                                }
                            }
                        }
                    }
                    if (putCrew) {
                        availableCrews.put(c);
                    } else {
                        for (Member m : tempFloaters) {
                            floaters.put(m);
                        }
                    }
                }

                System.out.println("F: " + floaters.size() + " C: " + availableCrews.size());

                /*if (!event.isManned()) {
                    for (Crew c : availableCrews) {
                        if (c.getMembers().getBest().getEventModifier() <= -3) {
                            fillEventForCrew(c, event);
                            break;
                        }
                    }
                }*/
                if (!event.isManned()) {
                    for (Member m : floaters) {
                        if (m.getEventModifier() <= -3) {
                            for (Member o : floaters) {
                                if (!o.getJob().getPosition().equals(m.getJob().getPosition())) {
                                    fillEventByJob(m, o, event);
                                    break;
                                }
                            }
                        }
                        if (event.isManned()) {
                            break;
                        }
                    }
                }
                if (!event.isManned()) {
                    for (Crew c : availableCrews) {
                        fillEventForCrew(c, event);
                        break;
                    }
                }
                if (!event.isManned()) {
                    for (Member m : floaters) {
                        for (Member o : floaters) {
                            if (!o.getJob().getPosition().equals(m.getJob().getPosition())) {
                                fillEventByJob(m, o, event);
                                break;
                            }
                        }
                        if (event.isManned()) {
                            break;
                        }
                    }
                }

                if (!event.isManned()) {
                    for (Member m : floaters) {
                        for (Crew c : availableCrews) {
                            for (Member o : c.getMembers()) {
                                if (!o.getJob().getPosition().equals(m.getJob().getPosition())) {
                                    fillEventByJob(m, o, event);
                                    break;
                                }
                            }
                            if (event.isManned()) {
                                break;
                            }
                        }
                        if (event.isManned()) {
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(events);
        for (Crew c : crews) {
            for (Member m : c.getMembers()) {
                System.out.println(m);
                System.out.println(m.getEvents());
            }
        }
    }

    public String toString() {
        return events.toString();
    }
}
