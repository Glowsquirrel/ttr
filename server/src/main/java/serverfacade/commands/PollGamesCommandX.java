package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;

public class PollGamesCommandX extends PollGamesCommand implements ICommand {
    public PollGamesCommandX(String username) {
        super(username);
    }

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.pollGameList(username);
    }
}
