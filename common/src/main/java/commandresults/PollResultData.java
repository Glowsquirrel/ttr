package commandresults;

import java.util.List;

import model.UnstartedGames;

/**
 * Created by shunsambongi on 7/11/17.
 */

public class PollResultData extends CommandResult {

    private List<UnstartedGames> gameList;

    public List<UnstartedGames> getGameList() {
        return gameList;
    }

    public void setGameList(List<UnstartedGames> gameList) {
        this.gameList = gameList;
    }
}
