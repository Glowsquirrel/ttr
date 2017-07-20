package results.menu;


import results.Result;
import utils.Utils;

public class JoinGameResult extends Result {
    protected String gameName;

    protected JoinGameResult(){}
    public JoinGameResult(String username, String gameName){
        super.type = Utils.JOIN_TYPE;
        super.username = username;
        this.gameName = gameName;
    }
}
