package clientfacade.game;

import clientfacade.ClientFacade;
import results.game.ReplaceFaceUpCardsResult;
import interfaces.ICommand;

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
