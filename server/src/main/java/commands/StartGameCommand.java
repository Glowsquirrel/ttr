package commands;

import clientproxy.ClientProxy;
import commandresults.CommandResult;
import serverfacade.ServerFacade;

/**
 * Created by glowsquirrel on 7/11/17.
 */

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
