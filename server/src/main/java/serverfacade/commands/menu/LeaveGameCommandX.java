package serverfacade.commands.menu;

import commands.menu.LeaveGameCommand;
import interfaces.ICommandX;
import serverfacade.ServerFacade;

public class LeaveGameCommandX extends LeaveGameCommand implements ICommandX {

    public LeaveGameCommandX(String username, String gameName){
        super(username, gameName);
    }

    @Override
    public boolean execute() {
        ServerFacade serverFacade = new ServerFacade();
        return username != null && gameName != null && serverFacade.leaveGame(username, gameName);
    }

    //This does not need to be saved.
    @Override
    public void addToDatabase() {}
}
