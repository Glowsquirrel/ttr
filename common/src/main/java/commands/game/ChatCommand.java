package commands.game;

import commands.Command;
import utils.Utils;

public class ChatCommand extends Command {
    protected String gameName;
    protected String message;

    protected ChatCommand(){}
    public ChatCommand(String username, String gameName, String message){
        super.type = Utils.CHAT_TYPE;
        super.username = username;
        this.gameName = gameName;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
