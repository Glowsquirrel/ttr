package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.Result;
import results.game.RejoinResult;

public class RejoinResultX extends RejoinResult implements ICommand {

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.reJoinGame(super.username, super.gameName);
    }
}
