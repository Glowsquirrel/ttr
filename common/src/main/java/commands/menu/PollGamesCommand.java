package commands.menu;

import commands.Command;
import utils.Utils;

public class PollGamesCommand extends Command {

    public PollGamesCommand(String username) {
        super.setType(Utils.POLL_TYPE);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
