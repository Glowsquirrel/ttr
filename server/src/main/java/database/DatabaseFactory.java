package database;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import database.FileDatabase.FileDatabase;
import database.RelationalDatabase.RDBDAO;

public class DatabaseFactory {

    public static IDatabase getDatabase(String databaseType, int commandsUntilSave){

        String jarPath = getJarPath(databaseType, commandsUntilSave);

        File jarFile = new File(jarPath);
        ClassLoader classLoader;
        try {
            classLoader = new URLClassLoader(new URL[] { jarFile.toURI().toURL() });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        ServiceLoader<IDatabase> serviceLoader = ServiceLoader.load(IDatabase.class, classLoader);

        IDatabase database = null;

        try {
            for (IDatabase factory : serviceLoader) {
                database = factory;
            }
        } catch (ServiceConfigurationError e) {
            e.printStackTrace();
            database = null;
        }
        return database;

    }

    private static String getJarPath(String databaseType, int commandsUntilSave) {
        return null;
    }
}
