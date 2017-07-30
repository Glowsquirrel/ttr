package results.game;

import results.Result;
import utils.Utils;

/**
 * Created by sjrme on 7/29/17.
 */

public class FinalRoundResult extends Result {
    protected String playerToEndOn;

    protected FinalRoundResult(){}
    public FinalRoundResult(String playerToEndOn){
        super.type = Utils.FINAL_ROUND_TYPE;
        this.playerToEndOn = playerToEndOn;
    }
}


