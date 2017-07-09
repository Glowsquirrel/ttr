package ClientFacade;

import CommandResults.CommandResult;
import CommandResults.LoginResultData;
import Model.ClientModel;

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
    //TODO add more methods to fully manipulate data in the ClientModel
}
