package results.game;

import java.util.List;

import results.Result;
import utils.Utils;

public class AllPlayerInfoResult extends Result{
    protected List<String> usernames;
    protected List<Integer> numRoutesOwned;
    protected List<Integer> numCardsHeld;
    protected List<Integer> numTrainCars;
    protected List<Integer> score;

    public AllPlayerInfoResult(){
        super.type = Utils.ALL_PLAYER_INFO_TYPE;
    }

    public void addPlayerInfo(String username, int numRoutesOwned, int numCardsHeld, int numTrainCars, int score){
        this.usernames.add(username);
        this.numRoutesOwned.add(numRoutesOwned);
        this.numCardsHeld.add(numCardsHeld);
        this.numTrainCars.add(numTrainCars);
        this.score.add(score);
    }
}
