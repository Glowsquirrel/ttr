package model;

import java.util.List;


public class Player extends AbstractPlayer {
    private final int STARTING_NUMBER_OF_TRAINS = 45;

    public Player(String userName){
        super.username = userName;
    }

    private int numOfRedCards = 0;
    private int numOfGreenCards = 0;
    private int numOfBlueCards = 0;
    private int numOfYellowCards = 0;
    private int numOfBlackCards = 0;

    private int turn;


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
