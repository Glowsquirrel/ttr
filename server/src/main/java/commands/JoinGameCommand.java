package commands;

import clientproxy.ClientProxy;
import commandresults.CommandResult;
import serverfacade.ServerFacade;

/**
 * Created by glowsquirrel on 7/11/17.
 */

public class JoinGameCommand extends JoinGameCommandData implements ICommand {

    public JoinGameCommand(String username, String gameName){
        super(username, gameName);
    }
    @Override
    public CommandResult execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.joinGame(username, gameName);
    }
}
