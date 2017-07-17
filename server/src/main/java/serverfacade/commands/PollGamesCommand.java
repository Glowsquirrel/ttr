package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;

public class PollGamesCommand extends PollGamesCommandData implements ICommand {
    public PollGamesCommand(String username) {
        super(username);
    }

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.pollGameList(username);
    }
}
