package model;

import java.util.ArrayList;
import java.util.List;


public class Player extends AbstractPlayer {

    public Player(String userName, List<Integer> trainCardInts){
        super.username = userName;
        initializeMyCards(trainCardInts);
    }

    private void initializeMyCards(List<Integer> trainCardInts){
        for (int myTrainCardID : trainCardInts){
            addTrainCardByInt(myTrainCardID);
        }
    }

    public int getNumOfCards(){
        return this.numOfPurpleCards + this.numOfWhiteCards + this.numOfBlueCards + this.numOfYellowCards +
                this.numOfOrangeCards + this.numOfBlackCards + this.numOfRedCards + this.numOfGreenCards +
                this.numOfWildCards;
    }

    public void addTrainCardByInt(int myTrainCardInt){
        super.addTrainCard();
        TrainCard myTrainCard = TrainCard.getTrainCard(myTrainCardInt);
        switch (myTrainCard){
            case PURPLE:
                this.numOfPurpleCards++;
                break;
            case WHITE:
                this.numOfWhiteCards++;
                break;
            case BLUE:
                this.numOfBlueCards++;
                break;
            case YELLOW:
                this.numOfYellowCards++;
                break;
            case ORANGE:
                this.numOfOrangeCards++;
                break;
            case BLACK:
                this.numOfBlackCards++;
                break;
            case RED:
                this.numOfRedCards++;
                break;
            case GREEN:
                this.numOfGreenCards++;
                break;
            case WILD:
                this.numOfWildCards++;
                break;
        }
    }

    private int numOfPurpleCards = 0;
    private int numOfWhiteCards = 0;
    private int numOfBlueCards = 0;
    private int numOfYellowCards = 0;
    private int numOfOrangeCards = 0;
    private int numOfBlackCards = 0;
    private int numOfRedCards = 0;
    private int numOfGreenCards = 0;
    private int numOfWildCards = 0;

    public void removeMultipleCardsOfType(TrainCard myTrainCard, int numToRemove){
        switch (myTrainCard){
            case PURPLE:
                this.numOfPurpleCards -=numToRemove;
                break;
            case WHITE:
                this.numOfWhiteCards -= numToRemove;
                break;
            case BLUE:
                this.numOfBlueCards -= numToRemove;
                break;
            case YELLOW:
                this.numOfYellowCards -= numToRemove;
                break;
            case ORANGE:
                this.numOfOrangeCards -= numToRemove;
                break;
            case BLACK:
                this.numOfBlackCards -= numToRemove;
                break;
            case RED:
                this.numOfRedCards -= numToRemove;
                break;
            case GREEN:
                this.numOfGreenCards -= numToRemove;
                break;
            case WILD:
                this.numOfWildCards -= numToRemove;
                break;
        }
    }

    public int getNumOfTypeCards(TrainCard myTrainCardType){
        switch (myTrainCardType){
            case PURPLE:
                return this.numOfPurpleCards;
            case WHITE:
                return this.numOfWhiteCards;
            case BLUE:
                return this.numOfBlueCards;
            case YELLOW:
                return this.numOfYellowCards;
            case ORANGE:
                return this.numOfOrangeCards;
            case BLACK:
                return this.numOfBlackCards;
            case RED:
                return this.numOfRedCards;
            case GREEN:
                return this.numOfGreenCards;
            case WILD:
                return this.numOfWildCards;
            default:
                return -1;
        }
    }

    public int getNumOfPurpleCards() {
        return numOfPurpleCards;
    }

    public void setNumOfPurpleCards(int numOfPurpleCards) {
        this.numOfPurpleCards = numOfPurpleCards;
    }

    public int getNumOfWhiteCards() {
        return numOfWhiteCards;
    }

    public void setNumOfWhiteCards(int numOfWhiteCards) {
        this.numOfWhiteCards = numOfWhiteCards;
    }

    public int getNumOfBlueCards() {
        return numOfBlueCards;
    }

    public void setNumOfBlueCards(int numOfBlueCards) {
        this.numOfBlueCards = numOfBlueCards;
    }

    public int getNumOfYellowCards() {
        return numOfYellowCards;
    }

    public void setNumOfYellowCards(int numOfYellowCards) {
        this.numOfYellowCards = numOfYellowCards;
    }

    public int getNumOfOrangeCards() {
        return numOfOrangeCards;
    }

    public void setNumOfOrangeCards(int numOfOrangeCards) {
        this.numOfOrangeCards = numOfOrangeCards;
    }

    public int getNumOfBlackCards() {
        return numOfBlackCards;
    }

    public void setNumOfBlackCards(int numOfBlackCards) {
        this.numOfBlackCards = numOfBlackCards;
    }

    public int getNumOfRedCards() {
        return numOfRedCards;
    }

    public void setNumOfRedCards(int numOfRedCards) {
        this.numOfRedCards = numOfRedCards;
    }

    public int getNumOfGreenCards() {
        return numOfGreenCards;
    }

    public void setNumOfGreenCards(int numOfGreenCards) {
        this.numOfGreenCards = numOfGreenCards;
    }

    public int getNumOfWildCards() {
        return numOfWildCards;
    }

    public void setNumOfWildCards(int numOfWildCards) {
        this.numOfWildCards = numOfWildCards;
    }

    public List<TrainCard> getMyTrainCards() {
        return myTrainCards;
    }

    public void setMyTrainCards(List<TrainCard> myTrainCards) {
        this.myTrainCards = myTrainCards;
    }

    public void setMyDestCards(List<DestCard> myDestCards) {
        this.myDestCards = myDestCards;
    }

    private List<TrainCard> myTrainCards = new ArrayList<>();
    private List<DestCard> myDestCards = new ArrayList<>();

    public int[] getMyTrainCardsAsIntArray(){
        int myTrainCards[] = new int[9];
        myTrainCards[0] = this.numOfPurpleCards;
        myTrainCards[1] = this.numOfWhiteCards;
        myTrainCards[2] = this.numOfBlueCards;
        myTrainCards[3] = this.numOfYellowCards;
        myTrainCards[4] = this.numOfOrangeCards;
        myTrainCards[5] = this.numOfBlackCards;
        myTrainCards[6] = this.numOfRedCards;
        myTrainCards[7] = this.numOfGreenCards;
        myTrainCards[8] = this.numOfWildCards;
        return myTrainCards;
    }

    public List<DestCard> getMyDestCards() {
        return myDestCards;
    }

    public void addDestCard(DestCard destCard){
        this.myDestCards.add(destCard);
    }

    public boolean addCards(List<TrainCard> allCards){
         for (TrainCard currentCard : allCards){
             if (currentCard == TrainCard.RED){
                 numOfRedCards++;
             }
             else if (currentCard == TrainCard.GREEN){
                 numOfGreenCards++;
             }
             else if (currentCard == TrainCard.BLUE){
                 numOfBlueCards++;
             }
             else if (currentCard == TrainCard.YELLOW){
                 numOfYellowCards++;
             }
             else {
                 numOfBlackCards++;
             }
         }
         return true;
     }

}
