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

    public void postErrorMessage(String errorMessage){
        clientModel.setErrorMessage(errorMessage);
    }

    public void loginUser(CommandResult commandResult){
        LoginResult loginResult = ((LoginResult)commandResult);

        if (loginResult.isSuccess())
            clientModel.setMyUser(loginResult.getUsername());
        else
            this.postErrorMessage(loginResult.getMessage());
    }

    public void registerUser (CommandResult commandResult){
            this.postErrorMessage(commandResult.getMessage()); //registering should only ever do a toast
    }


    public void startGame(CommandResult result) {
        if (result.isSuccess()){
            clientModel.setStartedGame(true);
            this.postErrorMessage(result.getMessage());
        }
        else
            this.postErrorMessage(result.getMessage());

    }

    public void updateGameList(CommandResult result){
        clientModel.setGamesToStart(((PollGamesResult)result).getGameList());
    }

    public void createGame(CommandResult result){
        if (result.isSuccess()){
            clientModel.setMyGame(result.getMessage());
            clientModel.setHasGame(true);
            this.postErrorMessage(result.getMessage());
        }
        else
            this.postErrorMessage(result.getMessage());
    }

    public void joinGame(CommandResult result){
        if (result.isSuccess()){
            clientModel.setMyGame(result.getMessage());
            clientModel.setHasGame(true);
            this.postErrorMessage(result.getMessage());
        }
        else
            this.postErrorMessage(result.getMessage());
    }

    public void leaveGame(CommandResult result){
        if (result.isSuccess()){
            clientModel.setHasGame(false);
            this.postErrorMessage(result.getMessage());
        }
        else
            this.postErrorMessage(result.getMessage());
    }
    //TODO add more methods that will handle each type of CommandResult subclass
}
