package serverfacade.commands.menu;

import commands.menu.PollGamesCommand;
import interfaces.ICommandX;
import serverfacade.ServerFacade;

public class PollGamesCommandX extends PollGamesCommand implements ICommandX {

    public PollGamesCommandX(String username) {
        super(username);
    }

    @Override
    public boolean execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.pollGameList(username);
    }

    //This does not need to be saved
    @Override
    public void addToDatabase() {}
}
