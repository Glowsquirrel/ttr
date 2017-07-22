package commands.menu;

import commands.Command;
import utils.Utils;

public class CreateGameCommand extends Command
{
    protected String gameName;
    protected int numPlayers;

    public CreateGameCommand(String username, String gameName, int numPlayers)
    {
        super.setType(Utils.CREATE_TYPE);
        this.username = username;
        this.gameName = gameName;
        this.numPlayers = numPlayers;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}