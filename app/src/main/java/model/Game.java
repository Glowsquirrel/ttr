package model;


import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.Observable;
import interfaces.Observer;

/**
 * Represents the game object.
 * This class is a singleton with a private constructor with only one instance of the object at all times
 * that can only be constructed on the first call of getGameInstance()
 */
public class Game implements Observable{
    private static Game myGame;
    private Game(){}

    /**
     * If instance of the game is currently null a new instance will be created
     *@return The only instance of the game
     */
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

    /**
     * @pre this method must be declared on the only instance of the game object
     * @pre the game needs to have been initialized before calling this method or else it will return null
     * @param username is the username of a player that is in the current game
     * @return The AbstractPlayer object associated with the username
     * @post The game object and player object will not be modified
     * @post if the username is not currently in the game null will be returned
     */
    public AbstractPlayer getPlayerByName(String username) {
        return playerMap.get(username);
    }

    /**
     * @pre game must be initialized before calling this method
     * @pre must be called on the game instance
     * @return list of abstract player objects currently in the game, if no players are initialized an empty list will be returned
     * @post nothing in the game is modified by this function
     *
     */
    public List<AbstractPlayer> getVisiblePlayerInformation() {
        List<AbstractPlayer> playerListToDisplay = new ArrayList<>();
        for (AbstractPlayer myPlayer : this.playerMap.values()){
            playerListToDisplay.add(myPlayer);
        }
        return playerListToDisplay;
    }

    /**
     * initializes the game instance
     * @pre must be called on the game instance
     * @pre This method should only be called once for the duration of the game, else information will be overwritten
     * @post Any past information inside the game will be overwritten
     * @post initialized the values of the game to store the players, game name, and cards that are currently face up
     * @param myself Player object of the current player on the client side, should not be null
     * @param gameName String associated with the current game instance as a identifier to this game, should not be null
     * @param playerNames List of Strings of the usernames of the players in the current game. Length must >=2 and <=5, should not be null
     * @param faceUpCards List of integer values associated with the face up cards, each integer must be <=110 and be
     *                    be the only instance of the integer within the game associated with a train card, should not be null. Length must be equal to 4
     */
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

    /**
     * @pre game must be initialized with a nonnull string value for game name
     * @return returns the game name given upon initialization, otherwise null if the game has not been initialized
     * @post doesn't modify any part of the game object
     */
    public String getMyGameName(){
        return this.gameName;
    }

    /**
     * @pre game must be initialized with a nonnull player value for myself
     * @return Player object that was given as the player object myself up game initialization with any changes made for duration of game
     * @post no modification made to any part of the game object
     */
    public Player getMyself() {
        return myself;
    }
    /**
     * @pre game must be initialized with a nonnull faceup cards list
     * @return returns a list of the integers representing train cards in their current order.
     *  The length will be between 0 and 110 and each integer will have a value associated with a card that is in the deck.
     *  The list will not include cards that currently belong to players or are face up.
     * @post no modification made to any part of the game object
     */

    public List<Integer> getTrainCards() {
        return trainCards;
    }

    /**
     * @pre game must be initialized with a nonnull faceup cards list
     * @return List of integers representing the cards that are currently face up in the game.
     *  The length will be between 2 and 4
     *  @post nothing else in the game will be modified
     */
    public List<Integer> getFaceUpCards() {
        iHaveDifferentFAceUpCards(false);
        return faceUpCards;
    }
    
    //begin CurrentlySelectedRouteID flags
    private boolean newRouteID = false;

    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public boolean routeIDHasChanged() {
        return newRouteID;
    }
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public int getCurrentlySelectedRouteID() {
        newRouteID = false;
        return currentlySelectedRouteID;
    }
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public void setCurrentlySelectedRouteID(int currentlySelectedRouteID) {
        this.currentlySelectedRouteID = currentlySelectedRouteID;
        newRouteID = true;
        notifyObserver();
    }
    
    //begin AllPlayerData flags
    private boolean playerHasChanged = false;
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public boolean aPlayerHasChanged() {
        return playerHasChanged;
    }
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public void aPlayerHasChanged(boolean playerHasChanged) {
        this.playerHasChanged = playerHasChanged;
    }
    //end AllPlayerData flags

    //begin trainCard flags
    private boolean iHaveDifferentTrainCards = false;
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public boolean iHaveDifferentTrainCards() {
        return iHaveDifferentTrainCards;
    }
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public void iHaveDifferentTrainCards(boolean trainCardChange){
        this.iHaveDifferentTrainCards = trainCardChange;
    }
    
    private boolean iHaveDifferentFaceUpCards = false;
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public boolean iHaveDifferentFaceUpCards() {
        return iHaveDifferentFaceUpCards;
    }
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public void iHaveDifferentFAceUpCards(boolean faceUpCardChange) {
        iHaveDifferentFaceUpCards = faceUpCardChange;
    }
    //end trainCard flags

    //begin Observable
    /**
     * Overrides the register() method on the Observable interface
     * @post the update method on o will be called each time notifyObserver() is called
     * @param o Observer object that implements the observer interface to be notified by this observable
     */
    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    /**
     * Overrides the unregister() method on the Observable interface
     * @param deleteObserver Object that implements the observor interface that is currently registered to
     *                       this object
     * @post deletObserver will no longer be updated or registered to this observable
     * @post no other observers, or the observer will be modified
     */
    @Override
    public void unregister(Observer deleteObserver) {
        int observerIndex = observers.indexOf(deleteObserver);
        if(observerIndex>=0) {
            observers.remove(observerIndex);
        }
    }

    /**
     * Overrides the notifyObservor() method on the Observable interfaces
     * @pre must have observer objects that have been registered to this observer
     * @post calls the update method on any objects currently registered as observers
     */

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
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public boolean iHaveDifferentDestCards(){
        return iHaveDifferentDestCards;
    }
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public void iHaveDifferentDestCards(boolean iHaveDifferentDestCards){
        this.iHaveDifferentDestCards = iHaveDifferentDestCards;
    }

    private List<DestCard> possibleDestCards = new ArrayList<>();
    private boolean iHavePossibleDestCards = false;
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public void setPossibleDestCards(List<Integer> possibleDestCards){
        this.possibleDestCards = new ArrayList<>();
        for (int i = 0; i < possibleDestCards.size(); i++){
            this.possibleDestCards.add(DestCard.getDestCardByID(possibleDestCards.get(i)));
        }
    }
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public boolean iHavePossibleDestCards(){
        return iHavePossibleDestCards;
    }
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public void iHavePossibleDestCards(boolean iHavePossibleDestCards){
        this.iHavePossibleDestCards = iHavePossibleDestCards;
    }
    /**todo
     *
     * @return boolean value to be used as a flag
     */
    public List<DestCard> getPossibleDestCards(){
        return possibleDestCards;
    }
}
