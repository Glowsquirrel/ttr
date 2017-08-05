package clientfacade.game;

import clientfacade.ClientFacade;
import interfaces.ICommand;
import results.game.EndGameResult;


public class EndGameResultX extends EndGameResult implements ICommand{

    @Override
    public void execute(){
        ClientFacade clientFacade = new ClientFacade();
        clientFacade.endGame(players, numRoutesClaimed, pointsFromRoutes, destCardPtsAdded,
                destCardPtsSubtracted, totalPoints, ownsLongestRoute);
    }
}
