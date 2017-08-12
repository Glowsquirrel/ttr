package database.FileDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import model.User;

/**
 * Created by Rachael on 8/9/2017.
 */

public class UserDao {
    String fileName="C:\\Users\\Rachael\\Documents\\ttr340\\340\\ttr\\users.ser.txt";
    File file=new File(fileName);
    public Set<User> loadUsersFromDatabase() {
        Set<User> users = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (Set<User>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return users;
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
            return users;
        }
        return users;
    }

    public void saveNewUserToDatabase(User myNewUser) {

        Set<User> users = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (Set<User>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
        }
        if(users==null)users=new HashSet<>();
        users.add(myNewUser);
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /users.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public boolean clearDatabase() {
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
