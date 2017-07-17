package commandresults;

import java.util.List;
import model.UnstartedGame;

public class PollGamesResult extends CommandResult {

    protected List<UnstartedGame> gameList;

    protected PollGamesResult(){};

    public PollGamesResult(List<UnstartedGame> gameList){
        super.setType("pollgames");
        this.gameList = gameList;
    }


    public List<UnstartedGame> getGameList() {
        return gameList;
    }

    public void setGameList(List<UnstartedGame> gameList) {
        this.gameList = gameList;
    }
}
