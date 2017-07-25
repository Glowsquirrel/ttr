package model;


import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.Observable;
import interfaces.Observer;

public class Game implements Observable{
    //singleton lazy loading pattern:
    private static Game myGame;
    private Game(){}
    public static Game getGameInstance(){
        if (myGame == null)
            myGame = new Game();
        return myGame;
    }

    private int numOfPlayers = 0;
    private String gameName;
    private Player myself;
    private List<String> playerUserNames = new ArrayList<>();
    private Map<String, AbstractPlayer> playerMap = new HashMap<>();
    private List<Integer> trainCards;
    private List<Integer> faceUpCards;
    private ArrayList<Observer> observers = new ArrayList<>();

    public AbstractPlayer getPlayerByName(String username) {
        return playerMap.get(username);
    }

    public List<AbstractPlayer> getVisiblePlayerInformation() {
        List<AbstractPlayer> playerListToDisplay = new ArrayList<>();
        for (AbstractPlayer myPlayer : this.playerMap.values()){
            playerListToDisplay.add(myPlayer);
        }
        return playerListToDisplay;
    }


    public void initializeMyGame(Player myself, String gameName, List<String> playerNames, List<Integer> faceUpCards){
        this.myself = myself;
        this.gameName = gameName;
        this.faceUpCards = faceUpCards;
        this.playerUserNames = playerNames;

        for(String name : playerNames) {
            if (name.equals(myself.getMyUsername())){
                playerMap.put(myself.getMyUsername(), this.myself);
                continue;
            }
            VisiblePlayer myPlayer = new VisiblePlayer(name);
            playerMap.put(name, myPlayer);
        }

    }

    public String getMyGameName(){
        return this.gameName;
    }

    public Player getMyself() {
        return myself;
    }


    public List<Integer> getTrainCards() {
        return trainCards;
    }

    public List<Integer> getFaceUpCards() {
        return faceUpCards;
    }

    //begin AllPlayerData flags
    private boolean playerHasChanged = false;
    public boolean aPlayerHasChanged() {
        return playerHasChanged;
    }
    public void aPlayerHasChanged(boolean playerHasChanged) {
        this.playerHasChanged = playerHasChanged;
    }
    //end AllPlayerData flags

    //begin trainCard flags
    private boolean iHaveDifferentTrainCards = false;
    public boolean iHaveDifferentTrainCards() {
        return iHaveDifferentTrainCards;
    }
    public void iHaveDifferentTrainCards(boolean trainCardChange){
        this.iHaveDifferentTrainCards = trainCardChange;
    }
    //end trainCard flags

    //begin Observable
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
    //end Observerable
}
