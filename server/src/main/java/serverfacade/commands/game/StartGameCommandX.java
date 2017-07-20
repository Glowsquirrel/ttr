package serverfacade.commands.game;

import commands.game.StartGameCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class StartGameCommandX extends StartGameCommand implements ICommand {

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.startGame(gameName, username);
    }
}
