package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.game.GameHistoryResult;

/**
 * Created by trznyk on 7/20/17.
 */

public class GameHistoryX extends GameHistoryResult implements ICommand {

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.addHistory(super.message);
    }
}
