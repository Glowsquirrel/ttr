package serverfacade.commands;

import commandresults.CommandResult;
import serverfacade.ServerFacade;

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
