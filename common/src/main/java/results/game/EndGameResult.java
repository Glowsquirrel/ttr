package results.game;

import java.util.List;

import results.Result;
import utils.Utils;

/**
 * Created by sjrme on 7/29/17.
 */

public class EndGameResult extends Result {

    protected List<Integer> pointsFromRoutes;
    protected List<Integer> destCardPtsAdded;
    protected List<Integer> destCardPtsSubtracted;
    protected List<Integer> totalPoints;
    protected String ownsLongestRoute;

    protected EndGameResult(){}
    public EndGameResult(List<Integer> pointsFromRoutes, List<Integer> destCardPtsAdded,
                         List<Integer> destCardPtsSubtracted, List<Integer> totalPoints,
                         String ownsLongestRoute){
        super.type = Utils.END_GAME_TYPE;
        this.pointsFromRoutes = pointsFromRoutes;
        this.destCardPtsAdded = destCardPtsAdded;
        this.destCardPtsSubtracted = destCardPtsSubtracted;
        this.totalPoints = totalPoints;
        this.ownsLongestRoute = ownsLongestRoute;
    }
}
