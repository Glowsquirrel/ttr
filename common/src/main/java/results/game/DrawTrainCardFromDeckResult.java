package results.game;

import results.Result;
import utils.Utils;

public class DrawTrainCardFromDeckResult extends Result {
    protected int trainCard;

    protected DrawTrainCardFromDeckResult(){}
    public DrawTrainCardFromDeckResult(String username, int trainCard){
        super.type = Utils.DRAW_TRAIN_CARD_DECK_TYPE;
        super.username = username;
        this.trainCard = trainCard;
    }
}
