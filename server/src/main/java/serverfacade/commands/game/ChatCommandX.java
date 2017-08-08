package serverfacade.commands.game;

import commands.game.ChatCommand;
import interfaces.ICommandX;
import serverfacade.ServerFacade;

public class ChatCommandX extends ChatCommand implements ICommandX {
    private ServerFacade serverFacade;

    @Override
    public boolean execute() {
        serverFacade = new ServerFacade();
        return serverFacade.sendChatMessage(username, gameName, message);
    }

    @Override
    public void addToDatabase() {
        serverFacade.addCommandToDatabase(super.gameName, this);
    }
}
