package Model;

import java.util.ArrayList;


import Interfaces.Observable;
import Interfaces.Observer;


public class ClientModel implements Observable{
    private static final ClientModel myClientModel = new ClientModel();
    private ClientModel(){}
    public static ClientModel getMyClientModel() {
        return myClientModel;
    }



    private User user = User.getMyUser();

    public String getMyUsername(){
        return user.getUsername();
    }
    public String getAuthToken(){
        return user.getAuthToken();
    }
    public void setMyUsername(String username){
        user.setUsername(username);
    }
    public void setMyAuthToken(String authToken){
        user.setMyAuthToken(authToken);
    }




    private ArrayList<Observer> observers;
    private String ip;
    private String port;

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }


    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregister(Observer deleteObserver) {
        int observerIndex = observers.indexOf(deleteObserver);
        observers.remove(observerIndex);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers){
            observer.update();
        }
    }

}
