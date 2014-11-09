package scheduler.model;

import scheduler.model.collections.Crews;
import scheduler.model.collections.Members;

public class Crew extends BaseItem<Crew> {
    private static Crews crews = new Crews();
    protected Members    members;
    protected Squad      squad;

    public static Crew create(long id, Squad squad, Members members) {
        if (!crews.containsKey(id)) {
            crews.put(new Crew(id, squad, members));
        }
        return crews.get(id);
    }

    private Crew(long id, Squad squad, Members members) {
        super(id);
        this.squad = squad;
        this.members = members;
    }

    public Members getMatched(Member other) {
        Members matched = new Members();
        for (Member member : getMembers()) {
            if (!member.getJob().getPosition().equals(other.getJob().getPosition())) {
                matched.put(member);
            }
        }
        return matched;
    }

    public Members getMembers() {
        return members;
    }

    public Squad getSquad() {
        return squad;
    }

    @Override
    public int compareTo(Crew other) {
        int compare = this.getMembers().compareTo(other.getMembers());
        if (compare == 0) {
            return super.compareTo(other);
        }
        return compare;
    }

    @Override
    public String toString() {
        String toReturn = Long.toString(getId()) + "\n";
        for (Member m : members) {
            toReturn += m + "\n";
        }
        return toReturn;
    }
}
