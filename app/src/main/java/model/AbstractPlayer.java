package model;

/**
 * Contains all the information that a player would know about any other player in the game. Both Player
 * and VisiblePlayer extend this class.
 */
public abstract class AbstractPlayer {
    private final int STARTING_NUMBER_OF_TRAINS = 45;
    private final int STARTING_NUMBER_OF_CARDS = 4;

    protected String username;
    private int numTrains = STARTING_NUMBER_OF_TRAINS;
    private int numCards = STARTING_NUMBER_OF_CARDS;
    private int numRoutes = 0;
    private int score = 0;

    public String getUserName(){
        return this.username;
    }

    public int getNumTrains() {
        return numTrains;
    }

    public int getNumCards(){
        return this.numCards;
    }

    public int getNumRoutes(){
        return this.numRoutes;
    }

    public int getScore() {
        return score;
    }

    //begin train updates
    public void removeTrains(int numTrainsToRemove){
        this.numTrains -= numTrainsToRemove;
    }
    //end train updates

    //begin train card updates
    public void addTrainCard(){
        this.numCards++;
    }
    public void removeTrainCard() { //may not be used
        this.numCards--;
    }
    public void addMultipleTrainCards(int numCardsToAdd){
        this.numCards += numCardsToAdd;
    }
    public void removeMultipleTrainCards(int numCardsToRemove){
        this.numCards -= numCardsToRemove;
    }
    //end train card updates

    //begin route updates
    public void incrementNumRoutesClaimed(){
        this.numRoutes++;
    }
    //end route updates

    //begin score updates
    public void addToScore(int scoreToAdd){
        this.score += scoreToAdd;
    }
    //end score updates
}
