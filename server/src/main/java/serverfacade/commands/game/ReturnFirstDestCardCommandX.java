package serverfacade.commands.game;

import commands.game.ReturnFirstDestCardCommand;
import interfaces.ICommandX;
import serverfacade.ServerFacade;

public class ReturnFirstDestCardCommandX extends ReturnFirstDestCardCommand implements ICommandX {
    private ServerFacade serverFacade;

    @Override
    public boolean execute() {
        serverFacade = new ServerFacade();
        return serverFacade.returnDestCards(super.username, super.gameName, super.destCard);
    }

    @Override
    public void addToDatabase() {
        serverFacade.addCommandToDatabase(super.gameName, this);
    }
}
