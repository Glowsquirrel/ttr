package database;

import database.FileDatabase.FileDatabase;
import database.RelationalDatabase.RDBDAO;

public class DatabaseFactory {

    public static IDatabase getDatabase(String databaseType, int commandsUntilSave){
        IDatabase iDatabase;
        switch (databaseType){
            case "nate":
                return new RDBDAO(commandsUntilSave);
            case "rachael":
                FileDatabase fb=new FileDatabase(commandsUntilSave);
               /* Class<?> c = null;
                try {
                    c = Class.forName("plugin.FileDatabase");
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
                try {
                    iDatabase = (IDatabase)c.newInstance();
                }
                catch (InstantiationException e) {
                    e.printStackTrace();
                    return null;
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
                return iDatabase;*/
                return fb;
            default:
                return new FakeDatabase(commandsUntilSave);
        }

    }

}
