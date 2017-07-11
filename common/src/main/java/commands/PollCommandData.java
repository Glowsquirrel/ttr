package commands;

/**
 * Created by shunsambongi on 7/11/17.
 */

public class PollCommandData extends Command {

    private String username;

    public PollCommandData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
