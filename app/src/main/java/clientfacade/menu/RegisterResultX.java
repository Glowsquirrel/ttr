package clientfacade.menu;

import clientfacade.ClientFacade;
import results.menu.RegisterResult;
import interfaces.ICommand;

public class RegisterResultX extends RegisterResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.registerUser(super.username, null, null, null);
    }
}
