package clientfacade.commands;

import clientfacade.ClientFacade;
import commandresults.PollGamesResult;
import interfaces.ICommand;

/**
 * Created by glowsquirrel on 7/17/17.
 */

public class PollGamesResultX extends PollGamesResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.updateSingleUserGameList(super.username, super.gameList, super.message);
    }
}
