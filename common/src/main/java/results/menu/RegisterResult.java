package results.menu;

import results.Result;
import utils.Utils;

public class RegisterResult extends Result {
    protected String message;

    protected RegisterResult(){}
    public RegisterResult(String username, String message){
        super.type = Utils.REGISTER_TYPE;
        super.username = username;
        this.message = message;
    }
}
