package serverfacade.commands.menu;


import commands.menu.LogoutCommand;
import interfaces.ICommandX;
import serverfacade.ServerFacade;

//experimental class
public class LogoutCommandX extends LogoutCommand implements ICommandX {

    public LogoutCommandX(String username){
        super(username);
    }

    @Override
    public boolean execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.logout(username);
    }

    //This does not need to be saved.
    @Override
    public void addToDatabase() {}
}
