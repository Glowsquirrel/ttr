package commands.menu;

import commands.Command;
import utils.Utils;

public class JoinGameCommand extends Command {
    protected String gameName;

    public JoinGameCommand(String username, String gameName){
        super.setType(Utils.JOIN_TYPE);
        this.username = username;
        this.gameName = gameName;
    }
    public String getUsername() {
        return username;
    }

    public String getGameName() {
        return gameName;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
