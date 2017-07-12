package model;

import java.util.ArrayList;
import java.util.List;


import interfaces.Observable;
import interfaces.Observer;


public class ClientModel implements Observable{
    private static final ClientModel myClientModel = new ClientModel();
    //begin User
    private User user = User.getMyUser();
    private boolean userLoggedIn = false;
    //if a user logs out, userLoggedIn = false;
    //end User
    private boolean hasGame=false;
    private boolean startedGame=false;
    private String gameName;

    public boolean isStartedGame() {
        for(UnstartedGames i:gamesToStart) {
            if(i.getName()==gameName) {
                startedGame=i.isStarted();
            }
        }
        return startedGame;
    }

    public void setStartedGame(boolean startedGame) {
        this.startedGame = startedGame;
    }

    private String ip;
    private String port = "8080";
    //begin Observer
    private ArrayList<Observer> observers = new ArrayList<>();
    private ClientModel(){}
    public static ClientModel getMyClientModel() {
        return myClientModel;
    }
    List<UnstartedGames> gamesToStart;


    public List<UnstartedGames> getGamesToStart() {
        if (gamesToStart == null) {
            return new ArrayList<UnstartedGames>();
        }
        return gamesToStart;

    }

    public void setGamestoStart(List<UnstartedGames> gamestoStart) {
        gamesToStart = gamestoStart;
    }
    public List<String> getPlayersinGame()
    {
        List<String>toreturn=null;
        int size=0;
        for(UnstartedGames i:gamesToStart) {
            if(i.getName()==gameName) {
                size=i.getPlayersNeeded();
                toreturn=i.getUsernames();
            }
        }
        if(toreturn==null)
        {
            toreturn=new ArrayList();
        }
        for(int l=toreturn.size();l<=size;l++)
        {
            toreturn.add("Waiting for player "+l);
        }
        return toreturn;
    }


    public String getMyUsername(){
        return user.getUsername();
    }
    public String getAuthToken(){
        return user.getAuthToken();
    }

    public void setMyUser(String username){
        user.setMyUsername(username);
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
    //end Observer

    private String errorMessage;

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
