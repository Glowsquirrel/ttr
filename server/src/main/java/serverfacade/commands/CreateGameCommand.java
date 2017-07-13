package serverfacade.commands;

import commandresults.CommandResult;
import serverfacade.ServerFacade;

public class CreateGameCommand extends CreateGameCommandData implements ICommand {

    public CreateGameCommand(String username, String gameName, int numPlayers){
        super(username, gameName, numPlayers);
    }

    @Override
    public CommandResult execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.createGame(username, gameName, numPlayers);
    }
}