package serverfacade.commands.menu;

import commands.menu.CreateGameCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class CreateGameCommandX extends CreateGameCommand implements ICommand {

    public CreateGameCommandX(String username, String gameName, int numPlayers){
        super(username, gameName, numPlayers);
    }

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.createGame(username, gameName, numPlayers);
    }
}
