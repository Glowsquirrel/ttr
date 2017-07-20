package clientfacade.menu;

import clientfacade.ClientFacade;
import results.menu.MessageResult;
import interfaces.ICommand;

public class MessageResultX extends MessageResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.postMessage(super.message);
    }
}
