package serverfacade.commands;

import utils.Utils;

public class LeaveGameCommand extends Command{
    protected String gameName;

    public LeaveGameCommand(String username, String gameName){
        super.setType(Utils.LEAVE_TYPE);
        this.username = username;
        this.gameName = gameName;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
