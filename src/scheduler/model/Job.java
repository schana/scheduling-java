package scheduler.model;

public class Job extends BaseItem<Job> {
    protected String description;
    protected Position position;
    protected int alertCount;
    protected int backupCount;
    protected int eventCount;

    public Job(long id, String description, Position position, int alertCount, int backupCount, int eventCount) {
        super(id);
        this.description = description;
        this.position = position;
        this.alertCount = alertCount;
        this.backupCount = backupCount;
        this.eventCount = eventCount;
    }

    public String getDescription() {
        return description;
    }

    public Position getPosition() {
        return position;
    }

    public int getAlertCount() {
        return alertCount;
    }

    public int getBackupCount() {
        return backupCount;
    }

    public int getEventCount() {
        return eventCount;
    }

    public String toString() {
        return getDescription();
    }

}
