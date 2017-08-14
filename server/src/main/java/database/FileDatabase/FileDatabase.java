package database.FileDatabase;

import java.io.File;
import java.nio.file.Paths;
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
    CommandDao commandDao;
    GameDao gameDao;
    UserDao userDao;
    int commandNum=0;
    String mURLPostfix;


    public int getCommandNum() {
        return commandNum;
    }

    public FileDatabase(int commandNum) {
        String pathToDB = Paths.get(".").toAbsolutePath().normalize().toString(); // get working directory
        this.commandNum=commandNum;
        this.commandNum = commandNum;
        //mURLPostfix = "/server/database/RelationalDatabase/ttr-rdb.sqlite";
        mURLPostfix = File.separator +"server"+ File.separator +"src"+ File.separator+"main/java"+ File.separator+"database"+ File.separator+"FileDatabase"+File.separator;
        commandDao=new CommandDao(pathToDB+mURLPostfix+"commands.ser.txt");
        gameDao=new GameDao(pathToDB+mURLPostfix+"games.ser.txt");
        userDao=new UserDao(pathToDB+mURLPostfix+"users.ser.txt");
    }

    public void setCommandNum(int commandNum) {
        commandDao.setCommandNum(commandNum);

    }

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

    @Override
    public boolean clearDatabase() {
        gameDao.clearDatabase();
        commandDao.clearDatabase();
        userDao.clearDatabase();
        return true;
    }

}
