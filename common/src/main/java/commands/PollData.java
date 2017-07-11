package commands;

/**
 * Created by shunsambongi on 7/11/17.
 */

public class PollData extends Command {

    private String username;

    public PollData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
