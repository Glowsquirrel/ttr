package clientfacade.game;

import clientfacade.ClientFacade;
import results.game.ReturnFirstDestCardResult;
import interfaces.ICommand;

public class ReturnFirstDestCardResultX extends ReturnFirstDestCardResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.returnFirstDestCards(super.username, super.cardReturned);
    }
}
