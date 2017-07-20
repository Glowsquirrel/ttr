package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import clientproxy.ClientProxy;

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
    Map<String, UnstartedGame> allUnstartedGames;
    Map<String, StartedGame> allStartedGames;
    Set<User> allUsers;
    ClientProxy toClient;

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
   public void addUnstartedGame(UnstartedGame newGame){
        String gameCreator = newGame.getUsernames().get(1);

        if (allUnstartedGames.containsKey(newGame.getGameName())){
            String message = "Game already exists.";
            toClient.rejectCommand(gameCreator, message);
        } else {
            allUnstartedGames.put(newGame.getGameName(), newGame);
            toClient.createGame(gameCreator, newGame.getGameName());
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
        String message = "User registered.";
        if(allUsers.add(user)) {
            toClient.registerUser(user.getUsername(), user.getPassword(), message, sessionID);
        } else {
            message = "User already exists.";
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
                    }
                }
            }
        } else{
            String message = "Log in failed.";
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
                    .gameName,
                    allStartedGames.get(nextGameName)
                            .allPlayers.size());
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
        for(String nextGameName : allUnstartedGames.keySet()) {
            if(gameName.equals(nextGameName)) {
                List<String> currentPlayers = allUnstartedGames.get(nextGameName).getUsernames();
                if(!currentPlayers.contains(username)){
                    currentPlayers.add(username);
                    allUnstartedGames.get(nextGameName).setUsernames(currentPlayers);
                    toClient.joinGame(username, gameName);
                    toClient.updateAllUsersInMenus(getUnstartedGamesList(), getStartedGamesList());
                } else {
                    //TODO: Call rejoin game in proxy
                }
            }
        }
    }

    public void removePlayerFromGame(String username, String gameName) {
        for(String nextGameName : allUnstartedGames.keySet()) {
            if(gameName.equals(nextGameName)) {
                List<String> currentPlayers = allUnstartedGames.get(nextGameName).getUsernames();
                if(currentPlayers.contains(username)){
                    currentPlayers.remove(username);
                    allUnstartedGames.get(nextGameName).setUsernames(currentPlayers);
                    toClient.leaveGame(username, gameName);
                    toClient.updateAllUsersInMenus(getUnstartedGamesList(), getStartedGamesList());
                }
            } else {
                //Shouldn't be possible to get here based on UI
                String message = "Player not in game.";
                toClient.rejectCommand(username, message);
            }
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

    private StartedGame activateGame(String gameName) throws GamePlayException {
        for (int a = 0; a < allUnstartedGames.size(); a++) {

            UnstartedGame currentGame = allUnstartedGames.get(a);

            if (currentGame.getGameName().equals(gameName)){

                allUnstartedGames.remove(a);
                StartedGame newlyStartedGame = new StartedGame(currentGame);

                allStartedGames.put(currentGame.getGameName(), newlyStartedGame);
                newlyStartedGame.preGameSetup(currentGame.getUsernames()); //TODO: Implement Layer
                return newlyStartedGame;
            }
        }
        throw new GamePlayException("Game " + gameName +  " does not exist.");
    }

   /****************************************ROUND ONE*********************************************/
    public void returnFirstDestCard(String gameName, String playerName, int destCard) {
        try {
            this.getGame(gameName).returnFirstRoundDestCard(playerName, destCard);
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
            this.getGame(gameName).drawThreeDestCards(playerName);
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void returnDestCards(String gameName, String playerName,
                                int cardOne, int cardTwo) throws GamePlayException {
        try {
            this.getGame(gameName).returnDestCards(playerName, cardOne, cardTwo);
        }
        catch (GamePlayException ex){
            System.out.println (ex.getMessage());
        }
    }

    /************************************DrawTrainCard****************************************/

    public void drawTrainCardFromDeck(String gameName, String playerName) {
        try {
            this.getGame(gameName).drawTrainCardFromDeck(playerName);
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void drawTrainCardFromFaceUp(String gameName, String playerName) {
        try {
            this.getGame(gameName).drawTrainCardFromFaceUp(playerName);
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*************************************Claim Route*************************************/

    public void claimRoute(String gameName, String playerName, int routeId) {
        try {
            this.getGame(gameName).claimRoute(playerName, routeId);
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
