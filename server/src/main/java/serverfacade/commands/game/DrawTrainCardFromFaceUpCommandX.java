package serverfacade.commands.game;

import commands.game.DrawTrainCardFromFaceUpCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class DrawTrainCardFromFaceUpCommandX extends DrawTrainCardFromFaceUpCommand implements ICommand{

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.drawTrainCardFromFaceUp(username, gameName, index);
    }
}
