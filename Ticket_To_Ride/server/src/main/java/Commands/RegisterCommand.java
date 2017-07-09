package Commands;

import CommandResults.CommandResult;
import Interfaces.ICommand;

public class RegisterCommand extends RegisterCommandData implements ICommand{
    public RegisterCommand(String username, String password) {
        super(username, password);
    }

    public CommandResult execute(){
        return null;
    }
}
