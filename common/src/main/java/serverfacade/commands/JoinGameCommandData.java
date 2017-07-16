package serverfacade.commands;

public class JoinGameCommandData extends Command{
    protected String username;
    protected String gameName;

    public JoinGameCommandData(String username, String gameName){
        super.setType("joingame");
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
