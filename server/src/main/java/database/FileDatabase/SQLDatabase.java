package database.FileDatabase;


import java.util.List;
import java.util.Map;
import java.util.Set;

import commands.Command;
import database.IDatabase;
import model.StartedGame;
import model.User;

public class SQLDatabase implements IDatabase {

    @Override
    public boolean clearDatabase(){
        return false;
    }

    @Override
    public boolean saveCommandToDatabase(String gameName, Command nextCommand){
        return false;
    }

    @Override
    public void saveNewStartedGameToDatabase(StartedGame myGame){

    }

    @Override
    public void updateStartedGameInDatabase(StartedGame myGame){

    }

    @Override
    public void saveNewUserToDatabase(User myNewUser){
    }

    @Override
    public Set<User> loadUsersFromDatabase(){
        return null;
    }

    @Override
    public Map<String, StartedGame> loadStartedGamesFromDatabase() {
        return null;
    }

    @Override
    public Map<String, List<Command>> loadOutstandingCommandsFromDatabase(){
        return null;
    }


}
