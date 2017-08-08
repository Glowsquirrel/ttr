package serverfacade.commands.menu;

import commands.Command;
import interfaces.ICommandX;
import serverfacade.ServerFacade;
import utils.Utils;

public class ClearDatabaseCommandX extends Command implements ICommandX {

    public ClearDatabaseCommandX(String username) {
        super.username = username;
        super.setType(Utils.CLEAR_TYPE);
    }

    @Override
    public boolean execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.clearDatabase(username);
    }

    //DO NOT STORE DESTRUCTIVE COMMANDS IN THE DATABASE
    @Override
    public void addToDatabase() {}
}
