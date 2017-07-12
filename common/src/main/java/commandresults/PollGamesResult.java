package commandresults;

import java.util.List;

import model.UnstartedGames;

/**
 * Created by shunsambongi on 7/11/17.
 */

public class PollGamesResult extends CommandResult {


    public PollGamesResult(boolean success, String message, List<UnstartedGames> gameList){
        super.setType("pollgames");
        super.setSuccess(success);
        super.setErrorMessage(message);
        this.gameList = gameList;
    }

    private List<UnstartedGames> gameList;

    public List<UnstartedGames> getGameList() {
        return gameList;
    }

    public void setGameList(List<UnstartedGames> gameList) {
        this.gameList = gameList;
    }
}
