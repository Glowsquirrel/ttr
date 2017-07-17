package serverfacade.commands;

import utils.Utils;

public class StartGameCommandData extends Command{
    protected String gameName;

    protected StartGameCommandData(){}
    public StartGameCommandData(String username, String gameName){
        super.setType(Utils.START_TYPE);
        super.username = username;
        this.gameName = gameName;
    }
    public String getGameName() {
        return gameName;
    }

    public void setUsername(String username) {
        this.gameName = username;
    }
}
