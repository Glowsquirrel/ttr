package model;

import java.util.List;

/**
 * Created by sjrme on 7/11/17.
 */

public class Player {

    String userName;
    int numOfRedCards = 0;
    int numOfGreenCards = 0;
    int numOfBlueCards = 0;
    int numOfYellowCards = 0;
    int numOfBlackCards = 0;
    int points;

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
