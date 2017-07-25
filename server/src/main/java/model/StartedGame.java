package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import results.Result;
import results.game.ChatResult;
import results.game.ClaimRouteResult;
import results.game.DrawThreeDestCardsResult;
import results.game.DrawTrainCardFromDeckResult;
import results.game.DrawTrainCardFromFaceUpResult;
import results.game.GameHistoryResult;
import results.game.ReplaceFaceUpCardsResult;
import results.game.ReturnFirstDestCardResult;
import results.game.StartGameResult;

/**
 * Container class for started games.
 * @author Stephen Richins
 */

//QUESTION: Do we add an index for the players in the startGame Result?
// TODO: continuous routes; check for low card amount; end game; check for low car amount;
class StartedGame {

    private String gameName;
    private Map<String, Player> allPlayers = new HashMap<>();
    private List<String> playerOrder = new ArrayList<>();
    private Board board;
    private List<Chat> allChats = new ArrayList<>();
    private boolean replaceFaceUpFlag = false;
    private int turnPointer = 0;
    private TurnState turnState = TurnState.FIRST_ROUND;
    private Result gameHistory;
    StartedGame(UnstartedGame unstartedGame) {
        this.gameName = unstartedGame.getGameName();
    }

/************************************START GAME************************************************/
    /**
     * Must deal cards before setting up deck for correct error handling.
     * @return True if successful, false otherwise.
     */
    List<Result> preGameSetup(List<String> userNames) {

        board = new Board();
        final int TRAIN_CARD_DRAW = 4;

        for(int a = 0; a < userNames.size(); a++) {
            Player newPlayer = new Player(userNames.get(a));
            newPlayer.addTrainCards(board.drawTrainCardsFromDeck(TRAIN_CARD_DRAW));
            newPlayer.addDestCards(board.drawDestCards());
            newPlayer.setPlayerColor(a);
            allPlayers.put(userNames.get(a), newPlayer);
            playerOrder.add(userNames.get(a));
        }
        replaceFaceUpFlag = board.getReplaceFaceUpFlag();
        return startGameResults();
    }

    private List<Result> startGameResults() {

        Set<Player> playerSet = new HashSet<>();
        playerSet.addAll(allPlayers.values());
        List<Result> allResults = new ArrayList<>();

        List<String> playerNames = new ArrayList<>();
        for (Player player : playerSet) {
            playerNames.add(player.getUsername());
        }

        for (Player player : playerSet) {

            List<Integer> destCardKeys = new ArrayList<>();
            for (DestCard destCard : player.getNewlyDrawnDestCards()) {
                destCardKeys.add(DestCard.getDestCardKey(destCard));
            }

            Result nextPlayer = new StartGameResult(player.getUsername(),
                                                    gameName,
                                                    playerNames,
                                                    destCardKeys,
                                                    player.getTrainCardCodes(),
                                                    board.getFaceUpCardCodes());
            allResults.add(nextPlayer);
        }

        return allResults;
    }



/********************************DrawDestCards*****************************************************/
     Result drawThreeDestCards(String playerName) throws GamePlayException {

        throwIfNotPlayersTurn(playerName);
        switchTurnState(CommandType.DRAW_THREE_DEST_CARDS);

        Player currentPlayer = allPlayers.get(playerName);
        ArrayList<DestCard> drawnDestCards;

        if (currentPlayer != null) {
            drawnDestCards = board.drawDestCards();
            currentPlayer.addDestCards(drawnDestCards);
        } else {
            throw new GamePlayException("Invalid player name");
        }

        String message = playerName + " drew 3 destination cards.";
        setGameHistory(playerName, message, -1);
        return drawDestCardResults(playerName, drawnDestCards);
    }

    private Result drawDestCardResults(String playerName, List<DestCard> drawnDestCards) {

        List<Integer> drawnCardKeys = new ArrayList<>();
        for (DestCard destCard : drawnDestCards) {
            drawnCardKeys.add(DestCard.getDestCardKey(destCard));
        }

        return new DrawThreeDestCardsResult(playerName, drawnCardKeys);
    }


