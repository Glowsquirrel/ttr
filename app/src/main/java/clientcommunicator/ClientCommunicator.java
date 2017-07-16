package clientcommunicator;

import android.os.AsyncTask;

import com.google.gson.Gson;

import serverfacade.commands.Command;
import model.ClientModel;
import serverfacade.commands.CreateGameCommandData;
import serverfacade.commands.JoinGameCommandData;
import serverfacade.commands.LeaveGameCommandData;
import serverfacade.commands.LoginCommandData;
import serverfacade.commands.PollGamesCommandData;
import serverfacade.commands.RegisterCommandData;
import serverfacade.commands.StartGameCommandData;
import websocket.MyWebSocket;

/**
 *
 */
public class ClientCommunicator {
    private ClientModel clientModel = ClientModel.getMyClientModel();
    private MyWebSocket webSocket = MyWebSocket.getMyWebSocket();
    private Gson gson = new Gson();

    public void doCommand(Command command){
        String myJsonString;
        switch (command.getType()){
            case "login":
                myJsonString = gson.toJson(command, LoginCommandData.class);
                break;
            case "register":
                myJsonString = gson.toJson(command, RegisterCommandData.class);
                break;
            case "creategame":
                myJsonString = gson.toJson(command, CreateGameCommandData.class);
                break;
            case "pollgames":
                myJsonString = gson.toJson(command, PollGamesCommandData.class);
                break;
            case "startgame":
                myJsonString = gson.toJson(command, StartGameCommandData.class);
                break;
            case "leavegame":
                myJsonString = gson.toJson(command, LeaveGameCommandData.class);
                break;
            case "joingame":
                myJsonString = gson.toJson(command, JoinGameCommandData.class);
                break;

            default:
                myJsonString = "";
        }

        webSocket.sendJson(myJsonString);

        //new CommandTask(clientModel.getIp(), clientModel.getPort(), command).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
