package database;

<<<<<<< HEAD:server/src/main/java/database/DatabaseFactory.java
import database.FileDatabase.FileDatabase;

public class DatabaseFactory {
=======
public class FakeDatabaseFactory implements AbstractFactory{
>>>>>>> dc93b48788e5d95ab9b3bfb13bf604592e5d5727:server/src/main/java/database/FakeDatabaseFactory.java

    public FakeDatabase getDatabase(){
        return new FakeDatabase();
        /*
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
        */
    }

}
