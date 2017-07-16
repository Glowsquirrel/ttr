package clientcommunicator;

import android.os.AsyncTask;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import model.ClientModel;
import serverfacade.commands.Command;
import serverfacade.commands.PollGamesCommandData;

/**
 * A class that manages polling services for the client. By implement the Serializable interface, a
 * pointer to a single PollerTask can be passed between activities.
 */
public class PollerTask implements Serializable {
    private int msDelay;
    private Timer timer = new Timer();
    private String username;
    private String gameName;
    private String ip;
    private String port;
    private AsyncTask myCommandTask; //pointer to the command task that is in the background.
    private int pollCounter = 1;    //counts # of polls. for testing

    /**
     * Constructor for polling the game list. Retrieves necessary data from the ClientModel.
     * @param msDelay Delay between polls.
     */
    public PollerTask(int msDelay){
        this.msDelay = msDelay;
        ClientModel clientModel = ClientModel.getMyClientModel();
        this.username = clientModel.getMyUsername();
        this.ip = clientModel.getIp();
        this.port = clientModel.getPort();
        this.gameName = clientModel.getMyGameName();
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
                Command pollCommand = new PollGamesCommandData(username);
                myCommandTask = new CommandTask(ip, port, pollCommand).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }, 0, msDelay);
    }

    //TODO create a pollActionListCommand and implement below

    /**
     * Polls the current action list held by the server on a fixed timer.
     */
    public void pollActionList(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            }
        }, 0, msDelay);
    }

    /**
     * Stops the poller and any asynchronous task created by the controller.
     */
    public void stopPoller(){
        myCommandTask.cancel(true);
        timer.cancel();
        timer.purge();
    }

    public int getPollCount() { //testing getter
        return pollCounter++;
    }

    public int getMsDelay() { //test getter.
        return msDelay;
    }
}
