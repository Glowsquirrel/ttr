package Commands;

import CommandResults.CommandResult;
import Interfaces.ICommand;

public class LoginCommand extends LoginCommandData implements ICommand {

    public LoginCommand(String username, String password) {
        super(username, password);
    }

    public CommandResult execute(){
        return null;
    }
}
