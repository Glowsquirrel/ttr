package results.game;

import results.Result;
import utils.Utils;

public class GameHistoryResult extends Result{
    protected String message;
    protected int numTrainCars;
    protected int numTrainCardsHeld;
    protected int numDestCardsHeld;
    protected int numRoutesOwned;
    protected int score;
    protected int claimedRouteNumber;
    protected int trainCardDeckSize;

    protected GameHistoryResult(){}
    public GameHistoryResult(String username, String message, int numTrainCars, int numTrainCardsHeld,
                             int numDestCardsHeld, int numRoutesOwned, int score, int claimedRouteNumber, int trainCardDeckSize){
        super.type = Utils.GAME_HISTORY_TYPE;
        super.username = username;
        this.message = message;
        this.numTrainCars = numTrainCars;
        this.numTrainCardsHeld = numTrainCardsHeld;
        this.numDestCardsHeld = numDestCardsHeld;
        this.numRoutesOwned = numRoutesOwned;
        this.score = score;
        this.claimedRouteNumber = claimedRouteNumber;
        this.trainCardDeckSize = trainCardDeckSize;
    }
}
