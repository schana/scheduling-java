package scheduler.model;

public class Configuration extends BaseItem<Configuration> {
    private int     alertLengthDays;
    private int     crewCountPerAlert;
    private boolean restrictSitesToSquad;

    public Configuration(long id, int alertLengthDays, int crewCountPerAlert, boolean restrictSitesToSquad) {
        super(id);
        this.alertLengthDays = alertLengthDays;
        this.crewCountPerAlert = crewCountPerAlert;
        this.restrictSitesToSquad = restrictSitesToSquad;
    }

    public int getAlertLengthDays() {
        return alertLengthDays;
    }

    public int getCrewCountPerAlert() {
        return crewCountPerAlert;
    }

    public boolean getRestrictSitesToSquad() {
        return restrictSitesToSquad;
    }

}
