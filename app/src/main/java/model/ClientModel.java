package model;

import java.util.ArrayList;


import interfaces.Observable;
import interfaces.Observer;


public class ClientModel implements Observable{
    private static final ClientModel myClientModel = new ClientModel();
    private ClientModel(){}
    public static ClientModel getMyClientModel() {
        return myClientModel;
    }


    //begin User
    private User user = User.getMyUser();
    private boolean hasUser = false;

    public String getMyUsername(){
        return user.getUsername();
    }
    public String getAuthToken(){
        return user.getAuthToken();
    }

    public void setMyUser(String username, String authToken){
        user.setMyUsername(username);
        user.setMyAuthToken(authToken);
        hasUser = true;
        notifyObserver();
    }

    public boolean hasUser(){
        return hasUser;
    }

    //if a user logs out, hasUser = false;
    //end User


    //begin Observer
    private ArrayList<Observer> observers = new ArrayList<>();

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
    //end Observer

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        notifyObserver();
    }

    private String ip;
    private String port = "8080";

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



}
