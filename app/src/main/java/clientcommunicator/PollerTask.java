package clientcommunicator;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import clientfacade.ClientFacade;
import commandresults.PollGamesResult;
import model.ClientModel;
import serverfacade.commands.PollGamesCommandData;


public class PollerTask {
    private PollGamesCommandData pollGamesData = null;
    private int msDelay;
    private PollGameListTask pollGameListTask;
    private Timer timer;
    //private PollGameActionsData pollGameActions = null

    public PollerTask(PollGamesCommandData pollData, int msDelay){
        this.pollGamesData = pollData;
        this.msDelay = msDelay;
    }



    public void pollGamesList() {
        timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            @Override
            public void run() {
                pollGameListTask = new PollGameListTask(pollGamesData);
                pollGameListTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }, 0, msDelay);
    }

    public void stopPoller(){
        pollGameListTask.cancel(true);
        timer.cancel();
        timer.purge();

    }

    private class PollGameListTask extends AsyncTask<Void, Void, PollGamesResult> {
        private ClientFacade clientFacade = new ClientFacade();
        private ClientModel clientModel = ClientModel.getMyClientModel();
        private String ip = clientModel.getIp();
        private String port = clientModel.getPort();
        private PollGamesCommandData pollData;

        private PollGameListTask(PollGamesCommandData pollData) {
            this.pollData = pollData;
        }

        @Override
        protected PollGamesResult doInBackground(Void... args) {

                try {
                    String trimString = "http://" + ip + ":" + port + "/command";
                    URL trimURL = new URL(trimString);
                    HttpURLConnection connection = (HttpURLConnection) trimURL.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    //convert to JSON
                    Gson gson = new Gson();

                    String jsonIn = gson.toJson(pollData);

                    //send JSON string to server
                    byte[] outputInBytes = jsonIn.getBytes("UTF-8");
                    OutputStream os = connection.getOutputStream();
                    os.write(outputInBytes);
                    os.close();

                    connection.connect();

                    //get JSON string back from server
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder lowerString = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lowerString.append(line);
                    }
                    reader.close();
                    String jsonOut = lowerString.toString();

                    return gson.fromJson(jsonOut, PollGamesResult.class);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    return null;
                }

        }

        protected void onPostExecute(PollGamesResult result){
            clientFacade.updateGameList(result);
        }

    }
}
