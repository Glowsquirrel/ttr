package serverfacade.commands;

import commandresults.CommandResult;
import serverfacade.ServerFacade;

public class JoinGameCommand extends JoinGameCommandData implements ICommand {

    public JoinGameCommand(String username, String gameName){
        super(username, gameName);
    }
    @Override
    public CommandResult execute() {
        ServerFacade serverFacade = new ServerFacade();
        return serverFacade.joinGame(username, gameName);
    }

    public String getGameName(){
        return super.getGameName();
    }

}
