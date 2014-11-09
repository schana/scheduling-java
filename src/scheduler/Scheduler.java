package scheduler;

import scheduler.da.Data;
import scheduler.schedule.Schedule;

import java.sql.SQLException;

public class Scheduler extends Thread {

    private Schedule schedule;

    public Scheduler(String fileIn) throws SQLException {
        Data data = Data.getData(fileIn);
        schedule = data.getSchedule(2014, 2);
    }

    public static void main(String[] args) throws SQLException {
        if (args.length < 1) {
            System.exit(1);
        }
        String fileIn = args[0];
        final Scheduler s = new Scheduler(fileIn);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                s.interrupt();
                try {
                    s.join();
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        s.start();
    }

    @Override
    public void run() {
        schedule.fill();
    }
}
