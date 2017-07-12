package commands;

import clientproxy.ClientProxy;
import commandresults.CommandResult;
import serverfacade.ServerFacade;

/**
 * Created by shunsambongi on 7/11/17.
 */

public class PollGamesCommand extends PollGamesCommandData implements ICommand {
    public PollGamesCommand(String username) {
        super(username);
    }

    @Override
    public CommandResult execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.pollGameList(username);
    }
}
