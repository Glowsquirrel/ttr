package clientfacade.game;

import clientfacade.ClientFacade;
import results.game.ReturnDestCardsResult;
import interfaces.ICommand;

public class ReturnDestCardsResultX extends ReturnDestCardsResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.returnDestCards(super.username, super.destCards);
    }
}
