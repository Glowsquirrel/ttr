package results.game;

import results.Result;
import utils.Utils;

public class ReturnFirstDestCardResult extends Result {
    protected int cardReturned;

    protected ReturnFirstDestCardResult(){}
    public ReturnFirstDestCardResult(String username, int cardReturned){
        super.type = Utils.RETURN_FIRST_DEST_CARD_TYPE;
        super.username = username;
        this.cardReturned = cardReturned;
    }
}
