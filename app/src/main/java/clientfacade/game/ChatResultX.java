package clientfacade.game;

import clientfacade.ClientFacade;
import results.game.ChatResult;
import interfaces.ICommand;

public class ChatResultX extends ChatResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.addChat(super.username, super.message);
    }
}
