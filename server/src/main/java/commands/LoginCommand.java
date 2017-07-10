package commands;

import commandresults.CommandResult;
import commandresults.LoginResultData;

public class LoginCommand extends LoginCommandData implements ICommand {

    public LoginCommand(String username, String password) {
        super(username, password);
    }

    public CommandResult execute(){
        //TODO link to a real database by calling ClientProxy methods

        CommandResult commandResult = new LoginResultData();
        commandResult.setType("login");
        commandResult.setSuccess(true);
        commandResult.setErrorMessage(null);
        return commandResult;
    }
}
