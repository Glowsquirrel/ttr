package clientfacade.menu;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.menu.RegisterResult;

public class RegisterResultX extends RegisterResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.registerUser(super.username, null, super.message, null);
    }
}
