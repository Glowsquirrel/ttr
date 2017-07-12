package clientfacade.commands;

import java.util.List;

import model.ClientModel;
import model.UnstartedGames;

/**
 * Created by Rachael on 7/11/2017.
 */

public class PollCommand {
    ClientModel clientModel=ClientModel.getMyClientModel();
    private List<UnstartedGames> gameList;

    public List<UnstartedGames> getGameList() {
        return gameList;
    }

    public void setGameList(List<UnstartedGames> gameList) {
        this.gameList = gameList;
    }
    public void execute()
    {
        clientModel.setGamesToStart(gameList);
        clientModel.notifyObserver();
    }
}
