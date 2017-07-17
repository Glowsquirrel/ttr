package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;
import utils.Utils;

public class ClearDatabaseCommand extends Command implements ICommand
{
    public ClearDatabaseCommand(String username)
    {
        super.username = username;
        super.setType(Utils.CLEAR_TYPE);
    }

    @Override
    public void execute()
    {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.clearDatabase(username);
    }
}
