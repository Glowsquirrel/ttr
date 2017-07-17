package clientfacade.commands;

import clientfacade.ClientFacade;
import commandresults.CommandResult;
import interfaces.ICommand;

public class StartGameResultX extends CommandResult implements ICommand
{
    @Override
    public void execute()
    {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.startGame(super.username, super.gameName, super.message);
    }
}
