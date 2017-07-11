package commands;

import commandresults.CommandResult;
import commandresults.LoginResultData;
import commandresults.PollResultData;

/**
 * Created by shunsambongi on 7/11/17.
 */

public class PollCommand extends PollCommandData implements ICommand {
    public PollCommand(String username) {
        super(username);
    }

    @Override
    public CommandResult execute() {
        CommandResult commandResult = new PollResultData();
        commandResult.setType("poll");
        commandResult.setSuccess(true);
        commandResult.setErrorMessage(null);
        return commandResult;
    }
}
