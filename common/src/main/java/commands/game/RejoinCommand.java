package commands.game;

import commands.Command;
import utils.Utils;

public class RejoinCommand extends Command {
    protected String gameName;

    protected RejoinCommand(){}
    public RejoinCommand(String username, String gameName){
        super.type = Utils.REJOIN_TYPE;
        super.username = username;
        this.gameName = gameName;
    }
}
