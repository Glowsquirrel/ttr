package database.FileDatabase;

import java.util.List;
import java.util.Map;

import commands.Command;

/**
 * Created by Rachael on 8/9/2017.
 */

public class CommandDao {
    public boolean saveCommandToDatabase(String gameName, Command nextCommand)
    {
        return true;
    }
    public Map<String, List<Command>> loadOutstandingCommandsFromDatabase()
    {
        Map<String, List<Command>> commands=null;
        return commands;
    }
}
