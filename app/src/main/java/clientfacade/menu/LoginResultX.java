package clientfacade.menu;

import clientfacade.ClientFacade;
import results.menu.LoginResult;
import interfaces.ICommand;

public class LoginResultX extends LoginResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.loginUser(super.username, null, null);
    }
}
