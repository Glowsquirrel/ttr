package database;

import database.FileDatabase.FileDatabase;
import database.RelationalDatabase.RDBDAO;

public class DatabaseFactory {

    public static IDatabase getDatabase(String databaseType, int commandsUntilSave){

        switch (databaseType){
            case "nate":
                return new RDBDAO(commandsUntilSave);
            case "rachael":
                FileDatabase fb=new FileDatabase();
                fb.setCommandNum(commandsUntilSave);
                return fb;
            default:
                return new FakeDatabase(commandsUntilSave);
        }

    }

}
