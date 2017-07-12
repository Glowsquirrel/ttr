package serverfacade.commands;

import commandresults.CommandResult;
import serverfacade.ServerFacade;

public class LeaveGameCommand extends LeaveGameCommandData implements ICommand{

    public LeaveGameCommand(String username, String gameName){
        super(username, gameName);
    }

    @Override
    public CommandResult execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.leaveGame(username, gameName);
    }
}
