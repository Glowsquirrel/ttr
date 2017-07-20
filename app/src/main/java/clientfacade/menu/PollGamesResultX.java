package clientfacade.menu;

import clientfacade.ClientFacade;
import results.menu.PollGamesResult;
import interfaces.ICommand;

public class PollGamesResultX extends PollGamesResult implements ICommand{

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.updateSingleUserGameList(super.username, super.unstartedGameList, super.runningGameList);
    }
}
