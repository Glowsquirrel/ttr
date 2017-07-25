package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static model.TrainCard.WILD;


public class Board {

    private List<TrainCard> trainCardDeck = new ArrayList<>();
    private List<TrainCard> faceUpTrainCards = new ArrayList<>();
    private List<TrainCard> discardedTrainCards = new ArrayList<>();
    private List<DestCard> destCardDeck = new ArrayList<>();

    private Map<Integer, Route> routeMap = new HashMap<>();
    private Map<Integer, DestCard> destCardMap = new HashMap<>();
    private boolean replaceFaceUpFlag = false;



    Board() {
        destCardMap = DestCard.getDestCardMap();
        routeMap = Route.createRouteMap();

        initializeTrainCardDeck();
        shuffleTrainCardDeck(true);
        setFaceUpTrainCards(faceUpTrainCards);

        drawFaceUpCards();
        setTrainCardDeck(trainCardDeck);

        initializeDestCardDeck();
        shuffleDestCarDeck();
        setDestCardDeck(destCardDeck);

        countLocomotives();
    }

    /***********************************BOARD SETUP/SHUFFLING*****************************************/
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

    private void shuffleTrainCardDeck(boolean setUp) {

        final int NUM_OF_SWITCHES = 1000;
        List<TrainCard> shuffledDeck;

        if (!setUp) {
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
        discardedTrainCards.clear();
    }

    //Sublist takes from 0 to one below the second parameter.
    private void drawFaceUpCards() {
        final int FIFTH_CARD = 5;

        faceUpTrainCards = new ArrayList<>(trainCardDeck.subList(0, FIFTH_CARD));
        for (int a = 0; a < FIFTH_CARD; a++) {
            trainCardDeck.remove(0);
        }
        countLocomotives();
    }

    private void initializeDestCardDeck() {
        destCardDeck.clear();
        destCardDeck.addAll(destCardMap.values());
    }

    private void shuffleDestCarDeck() {

        final int NUM_OF_SWITCHES = 1000;
        List<DestCard> shuffledDeck = destCardDeck;

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

    private void countLocomotives() {
        replaceFaceUpFlag = false;

        int numOfLocomotives = 0;
        final int LOCOMOTIVE_INDEX = 8;
        final int LOCOMOTIVE_LIMIT = 2;
        for (TrainCard trainCard : faceUpTrainCards) {
            if (TrainCard.getIntByTrainCard(trainCard) == LOCOMOTIVE_INDEX) {
                numOfLocomotives++;
            }
        }

        if (numOfLocomotives > LOCOMOTIVE_LIMIT) {
            replaceFaceUpFlag = true;
        }

    }

    /**********************************GAMEPLAY************************************************/

    ArrayList<TrainCard> drawTrainCardsFromDeck(int numberDrawn) {

        final int TOP_CARD_INDEX = 0;
        ArrayList<TrainCard> drawnTrainCards = new ArrayList<>();

        for (int a = 0; a < numberDrawn; a++) {
            TrainCard topCard = trainCardDeck.get(TOP_CARD_INDEX);
            drawnTrainCards.add(topCard);
            trainCardDeck.remove(TOP_CARD_INDEX);
        }

        return drawnTrainCards;
    }


    ArrayList<DestCard> drawDestCards() {
        final int SIZE_OF_DRAW = 3;
        final int TOP_CARD_INDEX = 0;

        ArrayList<DestCard> drawnDestCards = new ArrayList<>();
        for (int a = 0; a < SIZE_OF_DRAW; a++) {
            DestCard topCard = destCardDeck.get(TOP_CARD_INDEX);
            drawnDestCards.add(topCard);
            destCardDeck.remove(TOP_CARD_INDEX);
        }

        return drawnDestCards;
    }

    void pushBackDestCards(DestCard cardOne) {
        if (cardOne != null) {
            destCardDeck.add(cardOne);
        }
    }

    List<Integer> getFaceUpCardCodes() {
        List<Integer> faceUpCodes = new ArrayList<>();

        for (TrainCard trainCard : faceUpTrainCards) {
            faceUpCodes.add(TrainCard.getIntByTrainCard(trainCard));
        }
        return faceUpCodes;
    }

    TrainCard drawFaceUpCard(int index) {

        final int TOP_CARD_INDEX = 0;

        TrainCard returnedCard = faceUpTrainCards.get(index);
        faceUpTrainCards.set(index, trainCardDeck.get(TOP_CARD_INDEX));
        trainCardDeck.remove(TOP_CARD_INDEX);

        countLocomotives();
        return returnedCard;
    }

    List<Integer> replaceFaceUpCards() {
        trainCardDeck.addAll(faceUpTrainCards);
        faceUpTrainCards.clear();
        final int FIFTH_INDEX = 4;

        for (int a = 0; a < FIFTH_INDEX; a++) {
            faceUpTrainCards.add(trainCardDeck.get(0));
            trainCardDeck.remove(0);
        }

        countLocomotives();
        return getFaceUpCardCodes();
    }

    void discardTrainCards(List<TrainCard> discardedTrainCards) {
        this.discardedTrainCards.addAll(discardedTrainCards);
    }

    void reshuffleIfEmpty() {
        if (trainCardDeck.size() == 0){
            shuffleTrainCardDeck(false);
        }
    }

    boolean getReplaceFaceUpFlag() {
        return replaceFaceUpFlag;
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

    int getRoutePoints(int routeId) {
        return routeMap.get(routeId).getPointValue();
    }
    Map<Integer, DestCard> getDestCardMap() {
        return destCardMap;
    }

    Map<Integer, Route> getRouteMap() {
        return routeMap;
    }

    boolean incorrectCards(int routeId, List<TrainCard>returnedTrainCards) {

        Route route =  routeMap.get(routeId);
        if (route.getLength() != returnedTrainCards.size()){
            return false;
        }

        TrainCard routeColor = route.getColor();
        TrainCard firstTrainCard = returnedTrainCards.get(0);
        for (TrainCard currentCard : returnedTrainCards) {
            if (currentCard != firstTrainCard) {
                return false;
            }
        }
        if (routeColor == WILD) {
            return true;
        }
        else if (routeColor == firstTrainCard){
            return true;
        }

        return false;
    }

    boolean notEnoughCars(int routeId, int numOfCars) {
        Route route = routeMap.get(routeId);
        return (numOfCars < route.getLength());
    }
    //set owner, set color, set claimed.
    //

    boolean doubleRouteFailure(int routeId, int numOfPlayers, String playerName){
        Route route = routeMap.get(routeId);
        if (route.sisterRouteIsClaimed()){

            int sisterRouteKey = route.getSisterRouteKey();
            Route sisterRoute = routeMap.get(sisterRouteKey);
            String sisterRouteOwner = sisterRoute.getOwner();

            if (sisterRouteOwner.equals(playerName)){
                return true;
            }
            if (numOfPlayers < 4){
                return true;
            }
        }

        return false;
    }
    boolean routeIsClaimed(int routeId){
        return routeMap.get(routeId).isClaimed();
    }
    void claimRoute(int routeId, PlayerColor playerColor, String playerName){
        Route route = routeMap.get(routeId);

        route.setOwner(playerName);
        route.setClaimedColor(playerColor);
        route.claim();

        if (route.isDoubleRoute()){
            int sisterRouteKey = route.getSisterRouteKey();
            Route sisterRoute = routeMap.get(sisterRouteKey);
            sisterRoute.setSisterRouteClamed();
        }
    }
    //GETTERS USED FOR TESTING
    public List<TrainCard> getTrainCardDeck() {
        return trainCardDeck;
    }

    public List<TrainCard> getFaceUpTrainCards() {
        return faceUpTrainCards;
    }

    public List<TrainCard> getDiscardedTrainCards() {
        return discardedTrainCards;
    }

    public List<DestCard> getDestCardDeck() {
        return destCardDeck;
    }
}
