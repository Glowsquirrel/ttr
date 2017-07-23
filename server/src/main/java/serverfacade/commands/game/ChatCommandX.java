package serverfacade.commands.game;

import commands.game.ChatCommand;
import interfaces.ICommand;
import serverfacade.ServerFacade;

public class ChatCommandX extends ChatCommand implements ICommand{

    @Override
    public void execute() {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.sendChatMessage(username, gameName, message);
    }
}
