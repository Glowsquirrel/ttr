package clientcommunicator;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import clientfacade.ClientFacade;
import commandresults.CommandResult;
import commandresults.LoginResult;
import serverfacade.commands.Command;

/**
 * The Asynchronous task that all serverfacade.commands will pass through.
 */
public class CommandTask extends AsyncTask<Void, Void, CommandResult> {
    private String ip;
    private String port;
    private Command command;

    public CommandTask(String ip, String port, Command command) {
        this.ip = ip;
        this.port = port;
        this.command = command;
    }

    @Override
    protected CommandResult doInBackground(Void... args) {

        try {
            //set up the URL to get to the command to the correct handler
            String handlerString = "http://" + ip + ":" + port + "/command";
            URL handlerURL = new URL(handlerString);
            HttpURLConnection connection = (HttpURLConnection) handlerURL.openConnection();
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

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(CommandResult.class, new CommandResultSerializer());
            Gson customGson = gsonBuilder.create();
            //give CommandResult to onPostExecute to handle
            return customGson.fromJson(jsonOut, CommandResult.class);

        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
    protected void onPostExecute(CommandResult result){

        ClientFacade facade = new ClientFacade();

        if (result == null){ //the command did not make it to the server.
            //CommandResult testResult = new LoginResult(true, "fakeuser", "failed to connect to server");
            //facade.loginUser(testResult);
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
            case "creategame":
                facade.createGame(result);
                break;
        }


    }
}
