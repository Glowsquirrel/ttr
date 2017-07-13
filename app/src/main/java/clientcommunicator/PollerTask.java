package clientcommunicator;

import java.util.Timer;
import java.util.TimerTask;

import serverproxy.ServerProxy;


public class PollerTask {
    private int msDelay;
    private Timer timer;
    private String username;

    public PollerTask(String username, int msDelay){
        this.username = username;
        this.msDelay = msDelay;
    }

    /**
     * Starts the polling process at the rate specified when instantiating the object
     */
    public void startPoller() {
        timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            @Override
            public void run() {
                ServerProxy serverProxy = new ServerProxy();
                serverProxy.pollGameList(username);
            }
        }, 0, msDelay);
    }

    /**
     * Stops polling
     */
    public void stopPoller(){
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

}
