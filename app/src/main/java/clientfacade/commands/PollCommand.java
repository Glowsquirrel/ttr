package clientfacade.commands;

import java.util.List;

import model.ClientModel;
import model.UnstartedGame;

/**
 * Created by Rachael on 7/11/2017.
 */

public class PollCommand {
    ClientModel clientModel=ClientModel.getMyClientModel();
    private List<UnstartedGame> gameList;

    public List<UnstartedGame> getGameList() {
        return gameList;
    }

    public void setGameList(List<UnstartedGame> gameList) {
        this.gameList = gameList;
    }
    public void execute()
    {
        clientModel.setGamesToStart(gameList);
        clientModel.notifyObserver();
    }
}
