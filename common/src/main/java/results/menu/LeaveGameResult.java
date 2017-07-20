package results.menu;

import results.Result;
import utils.Utils;

public class LeaveGameResult extends Result {
    protected String gameName;

    protected LeaveGameResult(){}
    public LeaveGameResult(String username, String gameName){
        super.type = Utils.LEAVE_TYPE;
        super.username = username;
        this.gameName = gameName;
    }
}
