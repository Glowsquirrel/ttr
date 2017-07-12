package clientfacade;
import commandresults.*;

import clientfacade.commands.PollCommand;
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
            this.postErrorMessage(loginResult.getErrorMessage());
    }

    public void registerUser (CommandResult commandResult){
            this.postErrorMessage(commandResult.getErrorMessage()); //registering should only ever do a toast
    }


    public void startGame(CommandResult result) {

    }

    public void updateGameList(PollCommand result){
        result.execute();

    }

    public void createGame(CommandResult result){
        if (result.isSuccess()){
            //send a join game command
            this.postErrorMessage(result.getErrorMessage());
        }
        else
            this.postErrorMessage(result.getErrorMessage());


    }
    //TODO add more methods that will handle each type of CommandResult subclass
}
