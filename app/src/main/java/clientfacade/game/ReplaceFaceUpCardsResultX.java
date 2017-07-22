package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.game.ReplaceFaceUpCardsResult;

public class ReplaceFaceUpCardsResultX extends ReplaceFaceUpCardsResult implements ICommand {

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.replaceFaceUpCards(super.trainCards);
    }
}
