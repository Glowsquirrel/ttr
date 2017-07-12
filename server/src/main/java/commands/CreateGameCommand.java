package commands;

import clientproxy.ClientProxy;
import commandresults.CommandResult;
import serverfacade.ServerFacade;

/**
 * Created by glowsquirrel on 7/11/17.
 */

public class CreateGameCommand extends CreateGameCommandData implements ICommand {

    public CreateGameCommand(String username, String gameName, int numPlayers){
        super(username, gameName, numPlayers);
    }

    @Override
    public CommandResult execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.createGame(username, gameName, numPlayers);
    }
}
