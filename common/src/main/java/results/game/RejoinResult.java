package results.game;

import java.io.Serializable;

import results.Result;
import utils.Utils;

public class RejoinResult extends Result  implements Serializable {
    protected String gameName;

    protected RejoinResult(){}
    public RejoinResult(String username, String gameName){
        super.type = Utils.REJOIN_TYPE;
        super.username = username;
        this.gameName = gameName;
    }
}
