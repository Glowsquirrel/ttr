package commands.menu;

import commands.Command;
import utils.Utils;

public class LogoutCommand extends Command{

    protected LogoutCommand(){}
    public LogoutCommand(String username){
        super.type = Utils.LOGOUT_TYPE;
        super.username = username;
    }
}
