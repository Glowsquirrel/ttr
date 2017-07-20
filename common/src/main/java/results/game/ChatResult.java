package results.game;

import results.Result;
import utils.Utils;

public class ChatResult extends Result {
    protected String message;

    protected ChatResult(){}
    public ChatResult(String username, String message){
        super.type = Utils.CHAT_TYPE;
        super.username = username;
        this.message = message;
    }
}
