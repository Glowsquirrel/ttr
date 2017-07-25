package model;

import java.util.ArrayList;
import java.util.List;


public class Player extends AbstractPlayer {

    public Player(String userName, List<Integer> trainCardInts, List<Integer> destCardInts){
        super.username = userName;
        intializeMyCards(trainCardInts, destCardInts);
    }

    private void intializeMyCards(List<Integer> trainCardInts, List<Integer> destCardInts){
        for (int myTrainCardID : trainCardInts){
            addTrainCardByInt(myTrainCardID);
        }
        for (int myDestCardID : destCardInts){
            myDestCards.add(DestCard.getDestCardByID(myDestCardID));
        }
    }

    public void addTrainCardByInt(int myTrainCardInt){
        super.addTrainCard();
        TrainCard myTrainCard = TrainCard.getTrainCardTypeByInt(myTrainCardInt);
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
