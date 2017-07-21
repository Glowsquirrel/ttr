package results.game;

import results.Result;
import utils.Utils;

public class RejoinResult extends Result {
    protected String gameName;

    protected RejoinResult(){}
    public RejoinResult(String username, String gameName){
        super.type = Utils.REJOIN_TYPE;
        super.username = username;
        this.gameName = gameName;
    }
}
