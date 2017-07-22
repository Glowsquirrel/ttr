package model;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

import interfaces.Observable;
import interfaces.Observer;

public class ClientModel implements Observable{
    //singleton pattern:
    private static final ClientModel myClientModel = new ClientModel();
    private ClientModel(){}

    //For Login/Register:
    private boolean leftGame;
    private String ip;
    private String port = "8080";

    public boolean isLeftGame() {
        return leftGame;
    }

    public void setLeftGame(boolean leftGame) {
        this.leftGame = leftGame;
    }

    //begin User

    private boolean hasGame = false;

    //end Observer
    private String errorMessage;
    private List<UnstartedGame> unstartedGameList = new ArrayList<>();
    private List<RunningGame> runningGameList = new ArrayList<>();
    private boolean gameFull;
    private boolean startedGame=false;

    public boolean isGameFull() {
        gameFull=false;
        for(UnstartedGame i: unstartedGameList) {
            if(i.getGameName().equals(myGameName)) {
                if(i.getPlayersNeeded() == i.getPlayersIn()) {
                    gameFull=true;
                }
            }
        }
        return gameFull;
    }


    private String myGameName;

    public String getMyGameName() {
        return myGameName;
    }

    public void setMyGame(String gameName){
        this.myGameName = gameName;
    }

    public boolean isStartedGame() {
        startedGame=true;
        for(UnstartedGame i: unstartedGameList) {
            if(i.getGameName().equals(myGameName)) {
                startedGame=false;
            }
        }
        return startedGame;
    }

    //begin menu login
    private String username;

    public String getMyUsername() {
        return username;
    }

    private boolean hasUser;

    public boolean hasUser() {
        return hasUser;
    }

    public void setMyUser(String username, boolean hasUser) {
        this.username = username;
        this.hasUser = hasUser;
        notifyObserver();
    }
    //end menu login

    public void setStartedGame(boolean startedGame) {
        this.startedGame = startedGame;
    }

    private boolean createdGame = false;

    public boolean hasCreatedGame() {
        return createdGame;
    }

    public void setCreatedGame(boolean createdGame) {
        this.createdGame = createdGame;
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



    public List<UnstartedGame> getUnstartedGameList() {
        return this.unstartedGameList;
    }
    public List<RunningGame> getRunningGameList(){
        return this.runningGameList;
    }

    public void setGameLists(List<UnstartedGame> unstartedGameList, List<RunningGame> runningGameList){
        if (unstartedGameList != null)
            this.unstartedGameList = unstartedGameList;
        if (runningGameList != null)
            this.runningGameList = runningGameList;
        notifyObserver();
    }

    public List<String> getPlayersinGame()
    {
        List<String>toreturn=null;
        int size=0;
        for(UnstartedGame i: unstartedGameList) {
            if(i.getGameName().equals(myGameName)) {
                size=i.getPlayersNeeded();
                toreturn=i.getUsernamesInGame();
            }
        }
        if(toreturn==null)
        {
            toreturn=new ArrayList();
        }
        for(int l=toreturn.size();l<size;l++)
        {
            toreturn.add("Waiting for player "+l);
        }
        return toreturn;
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
    public synchronized void notifyObserver() {
        Handler uiHandler = new Handler(Looper.getMainLooper()); //gets the UI thread
        Runnable runnable = new Runnable() { //
            @Override
            public void run() {
                for (int i = 0; i < observers.size(); i++){
                    observers.get(i).update();
                }
            }
        };
        uiHandler.post(runnable); //do the run() method in previously declared runnable on UI thread
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
