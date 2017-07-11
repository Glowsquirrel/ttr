package model;

import java.util.ArrayList;
import java.util.List;


import interfaces.Observable;
import interfaces.Observer;


public class ClientModel implements Observable{
    //For Login/Register:
    private String ip;
    private String port = "8080";
    private static final ClientModel myClientModel = new ClientModel();
    //begin User
    private User user = User.getMyUser();
    private boolean userLoggedIn = false;
    //end User
    private boolean hasGame=false;
    //end Observer

    private String errorMessage;
    List<UnstartedGames> gamesToStart;
    //begin Observer
    private ArrayList<Observer> observers = new ArrayList<>();

    ClientModel(){

    }


    public static ClientModel getMyClientModel() {
        return myClientModel;
    }


    public List<UnstartedGames> getGamesToStart() {
        return gamesToStart;

    }

    public void setGamesToStart(List<UnstartedGames> gamestoStart) {
        gamesToStart = gamestoStart;
    }

    public String getMyUsername(){
        return user.getUsername();
    }
    public String getAuthToken(){
        return user.getAuthToken();
    }

    public void setMyUser(String username, String authToken){
        user.setMyUsername(username);
        user.setMyAuthToken(authToken);
        userLoggedIn = true;
        notifyObserver();
    }

    public boolean hasUser(){
        return userLoggedIn;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        notifyObserver();
    }

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

    public boolean hasGame() {return hasGame;}

    public void setHasGame(boolean hasGame) {this.hasGame = hasGame;}


}
