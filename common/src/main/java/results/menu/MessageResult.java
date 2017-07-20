package results.menu;

import results.Result;
import utils.Utils;

public class MessageResult extends Result {
    protected String message;

    protected MessageResult(){}
    public MessageResult(String username, String message){
        super.type = Utils.MESSAGE_TYPE;
        super.username = username;
        this.message = message;
    }

}
