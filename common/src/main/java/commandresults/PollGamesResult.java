package commandresults;

import java.util.List;

import model.UnstartedGame;

/**
 * Created by shunsambongi on 7/11/17.
 */

public class PollGamesResult extends CommandResult {


    public PollGamesResult(boolean success, String message, List<UnstartedGame> gameList){
        super.setType("pollgames");
        super.setSuccess(success);
        super.setErrorMessage(message);
        this.gameList = gameList;
    }

    private List<UnstartedGame> gameList;

    public List<UnstartedGame> getGameList() {
        return gameList;
    }

    public void setGameList(List<UnstartedGame> gameList) {
        this.gameList = gameList;
    }
}
