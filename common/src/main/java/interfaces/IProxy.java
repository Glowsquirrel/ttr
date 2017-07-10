package interfaces;

/**
 * The IProxy defines methods that the client will call, but will actually be executed on the server.
 * These functions will all return void because they require a connection which will be done by an
 * asynchronous task. Rather, results from this class will return from the server to whichever task
 * the ClientCommunicator created to handle (at this point only a commandTask exists). From there,
 * the Facade will update the ClientModel, which will then notify all its current observers.
 */
public interface IProxy {
    /**
     * Attempt to login.
     * @param username The username used in an attempt to login.
     * @param password The password used in an attempt to login.
     */
    void login(String username, String password);

    boolean register(String username, String password);
    void pollGameList(String username);
    void createGame(String username);
    void joinGame(String username);
    void leaveGame(String username);
    void startGame(String username);



    // TODO complete javadoc here
}

