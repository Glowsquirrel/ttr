package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import results.Result;


/**
 * Container class for started games.
 * @author Stephen Richins
 */

//QUESTION: Do we add an index for the players in the startGame Result?
// TODO: Add result.
public class StartedGame {

    private String gameName;
    private Map<String, Player> allPlayers = new HashMap<>();
    private Board board;
    private List<Result> gameHistory = new ArrayList<>();

    StartedGame(UnstartedGame unstartedGame) {
        this.gameName = unstartedGame.getGameName();
        preGameSetup(unstartedGame.getUsernamesInGame());
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
            newPlayer.addTrainCards(board.drawTrainCards(TRAIN_CARD_DRAW));
            newPlayer.addDestCards(board.drawDestCards());

            allPlayers.put(userNames.get(a), newPlayer);
        }
        return ResultCreator.startGameResults(this);
    }


/********************************DrawDestCards****************************************************/
    public Result drawThreeDestCards(String playerName) throws GamePlayException {

        Player currentPlayer = allPlayers.get(playerName);
        ArrayList<DestCard> drawnDestCards;

        if (currentPlayer != null) {
            drawnDestCards = board.drawDestCards();
            currentPlayer.addDestCards(drawnDestCards);
        }
        else {
            throw new GamePlayException("Invalid player name");
        }
        return ResultCreator.drawDestCardResults(playerName, drawnDestCards, board.getDestCardMap());
    }



    //How are cardOne and cardTwo converted from the command? Is there any way it could arbitrarily change?
    public Result returnDestCard(String playerName, int returnedCardKey)
            throws GamePlayException {

        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {
            DestCard returnedCard = board.getDestCardMap().get(returnedCardKey);
            currentPlayer.removeDestCards(returnedCard, null);
        }
        else {
            throw new GamePlayException("Invalid player name");
        }

        return ResultCreator.returnDestCardResults();
    }




/**********************************DrawTrainCards************************************************/
    public Result drawTrainCardFromDeck(String playerName) throws GamePlayException {

        final int TRAIN_CARD_DRAW = 1;
        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {
            currentPlayer.addTrainCards(board.drawTrainCards(TRAIN_CARD_DRAW));
        }
        else {
            throw new GamePlayException("Invalid player name");
        }
        return ResultCreator.drawTCFromDeckResults();
    }



    public Result drawTrainCardFromFaceUp(String playerName) throws GamePlayException{
        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {

        }
        else {
            throw new GamePlayException("Invalid player name");
        }
        return ResultCreator.drawTCFromFaceUpResults();
    }



    /************************************ClaimRoute*******************************************/
    public Result claimRoute(String playerName, int routeId) throws GamePlayException {
        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {

        }
        else {
            throw new GamePlayException("Invalid player name");
        }

        return ResultCreator.claimRouteResult();
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
