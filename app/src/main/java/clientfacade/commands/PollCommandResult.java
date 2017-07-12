package clientfacade.commands;

import java.util.List;
import model.ClientModel;
import model.UnstartedGame;


public class PollCommandResult {
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
