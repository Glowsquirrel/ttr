package results.game;

import results.Result;
import utils.Utils;

public class DrawTrainCardFromFaceUpResult extends Result {
    protected int trainCard;

    protected DrawTrainCardFromFaceUpResult(){}
    public DrawTrainCardFromFaceUpResult(String username, int trainCard){
        super.type = Utils.DRAW_TRAIN_CARD_FACEUP_TYPE;
        super.username = username;
        this.trainCard = trainCard;
    }

}
