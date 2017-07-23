package serverfacade.commands.game;

import commands.game.DrawTrainCardFromDeckCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class DrawTrainCardFromDeckCommandX extends DrawTrainCardFromDeckCommand implements ICommand {

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.drawTrainCardFromDeck(username, gameName);
    }
}
