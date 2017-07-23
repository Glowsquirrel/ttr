package serverfacade.commands.game;

import commands.game.ReturnDestCardsCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class ReturnDestCardsCommandX extends ReturnDestCardsCommand implements ICommand{

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.returnDestCards(username, gameName, destCard);
    }
}
