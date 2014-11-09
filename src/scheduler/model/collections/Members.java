package scheduler.model.collections;

import scheduler.model.Member;

import java.util.Collections;

public class Members extends BaseCollection<Member> implements Comparable<Members> {

    public Member getBest() {
        return getHelper(true);
    }

    public Member getWorst() {
        return getHelper(false);
    }

    protected Member getHelper(boolean best) {
        if (isEmpty()) {
            return null;
        }
        Collections.sort(list);
        if (best) {
            return list.get(0);
        } else {
            return list.get(list.size() - 1);
        }
    }

    public double getEnergy() {
        double high = last().getEnergy();
        double low = high;
        int maxSlots = last().getJob().getEventCount() + last().getEventModifier() - last().getEvents().size();
        int minSlots = maxSlots;
        for (Member m : this) {
            double energy = m.getEnergy();
            if (energy > high) {
                high = energy;
            }
            if (energy < low) {
                low = energy;
            }
            int slots = m.getJob().getEventCount() + m.getEventModifier() - m.getEvents().size();
            if (slots > maxSlots) {
                maxSlots = slots;
            }
            if (slots < minSlots) {
                minSlots = slots;
            }
        }
        return (double) (2 * (maxSlots - minSlots)) + high;
    }

    @Override
    public int compareTo(Members other) {
        Member otherBest = other.getBest();
        Member thisBest = getBest();
        if (otherBest == null && thisBest == null) {
            return 0;
        } else if (otherBest == null) {
            return -1;
        } else if (thisBest == null) {
            return 1;
        } else {
            return thisBest.compareTo(otherBest);
        }

    }
}
