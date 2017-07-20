package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



 class BoardSetUp extends Board {

     private Board board;
     private ArrayList<TrainCard> trainCardDeck = new ArrayList<>();
     private ArrayList<TrainCard> faceUpTrainCards = new ArrayList<>();
     private ArrayList<DestCard> destCardDeck = new ArrayList<>();
     private Map<Integer, Route> routeMap = new HashMap<>();
     private Map<Integer, DestCard> destCardMap = new HashMap<>();

     BoardSetUp (Board board) {
        this.board = board;
    }

     void initializeBoard() {
        destCardMap = DestCard.createDestCardMap();
        board.setDestCardMap(destCardMap);

        routeMap = Route.createRouteMap();
        board.setRouteMap(routeMap);

        initializeTrainCardDeck();
        shuffleTrainCardDeck(null);
        board.setFaceUpTrainCards(faceUpTrainCards);

        drawFaceUpCards();
        board.setTrainCardDeck(trainCardDeck);

        initializeDestCardDeck();
        shuffleDestCarDeck();
        board.setDestCardDeck(destCardDeck);
     }


     private void initializeTrainCardDeck() {

        final int NUM_OF_EACH_COLOR = 12;
        final int NUM_OF_LOCOMOTIVES = 14;
        final int NUM_OF_COLORS = 8;
        final int VALUE_OF_LOCOMOTIVE = 8;

        for (int a = 0; a < NUM_OF_COLORS; a++) {
            for (int b = 0; b < NUM_OF_EACH_COLOR; b++) {
                trainCardDeck.add(TrainCard.getTrainCard(a));
            }
        }

        for (int a = 0; a < NUM_OF_LOCOMOTIVES; a++) {
            trainCardDeck.add(TrainCard.getTrainCard(VALUE_OF_LOCOMOTIVE));
        }
     }

     void shuffleTrainCardDeck(ArrayList<TrainCard> discardedTrainCards) {

        final int NUM_OF_SWITCHES = 1000;
        ArrayList<TrainCard> shuffledDeck;

        if(discardedTrainCards != null) {
            shuffledDeck = discardedTrainCards;
        } else {
            shuffledDeck = trainCardDeck;
        }

        Random randomNumGenerator = new Random();
        for (int a = 0; a < NUM_OF_SWITCHES; a++) {

            int positionOne = randomNumGenerator.nextInt(shuffledDeck.size());
            int positionTwo = randomNumGenerator.nextInt(shuffledDeck.size());

            TrainCard savedCard = shuffledDeck.get(positionOne);
            shuffledDeck.set(positionOne, shuffledDeck.get(positionTwo));
            shuffledDeck.set(positionTwo, savedCard);
        }
         trainCardDeck = shuffledDeck;
     }

     private void drawFaceUpCards() {
         final int FIFTH_INDEX = 4;

         faceUpTrainCards = (ArrayList<TrainCard>)trainCardDeck.subList(0, FIFTH_INDEX);
         for (int a = 0; a < FIFTH_INDEX; a++) {
             trainCardDeck.remove(0);
         }
     }

     private void initializeDestCardDeck() {
        destCardDeck = (ArrayList<DestCard>)destCardMap.values();
     }

     private void shuffleDestCarDeck(){

         final int NUM_OF_SWITCHES = 1000;
         ArrayList<DestCard> shuffledDeck = destCardDeck;

         Random randomNumGenerator = new Random();
         for (int a = 0; a < NUM_OF_SWITCHES; a++) {

             int positionOne = randomNumGenerator.nextInt(shuffledDeck.size());
             int positionTwo = randomNumGenerator.nextInt(shuffledDeck.size());

             DestCard savedCard = shuffledDeck.get(positionOne);
             shuffledDeck.set(positionOne, shuffledDeck.get(positionTwo));
             shuffledDeck.set(positionTwo, savedCard);
         }

         destCardDeck = shuffledDeck;

     }



}
