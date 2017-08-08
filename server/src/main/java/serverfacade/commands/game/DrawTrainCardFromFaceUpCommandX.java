package serverfacade.commands.game;

import commands.game.DrawTrainCardFromFaceUpCommand;
import interfaces.ICommandX;
import serverfacade.ServerFacade;

public class DrawTrainCardFromFaceUpCommandX extends DrawTrainCardFromFaceUpCommand implements ICommandX {
    private ServerFacade serverFacade;

    @Override
    public boolean execute() {
        serverFacade = new ServerFacade();
        return serverFacade.drawTrainCardFromFaceUp(username, gameName, index);
    }

    @Override
    public void addToDatabase() {
        serverFacade.addCommandToDatabase(super.gameName, this);
    }
}
