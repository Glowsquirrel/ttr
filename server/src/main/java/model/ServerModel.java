package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import clientproxy.ClientProxy;
import results.Result;
import results.game.GameHistoryResult;

/**
 * ServerModel is the server model root class.
 * When a ServerModel method is called --
 *  Step one: User the parameter "gameName" to access the corresponding StartedGame in the Map.
 *  Step two: Call this StartedGame's version of the same method. Will return a Result.
 *  Step three: Pass the StartedGame's Result to the ClientProxy
 *
 * @author Stephen Richins
 * @version 1.0
 */

//TODO: Call ClientProxy
public class ServerModel {

    //NOTE: Both really should be maps. I'm not sure if changing the UnstartedGame list to a map rocks the boat
    //too much on other people's parts. Thoughts..?
    private Map<String, UnstartedGame> allUnstartedGames;
    private Map<String, StartedGame> allStartedGames;
    private Set<User> allUsers;
    private ClientProxy toClient;
    private Map<String, List<Result>> allCommandLists = new HashMap<>();

    private static class LazyModelHelper{
        private static final ServerModel MODEL_INSTANCE = new ServerModel();
    }

    public static ServerModel getInstance(){

        return LazyModelHelper.MODEL_INSTANCE;
    }

    private ServerModel(){
        allUnstartedGames = new HashMap<>();
        allStartedGames = new HashMap<>();
        allUsers = new HashSet<>();
        toClient = new ClientProxy();
    }

   /***********************************BEFORE GAME*******************************************/

    /**
     *  <h1>Add Unstarted Game</h1>
     *  Checks to see if a game exists, rejecting the creation command if so, and adds the game
     *  to the list of unstarted games if not; reports success to the proxy.
     *
     *  @param          newGame         The new game being proposed by the client
     */
   public void addUnstartedGame(String username, UnstartedGame newGame){

        if (allUnstartedGames.containsKey(newGame.getGameName()) || allStartedGames.containsKey(newGame.getGameName())){
            String message = "Game already exists.";
            toClient.rejectCommand(username, message);
        } else {
            allUnstartedGames.put(newGame.getGameName(), newGame);
            toClient.createGame(username, newGame.getGameName());
            toClient.updateAllUsersInMenus(getUnstartedGamesList(), getStartedGamesList());
        }
    }

    public void setAllUnstartedGames( Map<String, UnstartedGame> allUnstartedGame) {
        this.allUnstartedGames = allUnstartedGames;
    }

    public void setAllStartedGames(Map<String, StartedGame> startedGames) {
        allStartedGames = startedGames;
    }

    public void addUser(User user, String sessionID) {
        toClient = new ClientProxy();
        String message = "Registered as " + user.getUsername() + ".";
        if(allUsers.add(user)) {
            toClient.registerUser(user.getUsername(), user.getPassword(), message, sessionID);
        } else {
            message = user.getUsername() + "is already registered.";
            toClient.rejectCommand(sessionID, message);
        }
    }

    public void validateUser(User user, String sessionID) {
        toClient = new ClientProxy();

        if (allUsers.contains(user)){
            for (User currentUser : allUsers){
                if (currentUser.getUsername().equals(user.getUsername())){
                    if (currentUser.getPassword().equals(user.getPassword())){
                        toClient.loginUser(user.getUsername(), user.getPassword(), sessionID);
                        return;
                    }
                }
            }
        } else{
            String message = "Login failed.";
            toClient.rejectCommand(sessionID, message);
        }

    }

    private List<UnstartedGame> getUnstartedGamesList(){
        List<UnstartedGame> joinableGames = new ArrayList<>();

        for(String nextGameName : allUnstartedGames.keySet()){
            joinableGames.add(allUnstartedGames.get(nextGameName));
        }
        return joinableGames;
    }

    private List<RunningGame> getStartedGamesList(){
        List<RunningGame> startedGames = new ArrayList<>();

        for(String nextGameName : allStartedGames.keySet()){
            RunningGame nextStartedGame = new RunningGame(allStartedGames.get(nextGameName)
                    .getGameName(),
                    allStartedGames.get(nextGameName)
                            .getAllPlayers().size());
            startedGames.add(nextStartedGame);
        }
        return startedGames;
    }

    /**
     *  <h1>Poll Game List</h1>
     *  Sends the current list of games that haven't started to the proxy.
     *
     *  @param          username            The user that's requesting the game list
     */
    public void pollGameList(String username){
        toClient.updateSingleUserGameList(username, getUnstartedGamesList(), getStartedGamesList());
    }

    public void addPlayerToGame(String username, String gameName) {
        boolean success = false;
        if (allUnstartedGames.containsKey(gameName)){
            UnstartedGame myUnstartedGame = allUnstartedGames.get(gameName);
            if (!myUnstartedGame.hasEnoughPlayersToStart()){
                myUnstartedGame.addPlayer(username);
                success = true;
            }
        }

        if (success) {
            toClient.joinGame(username, gameName);
            toClient.updateAllUsersInMenus(getUnstartedGamesList(), getStartedGamesList());
        } else {
            String message = "Could not join game.";
            toClient.rejectCommand(username, message);
        }
    }

