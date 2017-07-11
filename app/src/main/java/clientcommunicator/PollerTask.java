package clientcommunicator;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import clientfacade.ClientFacade;
import commandresults.CommandResult;
import commandresults.LoginResultData;
import commands.Command;

/**
 * Created by Rachael on 7/11/2017.
 */

public class PollerTask extends AsyncTask<Command, Void, CommandResult> {
    private String ip;
    private String port;
    private Timer time;

    public PollerTask() {

    }

    @Override
    protected CommandResult doInBackground(Command... command) {

        time= new Timer();
        time.schedule(new poll(command),0, 1000);
        return new CommandResult();
    }
    public void endTimer()
    {
        time.cancel();
    }
    protected void onPostExecute(CommandResult result){




    }
    private class poll extends TimerTask
    {
        private Command[] command;
        public void poll(Command... command)
        {
            this.command=command;
        }
        @Override
        public void run()
        {
            try{
            //set up the URL to get to the command to the correct handler
            String trimString = "http://" + ip + ":" + port + "/command";
            URL trimURL = new URL(trimString);
            HttpURLConnection connection = (HttpURLConnection) trimURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            //convert to JSON
            Gson gson = new Gson();
            String jsonIn = gson.toJson(command);

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

            //give CommandResult to onPostExecute to handle
            return gson.fromJson(jsonOut, CommandResult.class);

        }catch (IOException ex){
        ex.printStackTrace();
        return null;
    }
        }
    }
}
