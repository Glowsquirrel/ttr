package clientfacade;

import clientfacade.commands.PollCommand;
import commandresults.CommandResult;
import commandresults.LoginResultData;
import commandresults.PollResultData;
import commandresults.RegisterResultData;
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
        LoginResultData loginResult = ((LoginResultData)commandResult);
        if (commandResult.getErrorMessage() == null)
            clientModel.setMyUser(loginResult.getUsername(), loginResult.getAuthToken());
        else{
            this.postErrorMessage(loginResult.getErrorMessage());
        }
    }

    public void registerUser (CommandResult commandResult){
        RegisterResultData loginResult = ((RegisterResultData) commandResult);
        if (commandResult.getErrorMessage() == null)
            clientModel.setMyUser(loginResult.getUsername(), loginResult.getAuthToken());
        else{
            this.postErrorMessage(loginResult.getErrorMessage());
        }
    }


    public void startGame(CommandResult result) {

    }

    public void updateGameList(PollCommand result){
        result.execute();

    }
    //TODO add more methods that will handle each type of CommandResult subclass
}
