package Commands;

import CommandResults.CommandResult;
import CommandResults.LoginResultData;
import Interfaces.ICommand;

public class LoginCommand extends LoginCommandData implements ICommand {

    public LoginCommand(String username, String password) {
        super(username, password);
    }

    public CommandResult execute(){
        //TODO link to a real database

        CommandResult commandResult = new LoginResultData();
        commandResult.setType("login");
        commandResult.setSuccess(true);
        commandResult.setErrorMessage(null);
        return commandResult;
    }
}
