package serverfacade.commands.menu;

import commands.menu.JoinGameCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class JoinGameCommandX extends JoinGameCommand implements ICommand {

    public JoinGameCommandX(String username, String gameName){
        super(username, gameName);
    }
    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.joinGame(username, gameName);
    }

    public String getGameName(){
        return super.getGameName();
    }

}
