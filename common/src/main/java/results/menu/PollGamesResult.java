package results.menu;

import java.util.List;

import results.Result;
import model.UnstartedGame;
import utils.Utils;

public class PollGamesResult extends Result {

    protected List<UnstartedGame> gameList;

    protected PollGamesResult(){}
    public PollGamesResult(List<UnstartedGame> gameList){
        super.type = Utils.POLL_TYPE;
        this.gameList = gameList;
    }

}
