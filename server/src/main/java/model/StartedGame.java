package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container class for started games.
 * @author Stephen Richins
 */

//QUESTION: Do we add an index for the players in the startGame Result?
// TODO: Add result.
public class StartedGame {

    String gameName;
    Map<String, Player> allPlayers = new HashMap<>();
    Board board;

    StartedGame(UnstartedGame unstartedGame) {
        this.gameName = unstartedGame.getGameName();
        preGameSetup(unstartedGame.getUsernamesInGame());
    }

    /**
     * @note Must deal cards before setting up deck for correct error handling.
     * @return True if successful, false otherwise.
     */
    private void preGameSetup(List<String> userNames) {

        board = new Board();
        final int TRAIN_CARD_DRAW = 4;

        for(int a = 0; a < userNames.size(); a++) {

            Player newPlayer = new Player(userNames.get(a));
            newPlayer.addTrainCards(board.drawTrainCards(TRAIN_CARD_DRAW));
            newPlayer.addDestCards(board.drawDestCards());

            allPlayers.put(userNames.get(a), newPlayer);
        }
    }

    public void returnFirstRoundDestCard(String playerName, int returnedCardKey)
            throws GamePlayException {

        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {
            DestCard returnedCard = board.getDestCardMap().get(returnedCardKey);
            currentPlayer.removeDestCards(returnedCard, null);
        }
        else {
            throw new GamePlayException("Invalid player name");
        }
    }

    public void drawThreeDestCards(String playerName) throws GamePlayException {
        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {
           currentPlayer.addDestCards(board.drawDestCards());
        }
        else {
            throw new GamePlayException("Invalid player name");
        }
    }

    //How are cardOne and cardTwo converted from the command? Is there any way it could arbitrarily change?
    public void returnDestCards(String playerName, int cardOneKey, int cardTwoKey)
            throws GamePlayException {
        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {

            DestCard cardOne = board.getDestCardMap().get(cardOneKey);
            DestCard cardTwo = board.getDestCardMap().get(cardTwoKey);

            if(currentPlayer.removeDestCards(cardOne, cardTwo)){
                board.pushBackDestCards(cardOne, cardTwo);
            }

        }
        else {
            throw new GamePlayException("Invalid player name");
        }
    }

    public void drawTrainCardFromDeck(String playerName) throws GamePlayException {

        final int TRAIN_CARD_DRAW = 1;
        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {
            currentPlayer.addTrainCards(board.drawTrainCards(TRAIN_CARD_DRAW));
        }
        else {
            throw new GamePlayException("Invalid player name");
        }
    }

    public void drawTrainCardFromFaceUp(String playerName) throws GamePlayException{
        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {

        }
        else {
            throw new GamePlayException("Invalid player name");
        }
    }

    public void claimRoute(String playerName, int routeId) throws GamePlayException {
        Player currentPlayer = allPlayers.get(playerName);
        if (currentPlayer != null) {

        }
        else {
            throw new GamePlayException("Invalid player name");
        }
    }



}
