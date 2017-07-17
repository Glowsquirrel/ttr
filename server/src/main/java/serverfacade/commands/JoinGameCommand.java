package serverfacade.commands;

import interfaces.ICommand;
import serverfacade.ServerFacade;

public class JoinGameCommand extends JoinGameCommandData implements ICommand {

    public JoinGameCommand(String username, String gameName){
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
