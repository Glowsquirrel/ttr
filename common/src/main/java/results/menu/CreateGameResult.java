package results.menu;

import results.Result;
import utils.Utils;

public class CreateGameResult extends Result {
    protected String gameName;

    protected CreateGameResult(){}
    public CreateGameResult(String username, String gameName){
        super.type = Utils.CREATE_TYPE;
        super.username = username;
        this.gameName = gameName;
    }
}
