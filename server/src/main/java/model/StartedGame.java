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
// TODO: TURN State; discard pile; continuous routes; check for correct destCard return, check for low card amount; double route enabled; draw 2 locomotives check;
public class StartedGame {

    private String gameName;
    private Map<String, Player> allPlayers = new HashMap<>();
    private Board board;
    private List<Result> gameHistory = new ArrayList<>();
    private List<Chat> allChats = new ArrayList<>();
    private boolean replaceFaceUpFlag = false;

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

        Player currentPlayer = allPlayers.get(playerName);

        if (currentPlayer != null) {
            board.getRouteMap().get(routeId).claimRoute(retrievePlayerColor((playerName)));
            int sizeOfRoute = board.getRouteMap().get(routeId).getLength();
            TrainCard routeType = board.getRouteMap().get(routeId).getColor();
            board.discardTrainCards(routeType, sizeOfRoute);

        } else {
            throw new GamePlayException("Invalid player name");
        }

        return new ClaimRouteResult(playerName, routeId);
    }

    public PlayerColor retrievePlayerColor(String playerName) {
        return allPlayers.get(playerName).getPlayerColor();
    }

    /***********************************Getters*****************************************/
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
    public Result addChat(String playerName, String message) {
        allChats.add(new Chat(playerName, message));
        return new ChatResult(playerName, message);
    }


    public enum TurnState {
        BEFORE_TURN,
        DRAW_DEST,
        DRAW_TRAIN_CARD,
        CLAIM_ROUTE;

        public void setState(String thisFunction) {
            switch(this) {
                case BEFORE_TURN: {

                }
            }
        }
    }


}
