package clientcommunicator;

import com.google.gson.Gson;

import serverfacade.commands.Command;
import serverfacade.commands.CreateGameCommand;
import serverfacade.commands.JoinGameCommand;
import serverfacade.commands.LeaveGameCommand;
import serverfacade.commands.LoginCommand;
import serverfacade.commands.PollGamesCommand;
import serverfacade.commands.RegisterCommand;
import serverfacade.commands.StartGameCommand;
import websocket.ClientWebSocket;

/**
 *
 */
public class ClientCommunicator
{
    private ClientWebSocket webSocket = ClientWebSocket.getClientWebSocket();
    private Gson gson = new Gson();

    public void doCommand(Command command)
    {
        String myJsonString;
        switch (command.getType())
        {
            case "login":
                myJsonString = gson.toJson(command, LoginCommand.class);
                break;
            case "register":
                myJsonString = gson.toJson(command, RegisterCommand.class);
                break;
            case "creategame":
                myJsonString = gson.toJson(command, CreateGameCommand.class);
                break;
            case "pollgames":
                myJsonString = gson.toJson(command, PollGamesCommand.class);
                break;
            case "startgame":
                myJsonString = gson.toJson(command, StartGameCommand.class);
                break;
            case "leavegame":
                myJsonString = gson.toJson(command, LeaveGameCommand.class);
                break;
            case "joingame":
                myJsonString = gson.toJson(command, JoinGameCommand.class);
                break;

            default:
                myJsonString = "";
        }

        webSocket.sendJson(myJsonString);
    }
}
