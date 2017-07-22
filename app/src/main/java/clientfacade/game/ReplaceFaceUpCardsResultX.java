package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.game.ReplaceFaceUpCardsResult;

/**
 * Created by sjrme on 7/21/17.
 */

public class ReplaceFaceUpCardsResultX extends ReplaceFaceUpCardsResult implements ICommand {

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.replaceFaceUpCards(super.trainCards);
    }
}
