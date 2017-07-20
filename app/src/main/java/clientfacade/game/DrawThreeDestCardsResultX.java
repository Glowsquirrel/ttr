package clientfacade.game;

import clientfacade.ClientFacade;
import results.game.DrawThreeDestCardsResult;
import interfaces.ICommand;

public class DrawThreeDestCardsResultX extends DrawThreeDestCardsResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.drawDestCards(super.username, super.destCards);
    }
}
