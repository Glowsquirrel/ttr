package database.FileDatabase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commands.Command;
import model.StartedGame;

/**
 * Created by Rachael on 8/9/2017.
 */

public class GameDao {
    String fileName="C:\\Users\\Rachael\\Documents\\ttr340\\340\\ttr\\games.ser.txt";
    public void saveNewStartedGameToDatabase(StartedGame myGame)
    {
        Map<String,StartedGame> games=null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            games = (Map<String, StartedGame>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
            return;
        }
        games.put(myGame.getGameName(),myGame);
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(games);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /games.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        return;
    }
    public void updateStartedGameInDatabase(StartedGame myGame)
    {
        Map<String,StartedGame> games=null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            games = (Map<String, StartedGame>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
            return;
        }
        if(games==null)games=new HashMap<>();
        games.put(myGame.getGameName(),myGame);
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(games);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /games.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        return;
    }
    public Map<String, StartedGame> loadStartedGamesFromDatabase()
    {
        Map<String,StartedGame> games=null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            games = (Map<String, StartedGame>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
            return null;
        }
        return games;
    }
    public boolean clearDatabase()
    {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /users.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        return true;
    }



}
