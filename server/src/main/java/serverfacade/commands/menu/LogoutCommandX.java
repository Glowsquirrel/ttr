package serverfacade.commands.menu;


import commands.menu.LogoutCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class LogoutCommandX extends LogoutCommand implements ICommand {

    public LogoutCommandX(String username){
        super(username);
    }

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.logout(username);
    }
}
