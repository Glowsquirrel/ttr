package clientfacade;

import commandresults.*;
import commandresults.CommandResult;
import model.ClientModel;

/**
 * This is the only class who may change information in the ClientModel. Other classes may retrieve
 * data, but not modify from the ClientModel.
 */
public class ClientFacade {
    private ClientModel clientModel = ClientModel.getMyClientModel();

    private void postMessage(String message){
        clientModel.setErrorMessage(message);
    }

    public void loginUser(CommandResult commandResult){
        LoginResult loginResult = ((LoginResult)commandResult);

        if (loginResult.isSuccess())
            clientModel.setMyUser(loginResult.getUsername());
        else
            this.postMessage(loginResult.getMessage());
    }

    public void registerUser (CommandResult commandResult){
            this.postMessage(commandResult.getMessage()); //registering should only ever do a toast
    }


    public void startGame(CommandResult result) {
        if (result.isSuccess()){
            clientModel.setStartedGame(true);
            this.postMessage(result.getMessage());
        }
        else
            this.postMessage(result.getMessage());

    }

    public void updateGameList(CommandResult result){
        clientModel.setGamesToStart(((PollGamesResult)result).getGameList());
    }

    public void createGame(CommandResult result){
        if (result.isSuccess()){
            clientModel.setHasCreatedGame(true);
            clientModel.setHasGame(true);
            this.postMessage(result.getMessage());
        }
        else
            this.postMessage(result.getMessage());
    }

    public void joinGame(CommandResult result){
        if (result.isSuccess()){
            //clientModel.setMyGame(result.getMessage());
            clientModel.setHasGame(true);
            this.postMessage(result.getMessage());
        }
        else
            this.postMessage(result.getMessage());
    }

    public void leaveGame(CommandResult result){
        if (result.isSuccess()){
            clientModel.setHasGame(false);
            this.postMessage(result.getMessage());
        }
        else
            this.postMessage(result.getMessage());
    }
    //TODO add more methods that will handle each type of CommandResult subclass
}
