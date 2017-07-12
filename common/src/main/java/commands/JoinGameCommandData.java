package commands;

public class JoinGameCommandData extends Command{
    protected String username;
    protected String gameName;

    public JoinGameCommandData(String username, String gameName){
        super.setType("joingame");
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
