package serverfacade.commands.menu;

import commands.Command;
import interfaces.ICommand;
import serverfacade.ServerFacade;
import utils.Utils;

public class ClearDatabaseCommandX extends Command implements ICommand {

    public ClearDatabaseCommandX(String username) {
        super.username = username;
        super.setType(Utils.CLEAR_TYPE);
    }

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.clearDatabase(username);
    }
}
