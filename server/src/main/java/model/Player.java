package model;

import java.util.ArrayList;
import java.util.List;


public class Player {

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
    int points;

    public Player(String userName) {
        this.userName = userName;
    }

     public void addTrainCards(List<TrainCard> drawnCards){

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
    public void addDestCards(List<DestCard> drawnCards) {
        for (DestCard currentCard : drawnCards) {
            newlyDrawnDestCards.add(currentCard);
        }
    }

    public boolean removeDestCards(DestCard cardOne, DestCard cardTwo) throws GamePlayException {
        boolean success = false;

        if (cardOne != null) {
            for (DestCard currentCard : newlyDrawnDestCards) {
                if (cardOne.equals(currentCard)) {
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

    public String getUsername() {
        return userName;
    }

    public List<DestCard> getDestCards() {
        return destCardHand;
    }

    public List<Integer> getTrainCardCodes() {
        List<Integer> trainCardCodes = new ArrayList<>();
        for (TrainCard trainCard : trainCardHand) {
            trainCardCodes.add(TrainCard.getTrainCardInt(trainCard));
        }
        return trainCardCodes;
    }

    /**
     * For error testing
     */
    public int getTotalNumberOfCards(){
        int allCards = numOfBlackCards + numOfYellowCards + numOfRedCards
                + numOfBlueCards + numOfGreenCards;

        return allCards;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }
    public void setPlayerColor(int position) {
        playerColor = PlayerColor.getPlayerColor(position);
    }

    public List<DestCard> getNewlyDrawnDestCards() {
        return newlyDrawnDestCards;
    }

}
