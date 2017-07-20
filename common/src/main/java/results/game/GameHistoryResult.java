package results.game;

import results.Result;
import utils.Utils;

public class GameHistoryResult extends Result{
    protected String message;

    protected GameHistoryResult(){}
    public GameHistoryResult(String message){
        super.type = Utils.GAME_HISTORY_TYPE;
        this.message = message;
    }
}
