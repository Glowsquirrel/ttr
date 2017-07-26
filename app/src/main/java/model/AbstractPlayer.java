package model;

import fysh340.ticket_to_ride.R;

import static fysh340.ticket_to_ride.R.color.neon_green;
import static fysh340.ticket_to_ride.R.color.neon_grey;
import static fysh340.ticket_to_ride.R.color.neon_orange;
import static fysh340.ticket_to_ride.R.color.neon_pink;
import static fysh340.ticket_to_ride.R.color.neon_yellow;

/**
 * Contains all the information that a player would know about any other player in the game. Both Player
 * and VisiblePlayer extend this class.
 *
 * @author Kalan
 */
public abstract class AbstractPlayer {
    private final int STARTING_NUMBER_OF_TRAINS = 45;

    protected String username;
    private int numTrains = STARTING_NUMBER_OF_TRAINS;
    protected int numCards = 0;
    private int numRoutes = 0;
    private int destCardNum=0;
    private int score = 0;

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    public void setNumRoutes(int numRoutes) {
        this.numRoutes = numRoutes;
    }

    public int getDestCardNum() {
        return destCardNum;

    }

    public void setDestCardNum(int destCardNum) {
        this.destCardNum = destCardNum;
    }

    private int color;


    public void setNumTrains(int numTrains) {
        this.numTrains = numTrains;
    }

    public void setColor(int col) {
        int i=col;

        switch(i) {
            case(0):
                color=R.color.neon_yellow;
                break;
            case(1):
                color=R.color.neon_orange;
                break;
            case(2):
                color=R.color.neon_green;
                break;
            case(3):
                color=R.color.neon_pink;
                break;
            case(4):
                color=R.color.neon_grey;
                break;

        }

    }

    public String getMyUsername(){
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

    public void setScore(int score) {
        this.score = score;
    }

    //end score updates
    public int getColor()
    {
       return color;
    }
}
