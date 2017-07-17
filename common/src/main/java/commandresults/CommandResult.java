package commandresults;

public class CommandResult
{
    protected String type;
    protected String username;
    protected String gameName;
    protected String message;

    public CommandResult(){}
    public CommandResult(String type, String username, String gameName, String message)
    {
        this.type = type;
        this.username = username;
        this.gameName = gameName;
        this.message = message;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }
}
