package serverfacade.commands;

import utils.Utils;

public class PollGamesCommandData extends Command {

    public PollGamesCommandData(String username) {
        super.setType(Utils.POLL_TYPE);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
