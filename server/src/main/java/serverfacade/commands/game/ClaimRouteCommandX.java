package serverfacade.commands.game;

import commands.game.ClaimRouteCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class ClaimRouteCommandX extends ClaimRouteCommand implements ICommand {

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.claimRoute(username, gameName, routeID);
    }
}
