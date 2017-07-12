package serverfacade.commands;

import commandresults.CommandResult;
import serverfacade.ServerFacade;

public class StartGameCommand extends StartGameCommandData implements ICommand{

    public StartGameCommand(String gameName){
        super(gameName);
    }

    @Override
    public CommandResult execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.startGame(gameName);
    }
}
