package scheduler.model;

import scheduler.model.collections.Leave;

public class LeaveSection extends BaseItem<LeaveSection> {
    private static Leave leave = new Leave();
    protected Day        start;
    protected Day        end;

    public static LeaveSection create(long id, Day start, Day end) {
        if (!leave.containsKey(id)) {
            leave.put(new LeaveSection(id, start, end));
        }
        return leave.get(id);
    }

    private LeaveSection(long id, Day start, Day end) {
        super(id);
        this.start = start;
        this.end = end;
    }

    public Day getStart() {
        return start;
    }

    public Day getEnd() {
        return end;
    }

    public int getLength() {
        return Day.getLength(start, end);
    }

    public boolean contains(Day day) {
        return Day.contains(day, start, end);
    }
}
