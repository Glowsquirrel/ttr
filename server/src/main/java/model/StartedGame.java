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
import results.game.ReplaceFaceUpCardsResult;
import results.game.ReturnFirstDestCardResult;
import results.game.StartGameResult;


/**
 *
 * Container class for started games.
 * @author Stephen Richins
 */

//QUESTION: Do we add an index for the players in the startGame Result?
// TODO: discard pile; continuous routes; check for correct destCard return, check for low card amount; double route enabled;
public class StartedGame {

    private String gameName;
    private Map<String, Player> allPlayers = new HashMap<>();
    private List<String> playerOrder = new ArrayList<>();
    private Board board;
    private List<Result> gameHistory = new ArrayList<>();
    private List<Chat> allChats = new ArrayList<>();
    private boolean replaceFaceUpFlag = false;
    private int turnPointer = 0;
    private TurnState turnState = TurnState.BEFORE_TURN;

    StartedGame(UnstartedGame unstartedGame) {
        this.gameName = unstartedGame.getGameName();
    }


/************************************START GAME************************************************/
    /**
     * @note Must deal cards before setting up deck for correct error handling.
     * @return True if successful, false otherwise.
     */
    public List<Result> preGameSetup(List<String> userNames) {

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
                destCardKeys.add(getDestCardInt(destCard, board.getDestCardMap()));
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

    private static int getDestCardInt(DestCard comparedDestCard, Map<Integer, DestCard> destCardMap) {

        Set<DestCard> allDestCards = new HashSet<>(destCardMap.values());
        for(DestCard destCard : allDestCards) {
            if (destCard.equals(comparedDestCard)){
                return destCard.getMapValue();
            }
        }
        return -1;
    }

/********************************DrawDestCards*****************************************************/
    public Result drawThreeDestCards(String playerName) throws GamePlayException {

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

        return drawDestCardResults(playerName, drawnDestCards);
    }


    private Result drawDestCardResults(String playerName, List<DestCard> drawnDestCards) {


        List<Integer> drawnCardKeys = new ArrayList<>();
        for (DestCard destCard : drawnDestCards) {
            drawnCardKeys.add(getDestCardInt(destCard, board.getDestCardMap()));
        }

        return new DrawThreeDestCardsResult(playerName, drawnCardKeys);

    }

    //How are cardOne and cardTwo converted from the command? Is there any way it could arbitrarily change?
    public Result returnDestCard(String playerName, int returnedCardKey)
            throws GamePlayException {

        throwIfNotPlayersTurn(playerName);
        if (returnedCardKey > 0) {
            switchTurnState(CommandType.RETURN_DEST_CARD);
        }
        else {
            switchTurnState(CommandType.RETURN_NO_DEST_CARD);
        }

        Player currentPlayer = allPlayers.get(playerName);

        if (currentPlayer != null) {
            DestCard returnedCard = board.getDestCardMap().get(returnedCardKey);
            currentPlayer.removeDestCards(returnedCard, null);
            board.pushBackDestCards(returnedCard);
        } else {
            throw new GamePlayException("Invalid player name");
        }

        return new ReturnFirstDestCardResult(playerName, returnedCardKey);
    }




/**********************************DrawTrainCards************************************************/
    public Result drawTrainCardFromDeck(String playerName) throws GamePlayException {
        throwIfNotPlayersTurn(playerName);
        switchTurnState(CommandType.DRAW_TRAIN_CARD_FROM_DECK);

        final int TRAIN_CARD_DRAW = 1;
        Player currentPlayer = allPlayers.get(playerName);
        List<TrainCard> trainCard;

        if (currentPlayer != null) {
            trainCard = board.drawTrainCardsFromDeck(TRAIN_CARD_DRAW);
            currentPlayer.addTrainCards(trainCard);
        } else {
            throw new GamePlayException("Invalid player name");
        }

        return new DrawTrainCardFromDeckResult(playerName, TrainCard.getTrainCardInt(trainCard.get(0)));
    }



    public Result drawTrainCardFromFaceUp(String playerName, int index) throws GamePlayException{
        throwIfNotPlayersTurn(playerName);
        final int LOCOMOTIVE_INDEX = 8;

        if (index == LOCOMOTIVE_INDEX) {
            switchTurnState(CommandType.FACEUP_LOCOMOTIVE);
        } else {
            switchTurnState(CommandType.FACEUP_NON_LOCOMOTIVE);
        }

        switchTurnState(CommandType.RETURN_DEST_CARD);
        Player currentPlayer = allPlayers.get(playerName);
        TrainCard drawnCard;

        if (currentPlayer != null) {
             drawnCard = board.drawFaceUpCard(index);
             replaceFaceUpFlag = board.getReplaceFaceUpFlag();
        } else {
            throw new GamePlayException("Invalid player name");
        }

        return new DrawTrainCardFromFaceUpResult(playerName, TrainCard.getTrainCardInt(drawnCard));
    }


    public Result replaceFaceUpCards() {
        List<Integer> newFaceUpCards = board.replaceFaceUpCards();
        replaceFaceUpFlag = board.getReplaceFaceUpFlag();
        return new ReplaceFaceUpCardsResult(newFaceUpCards);
    }

    /************************************ClaimRoute*******************************************/
    public Result claimRoute(String playerName, int routeId) throws GamePlayException {

        throwIfNotPlayersTurn(playerName);
        switchTurnState(CommandType.CLAIM_ROUTE);

        Player currentPlayer = allPlayers.get(playerName);

        if (currentPlayer != null) {
            Route route = board.getRouteMap().get(routeId);

            if (route.claimRoute(retrievePlayerColor((playerName)), allPlayers.size())){
                board.discardTrainCards(route.getColor(), route.getLength());
            } else {
                throw new GamePlayException("Cannot claim double route.");
            }

        } else {
            throw new GamePlayException("Invalid player name");
        }

        return new ClaimRouteResult(playerName, routeId);
    }

    public PlayerColor retrievePlayerColor(String playerName) {
        return allPlayers.get(playerName).getPlayerColor();
    }

    /*************************************Getters**********************************************/
    public List<Result> getGameHistory() {
        return gameHistory;
    }

    public String getGameName() {
        return gameName;
    }

    public Map<String, Player> getAllPlayers() {
        return allPlayers;
    }

    public Board getBoard() {
        return board;
    }

    public boolean getReplaceFaceUpFlag() {
        return replaceFaceUpFlag;
    }

    /***********************************CHAT*************************************/


    public Result addChat(String playerName, String message) {
        allChats.add(new Chat(playerName, message));
        return new ChatResult(playerName, message);
    }

    /**********************************TURN STATE***************************************/
    public boolean throwIfNotPlayersTurn(String playerName) throws GamePlayException {
        if(playerOrder.get(turnPointer).equals(playerName)) {
            return true;
        }
        throw new GamePlayException("Not your turn!");
    }

    public void advancePlayerTurn() {
        if (playerOrder.size()-1 == turnPointer){
            turnPointer = 0;
        } else {
            turnPointer++;
        }
        turnState = TurnState.BEFORE_TURN;
    }


    private void switchTurnState(CommandType commandType) throws GamePlayException {
        switch (turnState) {
            case BEFORE_TURN:
                  switchBeforeTurn(commandType);

            case DREW_DEST_CARDS:
                 switchDrawDest(commandType);

            case RETURNED_ONE_DEST_CARD:
                 switchReturnedOneDestCard(commandType);

            case DREW_ONE_TRAIN_CARD:
                 switchDrawTrainCard(commandType);

            default:
                throw new GamePlayException("What in the world");
        }
    }

    private void switchBeforeTurn(CommandType commandType) throws GamePlayException {
        switch (commandType) {
            case DRAW_THREE_DEST_CARDS: {
                turnState = TurnState.DREW_DEST_CARDS;
            }
            case DRAW_TRAIN_CARD_FROM_DECK: {
                turnState = TurnState.DREW_ONE_TRAIN_CARD;
            }
            case FACEUP_NON_LOCOMOTIVE: {
                turnState = TurnState.DREW_ONE_TRAIN_CARD;
            }
            case FACEUP_LOCOMOTIVE: {
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
            }
            case CLAIM_ROUTE: {
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
            }
            default: {
                throw new GamePlayException("Illegal move");
            }
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
                throw new GamePlayException("Illegal move");
            }
        }
    }

    private boolean switchReturnedOneDestCard(CommandType commandType) {
        switch (commandType) {

            case RETURN_DEST_CARD: {
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
                return true;
            }
            case RETURN_NO_DEST_CARD: {
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
                return true;
            }
            default:{
                return false;
            }
        }
    }

    private boolean switchDrawTrainCard(CommandType commandType) {
        switch (commandType) {
            case DRAW_TRAIN_CARD_FROM_DECK: {
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
                return true;
            }
            case FACEUP_NON_LOCOMOTIVE: {
                advancePlayerTurn();
                turnState = TurnState.BEFORE_TURN;
                return true;
            }
            default:
                return false;
        }
    }

    private enum TurnState {
        BEFORE_TURN,
        DREW_DEST_CARDS,
        RETURNED_ONE_DEST_CARD,
        DREW_ONE_TRAIN_CARD
    }


      enum CommandType {
          DRAW_THREE_DEST_CARDS,
          RETURN_DEST_CARD,
          RETURN_NO_DEST_CARD,
          DRAW_TRAIN_CARD_FROM_DECK,
          FACEUP_NON_LOCOMOTIVE,
          FACEUP_LOCOMOTIVE,
          CLAIM_ROUTE,
    }


}
