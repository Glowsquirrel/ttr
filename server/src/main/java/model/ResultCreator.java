package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import results.Result;
import results.game.DrawThreeDestCardsResult;
import results.game.StartGameResult;

/**
 * Created by sjrme on 7/20/17.
 */

public class ResultCreator {



    /************************************START GAME************************************************/
    public static List<Result> startGameResults(StartedGame game) {

        Set<Player> playerSet = (Set<Player>)game.getAllPlayers().values();
        List<Result> allResults = new ArrayList<>();

        List<String> playerNames = new ArrayList<>();
        for (Player player : playerSet) {
            playerNames.add(player.getUsername());
        }

        for (Player player : playerSet) {

            List<Integer> destCardKeys = new ArrayList<>();
            for (DestCard destCard : player.getDestCards()) {
                destCardKeys.add(getDestCardInt(destCard, game.getBoard().getDestCardMap()));
            }

            allResults.add(new StartGameResult(player.getUsername(),
                    game.getGameName(),
                    playerNames,
                    destCardKeys,
                    player.getTrainCardCodes(),
                    game.getBoard().getFaceUpCardCodes()));
        }
        return null;
    }

    private static int getDestCardInt(DestCard comparedDestCard, Map<Integer, DestCard> destCardMap) {

        Set<DestCard> allDestCards = (Set<DestCard>)destCardMap.values();
        for(DestCard destCard : allDestCards) {
            if (destCard.equals(comparedDestCard)){
                return destCard.getMapValue();
            }
        }
        return -1;
    }

    /**
     * First Result goes to client making the cammand, the second to the rest.
     * @param drawnDestCards
     * @return
     */
    public static Result drawDestCardResults(String playerName, List<DestCard> drawnDestCards, Map<Integer, DestCard> destCardMap) {

        List<Integer> drawnCardKeys = new ArrayList<>();
        for (DestCard destCard : drawnDestCards) {
            drawnCardKeys.add(getDestCardInt(destCard, destCardMap));
        }

        return new DrawThreeDestCardsResult(playerName, drawnCardKeys);

    }

    public static Result returnDestCardResults() {
        return null;
    }

    public static Result drawTCFromDeckResults() {
        return null;
    }

    public static Result drawTCFromFaceUpResults() {
        return null;
    }

    public static Result claimRouteResult() {
        return null;
    }
}
