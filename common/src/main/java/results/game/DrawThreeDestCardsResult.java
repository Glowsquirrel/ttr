package results.game;

import java.util.List;

import results.Result;
import utils.Utils;

public class DrawThreeDestCardsResult extends Result {
    protected List<Integer> destCards;

    protected DrawThreeDestCardsResult(){}
    public DrawThreeDestCardsResult(String username, List<Integer> destCards){
        super.type = Utils.DRAW_DEST_CARDS_TYPE;
        super.username = username;
        this.destCards = destCards;
    }
}
