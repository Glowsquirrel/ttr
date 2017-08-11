package database;

import database.FileDatabase.FileDatabase;
import database.FileDatabase.SQLDatabase;

/**
 * Created by sjrme on 8/11/2017.
 */

public class SQLFactory implements AbstractFactory {

    @Override
    public SQLDatabase getDatabase() {
        return new SQLDatabase();
    }

}
