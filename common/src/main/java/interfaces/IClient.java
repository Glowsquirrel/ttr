package interfaces;

import java.util.List;

import commandresults.CommandResult;
import model.UnstartedGame;

public interface IClient {

    void loginUser(String username, String password, String message);
    void registerUser(String username, String password, String message);

    void startGame(String username, String gameName, String message);
    void updateSingleUserGameList(String username, List<UnstartedGame> gameList, String message);
    void joinGame(String username, String gameName, String message);
    void leaveGame(String username, String gameName, String message);
    void createGame(String username, String gameName, String message);
}