    public void removePlayerFromGame(String username, String gameName) {
        boolean success = false;
        if (allUnstartedGames.containsKey(gameName)){
            UnstartedGame myUnstartedGame = allUnstartedGames.get(gameName);
            if (myUnstartedGame.hasPlayer(username)){ //if the player is in the game, remove them from the game
                myUnstartedGame.removePlayer(username);
                success = true;
                if (myUnstartedGame.hasNoPlayers()){ //if number of players == 0, remove the game
                    allUnstartedGames.remove(gameName);
                }
            }
        }

        if (success) {
            toClient.updateAllUsersInMenus(getUnstartedGamesList(), getStartedGamesList());
            toClient.leaveGame(username, gameName);
        } else {
            String message = "Player not in game.";
            toClient.rejectCommand(username, message);
        }
    }

   /****************************************STARTING GAME****************************************/
    public void startGame(String gameName) {
        try {
            activateGame(gameName);
        }
        catch (GamePlayException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void activateGame(String gameName) throws GamePlayException {

        if (allUnstartedGames.containsKey(gameName)){

            UnstartedGame myUnstartedGame = allUnstartedGames.get(gameName);
            StartedGame newlyStartedGame = new StartedGame(myUnstartedGame);

            allUnstartedGames.remove(gameName);
            List<Result> startResults =
                newlyStartedGame.preGameSetup(myUnstartedGame.getUsernamesInGame());

            allStartedGames.put(gameName, newlyStartedGame);
            allCommandLists.put(gameName, new ArrayList<Result>());

            toClient.updateAllUsersInMenus(getUnstartedGamesList(), getStartedGamesList());
            for (Result result : startResults) {
                //toClient.startGame(result.getUsername(), gameName, result);
                allCommandLists.get(gameName).add(result);
            }
            while (newlyStartedGame.getReplaceFaceUpFlag()) {
                Result nextResult =
                        newlyStartedGame.replaceFaceUpCards();
                allCommandLists.get(gameName).add(nextResult);
            }
        }

        throw new GamePlayException("Game " + gameName +  " does not exist.");
    }

   /****************************************ROUND ONE*********************************************/
    public void returnDestCard(String gameName, String playerName, int destCard) {
        try {
            Result result =
                this.getGame(gameName).returnDestCard(playerName, destCard);
            sendToClients(playerName, gameName, result, "returnDestCard");
        }
        catch (GamePlayException ex){
            System.out.println(ex.getMessage());
        }
    }

    private StartedGame getGame(String gameName) throws GamePlayException {
        StartedGame matchingStartedGame = allStartedGames.get(gameName);

        if (matchingStartedGame != null) {
          return matchingStartedGame;
        }
        else {
            throw new GamePlayException("Game " + gameName +  " does not exist.");
        }
    }

    /*************************************DrawDestCard*******************************************/

    public void drawThreeDestCards(String gameName, String playerName) {
        try {
            Result result =
                    this.getGame(gameName).drawThreeDestCards(playerName);
            sendToClients(playerName, gameName, result, "drawThreeDestCards");
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /************************************DrawTrainCard****************************************/

    public void drawTrainCardFromDeck(String gameName, String playerName) {
        try {
            Result result =
                    this.getGame(gameName).drawTrainCardFromDeck(playerName);
            sendToClients(playerName, gameName, result, "drawTrainCardFromDeck");
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void drawTrainCardFromFaceUp(String gameName, String playerName, int index) {
        try {
            StartedGame game = this.getGame(gameName);
            Result result =
                game.drawTrainCardFromFaceUp(playerName, index);
            toClient.sendToGame(gameName, result);
            allCommandLists.get(gameName).add(result);

            while (game.getReplaceFaceUpFlag()) {
                Result nextResult =
                        game.replaceFaceUpCards();
                allCommandLists.get(gameName).add(result);
            }
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*************************************Claim Route*************************************/

    public void claimRoute(String gameName, String playerName, int routeId) {
        try {
            Result result =
                    this.getGame(gameName).claimRoute(playerName, routeId);
            sendToClients(playerName, gameName, result, "claimRoute");
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void chat(String gameName, String playerName, String message) {
        try {
            Result result =
                    this.getGame(gameName).addChat(playerName, message);
            toClient.sendToGame(gameName, result);
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void sendToClients(String playerName, String gameName, Result result, String resultType) {
        toClient.sendToUser(playerName, result);
        allCommandLists.get(gameName).add(result);
        Result gameHistory = new GameHistoryResult(playerName, resultType);
        toClient.sendToOthersInGame(playerName, gameName, gameHistory);
        allCommandLists.get(gameName).add(gameHistory);
    }
}
