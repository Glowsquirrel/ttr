package database.FileDatabase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import commands.Command;
import model.User;

/**
 * Created by Rachael on 8/9/2017.
 */

public class CommandDao {
    String fileName="C:\\Users\\Rachael\\Documents\\ttr340\\340\\ttr\\commands.ser.txt";
    private int commandNum;

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public boolean saveCommandToDatabase(String gameName, Command nextCommand)
    {
        Map<String, List<Command>> commands=null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            commands = (Map<String, List<Command>>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
        }
        if(commands==null)commands=new HashMap<>();
        List<Command> commandList=commands.get(gameName);
        if(commandList!=null) {
            commandList.add(nextCommand);
        }
        else
        {
            commandList=new ArrayList<Command>();
            commandList.add(nextCommand);
        }
        commands.put(gameName,commandList);
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(commands);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /commands.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        if(commandList.size()>commandNum)
        {
            return true;
        }
        return false;
    }
    public Map<String, List<Command>> loadOutstandingCommandsFromDatabase()
    {
        Map<String, List<Command>> commands=null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            commands = (Map<String, List<Command>>) in.readObject();
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
        return commands;
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
