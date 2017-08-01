package clientfacade.menu;

import clientfacade.ClientFacade;
import results.game.RejoinResult;
import interfaces.ICommand;

/**
 *  <h1>ReJoinGameResultX</h1>
 *  Processes the re-joining of a game through the ClientFacade
 *
 *  @author     Nathan Finch
 *  @since      08.01.2017
 */

public class ReJoinGameResultX extends RejoinResult implements ICommand {
    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.reJoinGame(super.username, super.gameName);
    }
}
