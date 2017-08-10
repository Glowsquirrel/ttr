package database.FileDatabase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import model.User;

/**
 * Created by Rachael on 8/9/2017.
 */

public class UserDao {
    public Set<User> loadUsersFromDatabase()
    {
        Set<User> users=null;
        try {
            FileInputStream fileIn = new FileInputStream("/users.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (Set<User>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return users;
        }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return users;
        }
        return users;
    }
    public void saveNewUserToDatabase(User myNewUser)
    {

        Set<User> users=null;
        try {
            FileInputStream fileIn = new FileInputStream("/users.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (Set<User>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
        users.add(myNewUser);
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /users.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }
}
