package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Board {

    private List<TrainCard> trainCardDeck = new ArrayList<>();
    private List<TrainCard> faceUpTrainCards = new ArrayList<>();
    private List<TrainCard> discardedTrainCards = new ArrayList<>();
    private List<DestCard> destCardDeck = new ArrayList<>();

    private Map<Integer, Route> routeMap = new HashMap<>();
    private Map<Integer, DestCard> destCardMap = new HashMap<>();

    Board(){
        BoardSetUp setUpMyBoard = new BoardSetUp(this);
        setUpMyBoard.initializeBoard();
    }

    public ArrayList<TrainCard> drawTrainCards(int numberDrawn) {

        final int TOP_CARD_INDEX = 0;
        ArrayList<TrainCard> drawnTrainCards = new ArrayList<TrainCard>();

        for (int a = 0; a < numberDrawn; a++) {
            TrainCard topCard = trainCardDeck.get(TOP_CARD_INDEX);
            drawnTrainCards.add(topCard);
            trainCardDeck.remove(TOP_CARD_INDEX);
        }

        return drawnTrainCards;
    }


    public ArrayList<DestCard> drawDestCards() {
        final int SIZE_OF_DRAW = 3;
        final int TOP_CARD_INDEX = 0;

        ArrayList<DestCard> drawnDestCards = new ArrayList<DestCard>();
        for (int a = 0; a < SIZE_OF_DRAW; a++) {
            DestCard topCard = destCardDeck.get(TOP_CARD_INDEX);
            drawnDestCards.add(topCard);
            trainCardDeck.remove(TOP_CARD_INDEX);
        }

        return drawnDestCards;
    }


    public void pushBackDestCards(DestCard cardOne, DestCard cardTwo) {
        if (cardOne != null) {
            destCardDeck.add(cardOne);
        }
        if (cardTwo != null) {
            destCardDeck.add(cardTwo);
        }
    }






    void setDestCardDeck(List<DestCard> destCardDeck) {
        this.destCardDeck = destCardDeck;
    }

    void setTrainCardDeck(List<TrainCard> trainCardDeck) {
        this.trainCardDeck = trainCardDeck;
    }

    void setFaceUpTrainCards(List<TrainCard> faceUpTrainCards) {
        this.faceUpTrainCards = faceUpTrainCards;
    }

    void setRouteMap(Map<Integer, Route> routeMap) {
        this.routeMap = routeMap;
    }

    void setDestCardMap(Map<Integer, DestCard> destCardMap) {
        this.destCardMap = destCardMap;
    }

    public Map<Integer, DestCard> getDestCardMap() {
        return destCardMap;
    }

    public Map<Integer, Route> getRouteMap() {
        return routeMap;
    }
}

