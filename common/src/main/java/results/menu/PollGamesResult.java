package results.menu;

import java.util.List;

import model.RunningGame;
import results.Result;
import model.UnstartedGame;
import utils.Utils;

public class PollGamesResult extends Result {

    protected List<UnstartedGame> unstartedGameList;
    protected List<RunningGame> runningGameList;

    protected PollGamesResult(){}
    public PollGamesResult(List<UnstartedGame> unstartedGameList, List<RunningGame> runningGameList){
        super.type = Utils.POLL_TYPE;
        this.unstartedGameList = unstartedGameList;
        this.runningGameList = runningGameList;
    }

}
