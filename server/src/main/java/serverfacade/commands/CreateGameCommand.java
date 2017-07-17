package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;

public class CreateGameCommand extends CreateGameCommandData implements ICommand {

    public CreateGameCommand(String username, String gameName, int numPlayers){
        super(username, gameName, numPlayers);
    }

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.createGame(username, gameName, numPlayers);
    }
}
