package serverfacade.commands.game;

import commands.game.DrawTrainCardFromDeckCommand;
import interfaces.ICommandX;
import serverfacade.ServerFacade;

public class DrawTrainCardFromDeckCommandX extends DrawTrainCardFromDeckCommand implements ICommandX {
    private ServerFacade serverFacade;

    @Override
    public boolean execute() {
        serverFacade = new ServerFacade();
        return serverFacade.drawTrainCardFromDeck(username, gameName);
    }

    @Override
    public void addToDatabase() {
        serverFacade.addCommandToDatabase(super.gameName, this);
    }
}
