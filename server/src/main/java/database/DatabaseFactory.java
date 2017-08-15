package database;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
<<<<<<< HEAD
import java.nio.file.Paths;
=======
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import database.FileDatabase.FileDatabase;
import database.RelationalDatabase.RDBDAO;
>>>>>>> 9d80d34f49c6edc0b314ed7fe83fef188fc237c1

public class DatabaseFactory {

    public static IDatabase getDatabase(String databaseType, int commandsUntilSave){
<<<<<<< HEAD
        IDatabase iDatabase=null;
        String mURLPostfix=null;
        String className=null;
        switch (databaseType){
            case "nate":
                mURLPostfix=File.separator + "server" + File.separator + "src" + File.separator + "main" +
                        File.separator + "java" + File.separator+ "PersistanceJar" +
                        File.separator + "nate.jar";
                className="database.RelationalDatabase.RDBDAO";
                break;
               // return new RDBDAO(commandsUntilSave);
            case "rachael":
                mURLPostfix=File.separator + "server" + File.separator + "src" + File.separator + "main" +
                        File.separator + "java" + File.separator+ "PersistanceJar" + File.separator +
                        "rachael.jar";
                className="database.FileDatabase.FileDatabase";

        }
        String pathToDB = Paths.get(".").toAbsolutePath().normalize().toString(); // get working directory
        pathToDB += (mURLPostfix);
        File file  = new File(pathToDB);

        URL url=null;
        URL[] urls=null;
        try {
            url = file.toURL();
            urls = new URL[]{url};
        }catch(MalformedURLException ex)
        {
            ex.printStackTrace();
            return null;
        }

        ClassLoader cl = new URLClassLoader(urls);
        // cl.g
        Class<?> cls=null;
        try {
            cls = cl.loadClass(className);
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
            return null;
        }
        try {
             iDatabase = (IDatabase) cls.getDeclaredConstructor(new Class[] {int.class}).newInstance(commandsUntilSave);
            //iDatabase = (IDatabase) cls.newInstance();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }




        return iDatabase;
=======

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
>>>>>>> 9d80d34f49c6edc0b314ed7fe83fef188fc237c1

    }

    private static String getJarPath(String databaseType, int commandsUntilSave) {
        return null;
    }
}
