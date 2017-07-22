package results.game;

import java.util.List;

import results.Result;
import utils.Utils;


/**
 * Created by sjrme on 7/21/17.
 */

public class ReplaceFaceUpCardsResult  extends Result {

    protected List<Integer> trainCards;

    protected ReplaceFaceUpCardsResult(){}

    public ReplaceFaceUpCardsResult(List<Integer> trainCards) {
        super.type = Utils.REPLACE_ALL_FACEUP_TYPE;
        this.trainCards = trainCards;
    }
}
