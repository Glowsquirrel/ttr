package clientcommunicator;

import java.util.Timer;
import java.util.TimerTask;

import serverproxy.ServerProxy;

/**
 * A class that manages polling services for the client.
 */
public class PollerTask {
    private int msDelay;
    private Timer timer = new Timer();
    private String username;
    private String gameName;
    private ServerProxy serverProxy = new ServerProxy();

    /**
     * Constructor for polling the game list.
     * @param username Identifier of who is polling.
     * @param msDelay Delay between polls.
     */
    public PollerTask(String username, int msDelay){
        this.username = username;
        this.msDelay = msDelay;
    }

    /**
     * Constructor for polling the action list.
     * @param username Identifier of who is polling.
     * @param gameName Name of the game to get actions from.
     * @param msDelay Delay between polls.
     */
    public PollerTask(String username, String gameName, int msDelay){
        this.username = username;
        this.gameName = gameName;
        this.msDelay = msDelay;
    }

    /**
     * Polls the current game list held by the server on a fixed timer.
     */
    public void pollGameList() {
        timer.scheduleAtFixedRate( new TimerTask() {
            @Override
            public void run() {
                serverProxy.pollGameList(username);
            }
        }, 0, msDelay);
    }

    //TODO create a pollActionList and uncomment below

    /**
     * Polls the current action list held by the server on a fixed timer.
     */
    public void pollActionList(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //serverProxy.pollActionList(username, gameName);
            }
        }, 0, msDelay);
    }

    /**
     * Stops polling
     */
    public void stopPoller(){
        timer.cancel();
        timer.purge();
    }

}
