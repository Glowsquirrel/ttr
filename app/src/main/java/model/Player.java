package model;

import java.util.ArrayList;
import java.util.List;


public class Player extends AbstractPlayer {
    private final int STARTING_NUMBER_OF_TRAINS = 45;

    public Player(String userName, List<Integer> destCardInts){
        super.username = userName;
        intializeMyDestCards(destCardInts);
    }

    private void intializeMyDestCards(List<Integer> destCardInts){
        for (int myDestCardID : destCardInts){
            myDestCards.add(DestCard.getDestCardByID(myDestCardID));
        }
    }

    private int numOfRedCards = 0;
    private int numOfGreenCards = 0;
    private int numOfBlueCards = 0;
    private int numOfYellowCards = 0;
    private int numOfBlackCards = 0;

    private List<DestCard> myDestCards = new ArrayList<>();

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
