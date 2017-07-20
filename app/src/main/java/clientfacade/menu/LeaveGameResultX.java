package clientfacade.menu;

import clientfacade.ClientFacade;
import results.menu.LeaveGameResult;
import interfaces.ICommand;

public class LeaveGameResultX extends LeaveGameResult implements ICommand
{
    @Override
    public void execute()
    {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.leaveGame(super.username, super.gameName);
    }
}
