package commandresults;

import utils.Utils;

public class FailedResult extends CommandResult
{
    public FailedResult(String message)
    {
        super.setType(Utils.FAILED_TYPE);
        super.message = message;
    }
}
