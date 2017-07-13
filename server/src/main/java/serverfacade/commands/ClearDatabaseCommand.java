package serverfacade.commands;

import commandresults.CommandResult;
import serverfacade.ServerFacade;

public class ClearDatabaseCommand extends Command implements ICommand {

    public ClearDatabaseCommand(){
        super.setType("cleardb");
    }
    @Override
    public CommandResult execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.clearDatabase();
    }
}
