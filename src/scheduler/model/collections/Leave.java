package scheduler.model.collections;

import scheduler.model.Day;
import scheduler.model.LeaveSection;

public class Leave extends BaseCollection<LeaveSection> {

    public boolean contains(Day day) {
        for (LeaveSection leaveSection : this) {
            if (leaveSection.contains(day)) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        int count = 0;
        for (LeaveSection leaveSection : this) {
            count += leaveSection.getLength();
        }
        return count;
    }
}
