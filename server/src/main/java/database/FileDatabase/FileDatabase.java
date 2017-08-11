package database.FileDatabase;

import java.util.List;
import java.util.Map;
import java.util.Set;

import commands.Command;
import database.IDatabase;
import model.StartedGame;
import model.User;

/**
 * Created by Rachael on 8/9/2017.
 */

public class FileDatabase implements IDatabase {
    @Override
    public boolean saveCommandToDatabase(String gameName, Command nextCommand)
    {
        return true;
    }
    @Override
    public void saveNewStartedGameToDatabase(StartedGame myGame)
    {

    }
    @Override
    public void updateStartedGameInDatabase(StartedGame myGame)
    {

    }
    @Override
    public void saveNewUserToDatabase(User myNewUser)
    {

    }
    @Override
    public Set<User> loadUsersFromDatabase()
    {
        Set<User> users=null;
        return users;
    }
    @Override
    public Map<String, StartedGame> loadStartedGamesFromDatabase()
    {
        Map<String, StartedGame> games=null;
        return games;
    }
    @Override
    public Map<String, List<Command>> loadOutstandingCommandsFromDatabase()
    {
        Map<String, List<Command>> commands=null;
        return commands;
    }

    @Override
    public boolean clearDatabase(){
        return false;
    }

}
