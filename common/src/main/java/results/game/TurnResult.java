package results.game;

import results.Result;
import utils.Utils;

/**
 * Created by sjrme on 7/29/17.
 */

public class TurnResult extends Result{

    protected TurnResult(){}
    public TurnResult(String username){
        super.type = Utils.TURN_TYPE;
        super.username = username;
    }
}
