package commandresults;

import java.util.List;

/**
 * Created by shunsambongi on 7/11/17.
 */

public class PollResultData extends CommandResult {

    private List<String> gameList;

    public List<String> getGameList() {
        return gameList;
    }

    public void setGameList(List<String> gameList) {
        this.gameList = gameList;
    }
}
