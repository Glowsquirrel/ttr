package clientcommunicator;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import clientfacade.ClientFacade;
import commandresults.CommandResult;
import commandresults.LoginResultData;
import commands.Command;

/**
 * The Asynchronous task that all commands will pass through.
 */
public class CommandTask extends AsyncTask<Command, Void, CommandResult> {
    private String ip;
    private String port;

    public CommandTask(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected CommandResult doInBackground(Command... command) {

        try {
            //set up the URL to get to the command to the correct handler
            String trimString = "http://" + ip + ":" + port + "/command";
            URL trimURL = new URL(trimString);
            HttpURLConnection connection = (HttpURLConnection) trimURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.getResponseCode();

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
    protected void onPostExecute(CommandResult result){

        ClientFacade facade = new ClientFacade();

        if (result == null){ //the command did not make it to the server.
            CommandResult testResult = new LoginResultData();
            testResult.setType("login");
            ((LoginResultData)testResult).setUsername("me");
            ((LoginResultData)testResult).setAuthToken("asdf");
            facade.loginUser(testResult);
            //facade.postErrorMessage("Could not connect to server");
            return;
        }


        //TODO add more into switch statement corresponding to each CommandResult subclass

        //Switch statement for the client to somehow handle the CommandResult that has returned from
        //the server.
        switch (result.getType()) {
            case "login":
                facade.loginUser(result);
                break;
        }


    }
}
