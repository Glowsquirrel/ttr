package results.game;

import java.util.List;

import results.Result;
import utils.Utils;

public class ReturnDestCardsResult extends Result {
    protected List<Integer> destCards;

    protected ReturnDestCardsResult(){}
    public ReturnDestCardsResult(String username, List<Integer> destCards){
        super.type = Utils.RETURN_DEST_CARDS_TYPE;
        super.username = username;
        this.destCards = destCards;
    }
}
