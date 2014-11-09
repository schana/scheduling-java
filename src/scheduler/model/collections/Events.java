package scheduler.model.collections;

import scheduler.model.Event;

public class Events extends BaseCollection<Event> {

    public boolean areManned() {
        for (Event event : this) {
            if (!event.isManned()) {
                return false;
            }
        }
        return true;
    }

    public int countManned() {
        int count = 0;
        for (Event event : this) {
            if (event.isManned()) {
                count++;
            }
        }
        return count;
    }

    public Event getFirstUnmanned() {
        for (Event event : this) {
            if (!event.isManned()) {
                return event;
            }
        }
        return null;
    }
}