    Result returnDestCard(String playerName, int returnedCardKey)
            throws GamePlayException {

        Player currentPlayer = allPlayers.get(playerName);

        if (currentPlayer != null) {

            throwIfNotPlayersTurn(playerName);
            if (currentPlayer.invalidDestCard(DestCard.getDestCardByID(returnedCardKey))) {
                throw new GamePlayException("You have not drawn that destination card.");
            }

            if (returnedCardKey > 0) {
                switchTurnState(CommandType.RETURN_DEST_CARD);
            } else {
                switchTurnState(CommandType.RETURN_NO_DEST_CARD);
            }

            DestCard returnedCard = board.getDestCardMap().get(returnedCardKey);
            currentPlayer.removeDestCards(returnedCard, null);
            board.pushBackDestCards(returnedCard);

        } else {
            throw new GamePlayException("Invalid player name");
        }

        String message = playerName + " returned a destination card.";
        setGameHistory(playerName, message, -1);
        return new ReturnFirstDestCardResult(playerName, returnedCardKey);
    }



/**********************************DrawTrainCards************************************************/
    Result drawTrainCardFromDeck(String playerName) throws GamePlayException {

        final int TRAIN_CARD_DRAW = 1;
        Player currentPlayer = allPlayers.get(playerName);
        List<TrainCard> trainCard;

        if (currentPlayer != null) {
            throwIfNotPlayersTurn(playerName);
            switchTurnState(CommandType.DRAW_TRAIN_CARD_FROM_DECK);

            trainCard = board.drawTrainCardsFromDeck(TRAIN_CARD_DRAW);
            currentPlayer.addTrainCards(trainCard);
        } else {
            throw new GamePlayException("Invalid player name");
        }
        board.reshuffleIfEmpty();

        String message = playerName + " drew a train card from the deck.";
        setGameHistory(playerName, message, -1);
        return new DrawTrainCardFromDeckResult(playerName, TrainCard.getTrainCardKey(trainCard.get(0)));
    }


    Result drawTrainCardFromFaceUp(String playerName, int index) throws GamePlayException{

        Player currentPlayer = allPlayers.get(playerName);
        TrainCard drawnCard;

        if (currentPlayer != null) {
            throwIfNotPlayersTurn(playerName);
            final int LOCOMOTIVE_INDEX = 8;

            if (index == LOCOMOTIVE_INDEX) {
                switchTurnState(CommandType.FACEUP_LOCOMOTIVE);
            } else {
                switchTurnState(CommandType.FACEUP_NON_LOCOMOTIVE);
            }
            drawnCard = board.drawFaceUpCard(index);
            replaceFaceUpFlag = board.getReplaceFaceUpFlag();

        } else {
            throw new GamePlayException("Invalid player name");
        }

        String message = playerName + " drew a face-up train card.";
        setGameHistory(playerName, message, -1);
        return new DrawTrainCardFromFaceUpResult(playerName, TrainCard.getTrainCardKey(drawnCard));
    }


    Result replaceFaceUpCards() {
        List<Integer> newFaceUpCards = board.replaceFaceUpCards();
        replaceFaceUpFlag = board.getReplaceFaceUpFlag();
        return new ReplaceFaceUpCardsResult(newFaceUpCards);
    }

