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
    CommandDao commandDao=new CommandDao();
    GameDao gameDao=new GameDao();
    UserDao userDao=new UserDao();
    @Override
    public boolean saveCommandToDatabase(String gameName, Command nextCommand)
    {
        return commandDao.saveCommandToDatabase(gameName,nextCommand);
    }
    @Override
    public void saveNewStartedGameToDatabase(StartedGame myGame)
    {
        gameDao.saveNewStartedGameToDatabase(myGame);
    }
    @Override
    public void updateStartedGameInDatabase(StartedGame myGame)
    {
        gameDao.updateStartedGameInDatabase(myGame);
    }
    @Override
    public void saveNewUserToDatabase(User myNewUser)
    {
        userDao.saveNewUserToDatabase(myNewUser);
    }
    @Override
    public Set<User> loadUsersFromDatabase()
    {
       return userDao.loadUsersFromDatabase();
    }
    @Override
    public Map<String, StartedGame> loadStartedGamesFromDatabase()
    {
        return gameDao.loadStartedGamesFromDatabase();
    }
    @Override
    public Map<String, List<Command>> loadOutstandingCommandsFromDatabase()
    {
        return commandDao.loadOutstandingCommandsFromDatabase();
    }
<<<<<<< HEAD
<<<<<<< HEAD
    @Override
    public boolean clearDatabase()
    {
        gameDao.clearDatabase();
        commandDao.clearDatabase();
        userDao.clearDatabase();
        return true;
=======
=======
>>>>>>> dc93b48788e5d95ab9b3bfb13bf604592e5d5727

    @Override
    public boolean clearDatabase(){
        return false;
<<<<<<< HEAD
>>>>>>> dc93b48788e5d95ab9b3bfb13bf604592e5d5727
=======
>>>>>>> dc93b48788e5d95ab9b3bfb13bf604592e5d5727
    }

}
