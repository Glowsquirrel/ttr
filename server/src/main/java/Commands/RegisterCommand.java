package Commands;

import CommandResults.CommandResult;

public class RegisterCommand extends RegisterCommandData implements ICommand{
    public RegisterCommand(String username, String password) {
        super(username, password);
    }

    public CommandResult execute(){
        //TODO link to a real database

        return null;
    }
}