    /************************************ClaimRoute*******************************************/
    Result claimRoute(String playerName, int routeId, List<Integer> trainCards) throws GamePlayException {

        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {
            List<TrainCard> returnedTrainCards = convertKeysToTrainCards(trainCards);
            throwIfNotPlayersTurn(playerName);


            if(board.routeIsClaimed(routeId)) {
                throw new GamePlayException("Route has already been claimed.");
            }
            if(board.incorrectCards(routeId, returnedTrainCards)){
                throw new GamePlayException("Cannot claim route with cards given.");
            }
            if (board.notEnoughCars(routeId, currentPlayer.getNumOfCars())) {
                throw new GamePlayException("Not enough cars to claim route.");
            }
            if (board.doubleRouteFailure(routeId, allPlayers.size(), playerName)) {
                throw new GamePlayException("Can not claim double route.");
            }

            switchTurnState(CommandType.CLAIM_ROUTE);
            board.claimRoute(routeId, currentPlayer.getPlayerColor(), playerName);
            currentPlayer.addNumOfRoutes();
            currentPlayer.addScore(board.getRoutePoints(routeId));
            currentPlayer.removeTrainCards(returnedTrainCards);
            board.discardTrainCards(returnedTrainCards);

        } else {
            throw new GamePlayException("Invalid player name");
        }

        String message = playerName + " claimed route " + Integer.toString(routeId);
        setGameHistory(playerName, message, routeId);
        return new ClaimRouteResult(playerName, routeId);
    }

    public PlayerColor retrievePlayerColor(String playerName) {
        return allPlayers.get(playerName).getPlayerColor();
    }

    private List<TrainCard> convertKeysToTrainCards(List<Integer> trainCards) {
        List<TrainCard> returnTrainCards =  new ArrayList<>();
        for(Integer a : trainCards) {
            returnTrainCards.add(TrainCard.getTrainCard(a));
        }
        return returnTrainCards;
    }


    /***********************************CHAT*************************************/


    Result addChat(String playerName, String message) {
        allChats.add(new Chat(playerName, message));
        return new ChatResult(playerName, message);
    }

    /**********************************TURN STATE***************************************/
    private boolean throwIfNotPlayersTurn(String playerName) throws GamePlayException {
        if(playerOrder.get(turnPointer).equals(playerName)) {
            return true;
        }
        throw new GamePlayException("Not your turn!");
    }

    private void advancePlayerTurn() {
        if (playerOrder.size()-1 == turnPointer){
            turnPointer = 0;
        } else {
            turnPointer++;
        }
        turnState = TurnState.BEFORE_TURN;
    }


    private void switchTurnState(CommandType commandType) throws GamePlayException {
        switch (turnState) {
            case FIRST_ROUND:
                  switchFirstRound(commandType);
                  break;
            case BEFORE_TURN:
                  switchBeforeTurn(commandType);
                  break;
            case DREW_DEST_CARDS:
                 switchDrawDest(commandType);
                 break;
            case RETURNED_ONE_DEST_CARD:
                 switchReturnedOneDestCard(commandType);
                 break;
            case DREW_ONE_TRAIN_CARD:
                 switchDrawTrainCard(commandType);
                 break;
            default:
                throw new GamePlayException("What in the world");
        }
    }

    private void switchFirstRound(CommandType commandType) throws GamePlayException {
        if (commandType != CommandType.RETURN_DEST_CARD && commandType != CommandType.RETURN_NO_DEST_CARD) {
            throw new GamePlayException("Illegal move. You may only return dest cards.");
        }

        if (playerOrder.size()-1 == turnPointer){
            turnPointer = 0;
        } else {
            turnPointer++;
        }
        if (turnPointer == 0) {
            turnState = TurnState.BEFORE_TURN;
        }
    }

    private void switchBeforeTurn(CommandType commandType) throws GamePlayException {
        switch (commandType) {
            case DRAW_THREE_DEST_CARDS:
                turnState = TurnState.DREW_DEST_CARDS;
                break;
            case DRAW_TRAIN_CARD_FROM_DECK:
                turnState = TurnState.DREW_ONE_TRAIN_CARD;
                break;
            case FACEUP_NON_LOCOMOTIVE:
                turnState = TurnState.DREW_ONE_TRAIN_CARD;
                break;
            case FACEUP_LOCOMOTIVE:
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
                break;
            case CLAIM_ROUTE:
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
                break;
            default:
                throw new GamePlayException("Illegal move. Cannot return cards before any are drawn.");
        }
    }


