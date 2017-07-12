package interfaces;

import commandresults.CommandResult;
import commandresults.LoginResult;
import commandresults.PollGamesResult;

/**
 * The IServer defines methods that the client will call, but will actually be executed on the server.
 * These functions will all return void because they require a connection which will be done by an
 * asynchronous task. Rather, results from this class will return from the server to whichever task
 * the ClientCommunicator created to handle (at this point only a commandTask exists). From there,
 * the Facade will update the ClientModel, which will then notify all its current observers.
 */
public interface IServer {

    //MENU LOGIN
    LoginResult login(String username, String password);
    CommandResult register(String username, String password);

    //MENU GAMELIST && MENU LOBBY
    PollGamesResult pollGameList(String username);
    CommandResult createGame(String username, String gameName, int playerNum);
    CommandResult joinGame(String username, String gameName);
    CommandResult leaveGame(String username, String gameName);
    CommandResult startGame(String gameName);

}

