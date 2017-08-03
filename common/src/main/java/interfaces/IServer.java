package interfaces;

import java.util.List;

/**
 * The IServer defines methods that the client will call, but will actually be executed on the server.
 * These functions will all return void because they require a connection which will be done by an
 * asynchronous task. Rather, results from this class will return from the server to whichever task
 * the ClientCommunicator created to handle (at this point only a commandTask exists). From there,
 * the Facade will update the ClientModel, which will then notify all its current observers.
 */
public interface IServer {

    //MENU LOGIN
    void login(String username, String password, String sessionID);
    void register(String username, String password, String sessionID);

    //MENU GAMELIST && MENU LOBBY
    void pollGameList(String username);
    void createGame(String username, String gameName, int playerNum);
    void joinGame(String username, String gameName);
    void leaveGame(String username, String gameName);
    void startGame(String gameName, String username);
    void reJoinGame(String username, String gameName);
    
    void drawThreeDestCards(String username, String gameName);
    void returnDestCards(String username, String gameName, int destCard);
    void drawTrainCardFromDeck(String username, String gameName);
    void drawTrainCardFromFaceUp(String username, String gameName, int index);
    void claimRoute(String username, String gameName, int routeID, List<Integer> trainCards);
    void sendChatMessage(String username, String gameName, String message);

}

