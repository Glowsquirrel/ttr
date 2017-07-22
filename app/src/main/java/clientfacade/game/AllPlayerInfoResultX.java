package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.game.AllPlayerInfoResult;

public class AllPlayerInfoResultX extends AllPlayerInfoResult implements ICommand {

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.updateAllPlayerInformation(super.usernames, super.numRoutesOwned, super.numTrainCardsHeld,
                super.numDestCardsHeld, super.numTrainCars, super.score);
    }
}
