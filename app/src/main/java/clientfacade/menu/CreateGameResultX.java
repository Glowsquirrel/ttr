package clientfacade.menu;

import clientfacade.ClientFacade;
import results.menu.CreateGameResult;
import interfaces.ICommand;

public class CreateGameResultX extends CreateGameResult implements ICommand {

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.createGame(super.username, super.gameName);
    }
}
