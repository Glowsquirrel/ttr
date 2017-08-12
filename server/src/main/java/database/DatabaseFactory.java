package database;

import database.FileDatabase.FileDatabase;
import database.RelationalDatabase.RDBDAO;

public class DatabaseFactory {

    public static IDatabase getDatabase(String databaseType, int commandsUntilSave){

        switch (databaseType){
            case "nate":
                return new RDBDAO(commandsUntilSave);
            case "rachael":
                return new FileDatabase();
            default:
                return new FakeDatabase(commandsUntilSave);
        }

    }

}
