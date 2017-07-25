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
    private int currentlySelectedRouteID;

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
        int i=0;
        for(String name : playerNames) {
            if (name.equals(myself.getMyUsername())){
                playerMap.put(myself.getMyUsername(), this.myself);
                myself.setColor(i);
                i++;
                continue;
            }
            VisiblePlayer myPlayer = new VisiblePlayer(name);
            myPlayer.setColor(i);
            i++;
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
        iHaveDifferentFAceUpCards(false);
        return faceUpCards;
    }
    
    public int getCurrentlySelectedRouteID() {
        newRouteID = false;
        return currentlySelectedRouteID;
    }
    public void setCurrentlySelectedRouteID(int currentlySelectedRouteID) {
        this.currentlySelectedRouteID = currentlySelectedRouteID;
        newRouteID = true;
        notifyObserver();
    }
    //begin CurrentlySelectedRouteID flags
    private boolean newRouteID = false;
    public boolean routeIDHasChanged() {
        return newRouteID;
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
    
    private boolean iHaveDifferentFaceUpCards = false;
    public boolean iHaveDifferentFaceUpCards() {
        return iHaveDifferentFaceUpCards;
    }
    public void iHaveDifferentFAceUpCards(boolean faceUpCardChange) {
        iHaveDifferentFaceUpCards = faceUpCardChange;
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

    private boolean iHaveDifferentDestCards = false;
    public boolean iHaveDifferentDestCards(){
        return iHaveDifferentDestCards;
    }
    public void iHaveDifferentDestCards(boolean iHaveDifferentDestCards){
        this.iHaveDifferentDestCards = iHaveDifferentDestCards;
    }

    private List<DestCard> possibleDestCards = new ArrayList<>();
    private boolean iHavePossibleDestCards = false;
    public void setPossibleDestCards(List<Integer> possibleDestCards){
        this.possibleDestCards = new ArrayList<>();
        for (int i = 0; i < possibleDestCards.size(); i++){
            this.possibleDestCards.add(DestCard.getDestCardByID(possibleDestCards.get(i)));
        }
    }
    public boolean iHavePossibleDestCards(){
        return iHavePossibleDestCards;
    }
    public void iHavePossibleDestCards(boolean iHavePossibleDestCards){
        this.iHavePossibleDestCards = iHavePossibleDestCards;
    }
    public List<DestCard> getPossibleDestCards(){
        return possibleDestCards;
    }
}
