package commands;

public class CreateGameCommandData extends Command{
    protected String username;
    protected String gameName;
    protected int numPlayers;

    public CreateGameCommandData(String username, String gameName, int numPlayers){
        super.setType("creategame");
        this.username = username;
        this.gameName = gameName;
        this.numPlayers = numPlayers;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
