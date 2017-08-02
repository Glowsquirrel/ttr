package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.game.GameHistoryResult;

public class GameHistoryX extends GameHistoryResult implements ICommand {

    @Override
    public void execute() {
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.addHistory(super.username, super.message, super.numTrainCars, super.numTrainCardsHeld,
                super.numDestCardsHeld, super.numRoutesOwned, super.score, super.claimedRouteNumber,
                super.trainCardDeckSize, super.destCardDeckSize, super.faceUpIndex);
    }
}
