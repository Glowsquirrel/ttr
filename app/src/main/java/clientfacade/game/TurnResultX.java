package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.game.TurnResult;

/**
 * Created by sjrme on 7/29/17.
 */

public class TurnResultX extends TurnResult implements ICommand {

    @Override
    public void execute(){
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.turn(username);
    }
}
