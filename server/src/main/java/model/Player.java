package model;

import java.util.ArrayList;
import java.util.List;


 class Player {

    private String userName;
    private List<TrainCard> trainCardHand = new ArrayList<>();

    private int numOfPurpleCards = 0;
    private int numOfWhiteCards = 0;
    private int numOfBlueCards = 0;
    private int numOfYellowCards = 0;
    private int numOfOrangeCards = 0;
    private int numOfBlackCards = 0;
    private int numOfRedCards = 0;
    private int numOfGreenCards = 0;
    private int numOfWildCards = 0;

    private PlayerColor playerColor;
    private ArrayList<DestCard> destCardHand = new ArrayList<>();
    private List<DestCard> newlyDrawnDestCards = new ArrayList<>();
    private int points;
    private int numOfRoutes;
    private int numOfCars = 45;

     Player(String userName) {
        this.userName = userName;
    }

      void addTrainCards(List<TrainCard> drawnCards){

         for (TrainCard currentCard : drawnCards){
             trainCardHand.add(currentCard);
             if (currentCard == TrainCard.PURPLE){
                 numOfPurpleCards++;
             }
             else if (currentCard == TrainCard.WHITE) {
                 numOfWhiteCards++;
             }
             else if (currentCard == TrainCard.BLUE){
                 numOfBlueCards++;
             }
             else if (currentCard == TrainCard.YELLOW){
                 numOfYellowCards++;
             }
             else if (currentCard == TrainCard.ORANGE){
                 numOfOrangeCards++;
             }
             else if (currentCard == TrainCard.BLACK){
                 numOfBlackCards++;
             }
             else if (currentCard == TrainCard.RED){
                 numOfRedCards++;
             }
             else if (currentCard == TrainCard.GREEN){
                 numOfGreenCards++;
             }
             else {
                 numOfWildCards++;
             }
         }
     }

    /**
     * Places drawn cards in newlyDrawnDestCards member. Does not add them to hand.
     * @param drawnCards Cards drawn from board's DestCardDeck
     */
     void addDestCards(List<DestCard> drawnCards) {
        for (DestCard currentCard : drawnCards) {
            newlyDrawnDestCards.add(currentCard);
        }
    }


     boolean removeDestCards(DestCard cardOne, DestCard cardTwo) throws GamePlayException {
        boolean success = false;

        if (cardOne != null) {
            for (int a = 0; a < newlyDrawnDestCards.size(); a++) {
                if (cardOne.equals(newlyDrawnDestCards.get(a))) {
                    success = true;
                    newlyDrawnDestCards.remove(cardOne);
                }
            }
            if (!success) {
                throw new GamePlayException("Invalid card returned");
            }
        }

        success = false;
        if (cardTwo != null) {
            for (DestCard currentCard : newlyDrawnDestCards) {
                if (cardTwo.equals(currentCard)) {
                    success = true;
                    newlyDrawnDestCards.remove(cardTwo);
                }
            }
            if (!success) {
                throw new GamePlayException("Invalid card returned");
            }
        }

        for (DestCard currentCard : newlyDrawnDestCards) {
            destCardHand.add(currentCard);
        }

        newlyDrawnDestCards.clear();

        ArrayList<DestCard> returnList = new ArrayList<>();
        returnList.add(cardOne);
        returnList.add(cardTwo);
        return true;
    }

    public boolean invalidDestCard(DestCard destCard) {
        for (DestCard drawnDestCard : newlyDrawnDestCards) {
            if (destCard.equals(drawnDestCard)){
                return false;
            }
        }
        return true;
    }

    public String getUsername() {
        return userName;
    }

    int getNumOfCars() {
        return numOfCars;
    }

    int getSizeOfTrainCardHand() {
        return trainCardHand.size();
    }

    int getSizeOfDestCardHand() {
        return destCardHand.size();
    }

    int getPoints() {
        return points;
    }

    int getNumOfRoutesOwned() {
        return numOfRoutes;
    }
    public List<DestCard> getDestCards() {
        return destCardHand;
    }

    List<Integer> getTrainCardCodes() {
        List<Integer> trainCardCodes = new ArrayList<>();
        for (TrainCard trainCard : trainCardHand) {
            trainCardCodes.add(TrainCard.getTrainCardKey(trainCard));
        }
        return trainCardCodes;
    }

    /**
     * For error testing
     */
    public int getTotalNumberOfCards(){
         return numOfBlackCards + numOfYellowCards + numOfRedCards
                + numOfBlueCards + numOfGreenCards;
    }

    boolean removeCars(int numOfCars) {
        if (numOfCars < 0 || numOfCars > 6){
            return false;
        }
        this.numOfCars -= numOfCars;
        return true;
    }

    PlayerColor getPlayerColor() {
        return playerColor;
    }
    void setPlayerColor(int position) {
        playerColor = PlayerColor.getPlayerColor(position);
    }
    List<DestCard> getNewlyDrawnDestCards() {
        return newlyDrawnDestCards;
    }

    void addScore(int points) {
        this.points += points;
    }
    void addNumOfRoutes() {
        numOfRoutes++;
    }

    void removeTrainCards(List<TrainCard> discardedCards) {
        for (int a = 0; a  < discardedCards.size(); a++) {
            TrainCard trainCard = discardedCards.get(a);
            switch (trainCard){
                case RED:
                    numOfRedCards--;
                case GREEN:
                    numOfGreenCards--;
                case BLUE:
                    numOfBlueCards--;
                case YELLOW:
                    numOfYellowCards--;
                case BLACK:
                    numOfBlackCards--;
                case PURPLE:
                    numOfPurpleCards--;
                case ORANGE:
                    numOfOrangeCards--;
                case WHITE:
                    numOfWhiteCards--;
                case WILD:
                    numOfWildCards--;
            }
            removeCardFromHand(trainCard);
        }
    }

    private void removeCardFromHand(TrainCard trainCard) {
        for(int a = 0; a < trainCardHand.size(); a++){
            if (trainCard == trainCardHand.get(a)) {
                trainCardHand.remove(a);
                break;
            }
        }
    }
     int getNumOfPurpleCards() {
         return numOfPurpleCards;
     }

     int getNumOfWhiteCards() {
         return numOfWhiteCards;
     }

     int getNumOfBlueCards() {
         return numOfBlueCards;
     }

     int getNumOfYellowCards() {
         return numOfYellowCards;
     }

     int getNumOfOrangeCards() {
         return numOfOrangeCards;
     }

     int getNumOfBlackCards() {
         return numOfBlackCards;
     }

     int getNumOfRedCards() {
         return numOfRedCards;
     }

     int getNumOfGreenCards() {
         return numOfGreenCards;
     }

     int getNumOfWildCards() {
         return numOfWildCards;
     }

}
