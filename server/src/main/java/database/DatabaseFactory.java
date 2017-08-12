package database;

import database.FileDatabase.FileDatabase;

public class DatabaseFactory {

    public static IDatabase createDatabase(String databaseType, int commandsUntilSave){
        switch (databaseType){
            case "database":
                return new FakeDatabase(commandsUntilSave);
            case "natedatabase":
                //return new NateDatabase(commandsUntilSave);
                //TODO: Nate: Put your database here.
                return null;
            case "rachaeldatabase":
                //return new RachaelDatabase(commandsUntilSave);
                return new FileDatabase();
            default:
                return null;
        }
    }

}
