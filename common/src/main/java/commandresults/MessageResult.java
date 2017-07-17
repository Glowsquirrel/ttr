package commandresults;

import utils.Utils;

public class MessageResult extends CommandResult
{
    public MessageResult(String message)
    {
        super.type = Utils.MESSAGE_TYPE;
        super.message = message;
    }

    public MessageResult(String username, String message)
    {
        super.type = Utils.MESSAGE_TYPE;
        super.username = username;
        super.message = message;
    }

}
