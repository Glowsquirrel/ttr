package serverfacade.commands;

public class PollGamesCommandData extends Command {

    public PollGamesCommandData(String username) {
        super.setType("pollgames");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
