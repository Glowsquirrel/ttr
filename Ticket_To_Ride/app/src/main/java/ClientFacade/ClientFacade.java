package ClientFacade;

import CommandResults.CommandResult;
import CommandResults.LoginResultData;
import Model.ClientModel;

//only this class may modify data in the clientmodel
public class ClientFacade {
    private ClientModel clientModel = ClientModel.getMyClientModel();

    public void loginUser(CommandResult commandResult){

        LoginResultData loginResult = ((LoginResultData)commandResult);
        clientModel.setMyUsername(loginResult.getUsername());
        clientModel.setMyAuthToken(loginResult.getAuthToken());


    }
}
