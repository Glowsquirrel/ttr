package clientproxy;

import java.util.logging.Logger;

import interfaces.IProxy;

//TODO implement methods here

/**
 * The ClientProxy is the proxy on the server side that does execution for the client.
 *
 */
public class ClientProxy implements IProxy {

    private static Logger logger = Logger.getLogger("serverlog");

    public void login(String username, String password){


    }
    public boolean register(String username, String password){

        return true;
    }

    @Override
    public void pollGameList(String username) {

    }

    @Override
    public void createGame(String username) {

    }

    @Override
    public void joinGame(String username) {

    }

    @Override
    public void leaveGame(String username) {

    }

    @Override
    public void startGame(String username) {

    }
}
