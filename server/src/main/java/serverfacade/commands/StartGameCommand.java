package serverfacade.commands;

import commandresults.CommandResult;
import serverfacade.ServerFacade;

public class StartGameCommand extends StartGameCommandData implements ICommand{

    public StartGameCommand(String gameName){
        super(gameName);
    }

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.startGame(gameName, username);
    }
}