    private void switchDrawDest(CommandType commandType) throws GamePlayException {
        switch (commandType) {
            case RETURN_DEST_CARD: {
                turnState = TurnState.RETURNED_ONE_DEST_CARD;
            }
            case RETURN_NO_DEST_CARD: {
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
            }
            default:{
                throw new GamePlayException("Illegal move. You may only return dest cards.");
            }
        }
    }

    private void switchReturnedOneDestCard(CommandType commandType) throws GamePlayException {
        switch (commandType) {

            case RETURN_DEST_CARD:
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
                break;
            case RETURN_NO_DEST_CARD:
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
                break;
            default:
                throw new GamePlayException("Illegal Move. You may only return dest cards.");
        }
    }

    private void switchDrawTrainCard(CommandType commandType) throws GamePlayException {
        switch (commandType) {
            case DRAW_TRAIN_CARD_FROM_DECK: {
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
            }
            case FACEUP_NON_LOCOMOTIVE: {
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
            }
            default:
                throw new GamePlayException("Illegal move. You may only draw train cards.");
        }
    }


    private enum TurnState {
        FIRST_ROUND("First Round"),
        BEFORE_TURN("Before turn"),
        DREW_DEST_CARDS("Drew Dest Cards"),
        RETURNED_ONE_DEST_CARD("Returned One Dest Card"),
        DREW_ONE_TRAIN_CARD("Drew One Train Card");

        String prettyName;
        TurnState(String prettyName) {
            this.prettyName = prettyName;
        }

    }

    private enum CommandType {
          DRAW_THREE_DEST_CARDS,
          RETURN_DEST_CARD,
          RETURN_NO_DEST_CARD,
          DRAW_TRAIN_CARD_FROM_DECK,
          FACEUP_NON_LOCOMOTIVE,
          FACEUP_LOCOMOTIVE,
          CLAIM_ROUTE,
    }

    //TODO: change ServerModel; Draw from face up gamehist?
    //TODO; Advise client model on destCard -1; Advise client model on face up game hist + Replace Face UP
    /*************************************Getters/Setters**********************************************/

    private void setGameHistory(String playerName, String message, int routeNumber){
        Player player = allPlayers.get(playerName);
        gameHistory = new GameHistoryResult(playerName, message,
                player.getNumOfCars(), player.getSizeOfTrainCardHand(),
                player.getSizeOfDestCardHand(), player.getNumOfRoutesOwned(),
                player.getPoints(), routeNumber, board.getTrainCardDeckSize(), board.getDestCardDeck().size());
    }
    Result getGameHistory() {
        return gameHistory;
    }

    public String getGameName() {
        return gameName;
    }

    Map<String, Player> getAllPlayers() {
        return allPlayers;
    }

    boolean getReplaceFaceUpFlag() {
        return replaceFaceUpFlag;
    }

