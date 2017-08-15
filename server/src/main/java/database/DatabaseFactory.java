package database;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class DatabaseFactory {

    public static IDatabase getDatabase(String databaseType, int commandsUntilSave){
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

    }

}

