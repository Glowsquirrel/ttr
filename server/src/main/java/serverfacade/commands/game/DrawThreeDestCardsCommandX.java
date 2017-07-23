package serverfacade.commands.game;

import commands.game.DrawThreeDestCardsCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class DrawThreeDestCardsCommandX extends DrawThreeDestCardsCommand implements ICommand{

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.drawThreeDestCards(username, gameName);
    }
}
