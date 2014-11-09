package scheduler.model;

import scheduler.model.collections.Certifications;
import scheduler.model.collections.Events;
import scheduler.model.collections.Leave;
import scheduler.model.collections.Members;

public class Member extends BaseItem<Member> {
    private static Members members = new Members();
    protected String name;
    protected Job job;
    protected Events events;
    protected Crew crew;
    protected Leave leave;
    protected Certifications certifications;

    private Member(long id, String name, Job job, Events events, Crew crew, Leave leave, Certifications certifications) {
        super(id);
        this.name = name;
        this.job = job;
        this.events = events;
        this.crew = crew;
        this.leave = leave;
        this.certifications = certifications;
    }

    public static Member create(long id, String name, Job job, Events events, Crew crew, Leave leave,
                                Certifications certifications) {
        if (!members.containsKey(id)) {
            members.put(new Member(id, name, job, events, crew, leave, certifications));
        }
        return members.get(id);
    }

    public double getEnergy() {
        int eventSize = getEvents().size();
        if (eventSize == 2) {
            return 0.0;
        }
        int energy = 0;
        for (Event event : events) {
            energy += event.getDay().getWeekday().getEnergy();
            if (!event.getCommander().getCrew().equals(event.getDeputy().getCrew())) {
                energy += 2;
            }
        }
        return (double) energy / (double) eventSize;
    }

    public String getName() {
        return name;
    }

    public Job getJob() {
        return job;
    }

    public Events getEvents() {
        return events;
    }

    public Crew getCrew() {
        return crew;
    }

    public Leave getLeave() {
        return leave;
    }

    public Certifications getCertifications() {
        return certifications;
    }

    public int getEventModifier() {
        return -1 * (getLeave().getLength() / (int) Math.ceil(28.0 / (double) getJob().getEventCount()));
    }

    @Override
    public int compareTo(Member other) {
        int comp = getCrew().getSquad().compareTo(other.getCrew().getSquad());
        if (comp == 0) {
            comp = Integer.compare(getEventModifier(), other.getEventModifier());
        }
        if (comp == 0) {
            comp = Integer.compare(getEvents().size(), other.getEvents().size());
        }
        if (comp == 0) {
            comp = Integer.compare(other.getJob().getEventCount() - other.getEvents().size(), getJob().getEventCount() - getEvents().size());
        }
        if (comp == 0) {
            comp = Double.compare(getEnergy(), other.getEnergy());
        }
        if (comp == 0) {
            comp = Long.compare(getId(), other.getId());
        }
        return comp;
    }

    @Override
    public String toString() {
        return String.format("%1$-20s", getName() + ":") + "\tMaxEvents: " + getJob().getEventCount() + ",\tEventCount: " + getEvents().size()
                + ",\tModifier: " + getEventModifier() + ",\tEnergy: " + getEnergy() + ",\tJob: " + getJob();
    }
}
