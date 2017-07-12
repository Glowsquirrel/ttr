package serverfacade.commands;

/**
 * Created by shunsambongi on 7/11/17.
 */

public class PollGamesCommandData extends Command {

    protected String username;

    public PollGamesCommandData(String username) {
        super.setType("pollgames");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
