package results.game;

import java.util.List;

import results.Result;
import utils.Utils;

/**
 * Created by sjrme on 7/29/17.
 */

public class EndGameResult extends Result {

    protected List<String> playerNames;
    protected List<String> destCardPtsAdded;
    protected List<String> destCardPtsSubtracted;
    protected List<Integer> totalPoints;
    protected String ownsLongestRoute;

    protected EndGameResult(){}
    public EndGameResult(List<String> playerNames, List<String> destCardPtsAdded,
            List<String> destCardPtsSubtracted, List<Integer> totalPoints, String ownsLongestRoute){
        super.type = Utils.END_GAME_TYPE;
        this.playerNames = playerNames;
        this.destCardPtsAdded = destCardPtsAdded;
        this.destCardPtsSubtracted = destCardPtsSubtracted;
        this.totalPoints = totalPoints;
        this.ownsLongestRoute = ownsLongestRoute;
    }
}
