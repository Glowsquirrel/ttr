package serverfacade.commands.game;

import commands.game.RejoinCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class RejoinCommandX extends RejoinCommand implements ICommand {

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.rejoinGame(super.username, super.gameName);
    }
}
