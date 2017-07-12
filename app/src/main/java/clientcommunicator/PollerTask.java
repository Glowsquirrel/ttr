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
import clientfacade.commands.PollCommandResult;
import serverfacade.commands.PollGamesCommandData;
import model.ClientModel;

public class PollerTask extends AsyncTask<Void, Void, Void> {
    private ClientFacade clientFacade = new ClientFacade();
    private ClientModel clientModel = ClientModel.getMyClientModel();
    private String ip = clientModel.getIp();
    private String port = clientModel.getPort();
    private PollGamesCommandData pollData;

    public PollerTask(PollGamesCommandData pollData) {
        this.pollData = pollData;
    }

    @Override
    protected Void doInBackground(Void... args) {

        while(true) {
            try {

                String trimString = "http://" + ip + ":" + port + "/command";
                URL trimURL = new URL(trimString);
                HttpURLConnection connection = (HttpURLConnection) trimURL.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                //convert to JSON
                //GsonBuilder gsonBuilder = new GsonBuilder();
                //gsonBuilder.registerTypeAdapter(Command.class, new CommandDeserializer());
                //Gson gson = gsonBuilder.create();
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

                PollCommandResult result = gson.fromJson(jsonOut, PollCommandResult.class);

                clientFacade.updateGameList(result);

                Thread.sleep(3000); //wait for 3s after updating

            } catch (InterruptedException | IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}
