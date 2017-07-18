package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;

public class LeaveGameCommandX extends LeaveGameCommand implements ICommand {

    public LeaveGameCommandX(String username, String gameName){
        super(username, gameName);
    }

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        if (username != null && gameName != null)
            serverFacade.leaveGame(username, gameName);
    }
}
