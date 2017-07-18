package interfaces;

import java.util.List;

import model.UnstartedGame;

public interface IClient {

    boolean loginUser(String username, String password, String message, String sessionID);
    boolean registerUser(String username, String password, String message, String sessionID);

    List<String> startGame(String username, String gameName, String message);
    boolean updateSingleUserGameList(String username, List<UnstartedGame> gameList, String message);
    boolean joinGame(String username, String gameName, String message);
    boolean leaveGame(String username, String gameName, String message);
    boolean createGame(String username, String gameName, String message);
}
