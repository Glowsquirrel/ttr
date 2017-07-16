package serverfacade.commands;

public class StartGameCommandData extends Command{
    protected String gameName;

    public StartGameCommandData(String gameName){
        this.gameName = gameName;
        super.setType("startgame");
    }
    public String getGameName() {
        return gameName;
    }

    public void setUsername(String username) {
        this.gameName = username;
    }
}
