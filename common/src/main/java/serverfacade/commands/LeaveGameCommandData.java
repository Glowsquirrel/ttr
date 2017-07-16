package serverfacade.commands;

public class LeaveGameCommandData extends Command{
    protected String username;
    protected String gameName;

    public LeaveGameCommandData(String username, String gameName){
        super.setType("leavegame");
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
