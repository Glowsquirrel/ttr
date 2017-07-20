package clientfacade.game;

import clientfacade.ClientFacade;
import results.game.ClaimRouteResult;
import interfaces.ICommand;

public class ClaimRouteResultX extends ClaimRouteResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.claimRoute(super.username, super.routeID);
    }
}
