package clientfacade;

import java.util.List;

import interfaces.IClient;
import model.ClientModel;
import model.UnstartedGame;

/**
 * This is the only class who may change information in the ClientModel. Other classes may retrieve
 * data, but not modify data in the ClientModel.
 */
public class ClientFacade implements IClient{
    private ClientModel clientModel = ClientModel.getMyClientModel();

    private void postMessage(String message){
        clientModel.setErrorMessage(message);
    }

    @Override
    public boolean loginUser(String username, String password, String message, String sessionID){
        clientModel.setMyUser(username);
        if (message != null)
            this.postMessage(message);
        return true;
    }

    @Override
    public boolean registerUser (String username, String password, String message, String sessionID){
        if (message != null)
            this.postMessage(message); //registering should only ever do a toast at most
        return true;
    }

    @Override
    public boolean startGame(String username, String gameName, String message) {
        clientModel.setStartedGame(true);
        if (message != null)
            this.postMessage(message);
        return true;
    }

    @Override
    public boolean updateSingleUserGameList(String username, List<UnstartedGame> gameList, String message){
        clientModel.setGamesToStart(gameList);
        if (message != null)
            this.postMessage(message);
        return true;
    }

    @Override
    public boolean createGame(String username, String gameName, String message){ //only called if server already accepted a true createGame
        clientModel.setHasCreatedGame(true);
        clientModel.setHasGame(true);
        if (message != null)
            this.postMessage(message);
        return true;
    }

    @Override
    public boolean joinGame(String username, String gameName, String message){
        clientModel.setHasGame(true);
        if (message != null)
            this.postMessage(message);
        return true;
    }

    @Override
    public boolean leaveGame(String username, String gameName, String message){
        clientModel.setHasGame(false);
        if (message != null)
            this.postMessage(message);
        return true;
    }
    //TODO add more methods that will handle commands from the server
}
