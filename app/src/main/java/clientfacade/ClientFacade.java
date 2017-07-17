package clientfacade;

import java.util.List;

import commandresults.*;
import commandresults.CommandResult;
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
    public void loginUser(String username, String password, String message){
        clientModel.setMyUser(username);
        if (message != null)
            this.postMessage(message);
    }

    @Override
    public void registerUser (String username, String password, String message){
        if (message != null)
            this.postMessage(message); //registering should only ever do a toast at most
    }

    @Override
    public void startGame(String username, String gameName, String message) {
        clientModel.setStartedGame(true);
        if (message != null)
            this.postMessage(message);
    }

    @Override
    public void updateSingleUserGameList(String username, List<UnstartedGame> gameList, String message){
        clientModel.setGamesToStart(gameList);
        if (message != null)
            this.postMessage(message);
    }

    @Override
    public void createGame(String username, String gameName, String message){ //only called if server already accepted a true createGame
        clientModel.setHasCreatedGame(true);
        clientModel.setHasGame(true);
        if (message != null)
            this.postMessage(message);
    }

    @Override
    public void joinGame(String username, String gameName, String message){
        clientModel.setHasGame(true);
        if (message != null)
            this.postMessage(message);
    }

    @Override
    public void leaveGame(String username, String gameName, String message){
        clientModel.setHasGame(false);
        if (message != null)
            this.postMessage(message);
    }
    //TODO add more methods that will handle each type of CommandResult subclass
}
