package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;

public class StartGameCommand extends StartGameCommandData implements ICommand {

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.startGame(gameName, username);
    }
}