    void printBoardState() {
         List<Boolean> destCardSeen = new ArrayList<>();
         for (int a = 0; a < 30; a++) {
             destCardSeen.add(false);
         }

         int numOfPurpleCards = 0;
         int numOfWhiteCards = 0;
         int numOfBlueCards = 0;
         int numOfYellowCards = 0;
         int numOfOrangeCards = 0;
         int numOfBlackCards = 0;
         int numOfRedCards = 0;
         int numOfGreenCards = 0;
         int numOfWildCards = 0;

        System.out.println("BOARD");
        System.out.println("Num of train cards in deck: " + board.getTrainCardDeck().size());
        System.out.println("Num of dest cards in deck: " + board.getDestCardDeck().size() + "\n");
        System.out.println("Turn state for next turn: " + turnState.prettyName);
        System.out.println("Next person's turn" + playerOrder.get(turnPointer));

        for (int a = 0; a < allPlayers.size(); a++) {
            Player player = allPlayers.get(playerOrder.get(a));
            System.out.println("Player: " + player.getUsername());
            System.out.println("  Size of train card hand: " + player.getSizeOfTrainCardHand());

            numOfRedCards += player.getNumOfRedCards();
            System.out.println("    Red: " + player.getNumOfRedCards());

            numOfGreenCards += player.getNumOfGreenCards();
            System.out.println("    Green: " + player.getNumOfGreenCards());

            numOfBlueCards += player.getNumOfBlueCards();
            System.out.println("    Blue: " + player.getNumOfBlueCards());

            numOfYellowCards += player.getNumOfYellowCards();
            System.out.println("    Yellow: " + player.getNumOfYellowCards());

            numOfBlackCards += player.getNumOfBlackCards();
            System.out.println("    Black: " + player.getNumOfBlackCards());

            numOfPurpleCards += player.getNumOfPurpleCards();
            System.out.println("    Purple: " + player.getNumOfPurpleCards());

            numOfOrangeCards += player.getNumOfOrangeCards();
            System.out.println("    Orange: " + player.getNumOfOrangeCards());

            numOfWhiteCards += player.getNumOfWhiteCards();
            System.out.println("    White: " + player.getNumOfWhiteCards());

            numOfWhiteCards += player.getNumOfWhiteCards();
            System.out.println("    Wild: " + player.getNumOfWildCards());

            System.out.println("  Size of dest card hand:  " + player.getSizeOfDestCardHand());
            for (int b = 0; b < player.getDestCards().size(); b++) {
                DestCard destCard = player.getDestCards().get(b);
                int destCardKey = DestCard.getDestCardKey(destCard);
                if (!destCardSeen.get(destCardKey)){
                    destCardSeen.set(destCardKey, true);
                } else {
                    System.out.println("ERROR: DEST CARD SEEN TWICE.");
                }

                System.out.println("  " + destCardKey);
            }
            System.out.println("  Size of newlydrawndest:  " + player.getNewlyDrawnDestCards().size());
        }
        numOfRedCards += board.getNumOfRedCards();
        numOfGreenCards += board.getNumOfGreenCards();
        numOfBlueCards += board.getNumOfBlueCards();
        numOfYellowCards += board.getNumOfYellowCards();
        numOfBlackCards += board.getNumOfBlackCards();
        numOfPurpleCards += board.getNumOfPurpleCards();
        numOfOrangeCards += board.getNumOfOrangeCards();
        numOfWhiteCards += board.getNumOfWhiteCards();
        numOfWildCards += board.getNumOfWildCards();

        System.out.println("Game number of red: " + numOfRedCards);
        System.out.println("Game number of green: " + numOfGreenCards);
        System.out.println("Game number of blue: " + numOfBlueCards);
        System.out.println("Game number of yellow: " + numOfYellowCards);
        System.out.println("Game number of black: " + numOfBlackCards);
        System.out.println("Game number of purple: " + numOfPurpleCards);
        System.out.println("Game number of orange: " + numOfOrangeCards);
        System.out.println("Game number of white: " + numOfWhiteCards);
        System.out.println("Game number of wild: " + numOfWildCards);

        for (int a = 0; a < board.getDestCardDeck().size(); a++) {
            DestCard destCard = board.getDestCardDeck().get(a);
            int destCardKey = DestCard.getDestCardKey(destCard);

            if (!destCardSeen.get(destCardKey)){
                destCardSeen.set(destCardKey, true);
            } else {
                System.out.println("ERROR: DEST CARD SEEN TWICE.");
            }
        }

        boolean success = true;
        for (int a = 0; a < destCardSeen.size(); a++) {
            if (!destCardSeen.get(a)){
                success = false;
                break;
            }
        }

        if (success){
            System.out.println("All dest cards accounted for.");
        } else {
            System.out.println("ERROR: MISSING DEST CARDS");
        }

        System.out.println("CLAIMED ROUTES: ");
        Set<Route> routeSet = new HashSet<>(board.getRouteMap().values());
    }

}
