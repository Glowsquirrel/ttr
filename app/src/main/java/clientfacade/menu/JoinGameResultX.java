package clientfacade.menu;

import clientfacade.ClientFacade;
import results.menu.JoinGameResult;
import interfaces.ICommand;

public class JoinGameResultX extends JoinGameResult implements ICommand
{
    @Override
    public void execute()
    {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.joinGame(super.username, super.gameName);
    }
}
