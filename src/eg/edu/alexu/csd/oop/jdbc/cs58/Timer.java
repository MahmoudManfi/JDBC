package eg.edu.alexu.csd.oop.jdbc.cs58;

import java.sql.SQLTimeoutException;

public class Timer extends Thread {

    private int timeout ;
    public Timer(int timeout) {

        this.timeout = timeout;
    }

    @Override
    public void run() {

        if (timeout == 0) return;

        try {
            sleep(timeout*1000);
            interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
