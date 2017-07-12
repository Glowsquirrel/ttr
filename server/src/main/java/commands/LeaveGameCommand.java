package commands;

import clientproxy.ClientProxy;
import commandresults.CommandResult;
import serverfacade.ServerFacade;

/**
 * Created by glowsquirrel on 7/11/17.
 */

public class LeaveGameCommand extends LeaveGameCommandData implements ICommand{

    public LeaveGameCommand(String username, String gameName){
        super(username, gameName);
    }

    @Override
    public CommandResult execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.leaveGame(username, gameName);
    }
}
