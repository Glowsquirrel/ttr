package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.Result;
import results.game.RejectResult;

/**
 * TODO: description
 *
 * @author Shun Sambongi
 * @version 1.0
 * @since 7/25/17
 */
public class RejectResultX extends RejectResult implements ICommand {
    public RejectResultX(String message) {
        super(message);
    }

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.showRejectMessage(getMessage());
    }
}
