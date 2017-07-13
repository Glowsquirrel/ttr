package model;

import java.util.ArrayList;
import java.util.List;
import interfaces.Observable;
import interfaces.Observer;

public class ClientModel implements Observable{
    //For Login/Register:

    ClientModel(){

    }

    private String ip;
    private String port = "8080";
    private static final ClientModel myClientModel = new ClientModel();
    //begin User
    private User user = User.getMyUser();
    private boolean userLoggedIn = false;
    //end User
    private boolean hasGame = false;

    //end Observer
    private String errorMessage;
    List<UnstartedGame> gamesToStart;
    private boolean gameFull;
    private boolean startedGame=false;

    public boolean isGameFull() {
        return gameFull;
    }

    public void setGameFull(boolean gameFull) {
        this.gameFull = gameFull;
    }

    private String myGameName;

    public String getMyGameName() {
        return myGameName;
    }

    public void setMyGame(String gameName){
        this.myGameName = gameName;
    }

    private boolean hasCreatedGame = false;

    public boolean hasCreatedGame() {
        return hasCreatedGame;
    }

    public void setHasCreatedGame(boolean hasCreatedGame) {
        this.hasCreatedGame = hasCreatedGame;
    }

    public boolean isStartedGame() {
        for(UnstartedGame i:gamesToStart) {
            if(i.getName().equals(myGameName)) {
                startedGame=i.isStarted();
            }
        }
        return startedGame;
    }

    public void setStartedGame(boolean startedGame) {
        this.startedGame = startedGame;
    }



    //begin Observer
    private ArrayList<Observer> observers = new ArrayList<>();

    private boolean hasMessage = false;

    public boolean hasMessage() {
        return hasMessage;
    }
    public void receivedMessage(){
        hasMessage = false;
    }

    public static ClientModel getMyClientModel() {
        return myClientModel;
    }



    public List<UnstartedGame> getGamesToStart() {
        if (gamesToStart == null) {
            return new ArrayList<UnstartedGame>();
        }
        return gamesToStart;

    }

    public void setGamesToStart(List<UnstartedGame> gamestoStart) {
        gamesToStart = gamestoStart;
        notifyObserver();
    }
    public List<String> getPlayersinGame()
    {
        List<String>toreturn=null;
        int size=0;
        for(UnstartedGame i:gamesToStart) {
            if(i.getName().equals(myGameName)) {
                size=i.getPlayersNeeded();
                toreturn=i.getUsernames();
            }
        }
        if(toreturn==null)
        {
            toreturn=new ArrayList();
        }
        gameFull=true;
        for(int l=toreturn.size();l<size;l++)
        {
            gameFull=false;
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

    public void setMyUser(String username) {
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
        if(observerIndex>=0) {
            observers.remove(observerIndex);
        }
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

    public void setErrorMessage(String errorMessage) { //need to rename this to postMessage
        this.errorMessage = errorMessage;
        this.hasMessage = true;
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
