package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import clientproxy.ClientProxy;
import results.Result;

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

public class ServerModel {

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

    public List<Result> getGameResultList(String gameName){
        return allCommandLists.get(gameName);
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
            toClient.rejectCommand(username, newGame.getGameName(), message);
        } else {
            //newGame.addPlayer(username);
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
            message = user.getUsername() + " is already registered.";
            toClient.rejectCommand(sessionID, null, message);
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
            String message = "Invalid login information.";
            toClient.rejectCommand(sessionID, null, message);
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
            toClient.rejectCommand(username, gameName, message);
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
            toClient.rejectCommand(username, gameName, message);
        }
    }
    
    /**
     *  <h1>reJoinGame</h1>
     *  Checks to see if a user is a player in a started game, confirming that player can re-join
     *  the game if so, or a rejection is sent to the client if not.
     *
     *  @param          username            The player trying to re-join.
     *  @param          gameName            The game the player is trying to re-join
     */
    public void reJoinGame(String username, String gameName) {
        String message = "You were not in that game.";
        if(allStartedGames.containsKey(gameName)) {
            StartedGame gameToReJoin = allStartedGames.get(gameName);
            if(gameToReJoin.getAllPlayers().containsKey(username)) {
                toClient.reJoinGame(username, gameName);
            } else {
                toClient.rejectCommand(username, gameName, message);
            }
        } else {
            toClient.rejectCommand(username, gameName,message);
        }
    }
    
   /*************************************STARTING GAME*********************************************/
    public void startGame(String gameName, String username) {
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
                toClient.startGame(result, result.getUsername());
                allCommandLists.get(gameName).add(result);
            }
            while (newlyStartedGame.getReplaceFaceUpFlag()) { //If three face-up locomotives
                Result nextResult = newlyStartedGame.replaceFaceUpCards(username);
                toClient.sendToGame(gameName, nextResult);
                allCommandLists.get(gameName).add(nextResult);
            }

            Result turnResult = newlyStartedGame.getThenNullifyTurnResult();
            if (turnResult != null) {
                toClient.sendToGame(gameName, turnResult);
                allCommandLists.get(gameName).add(turnResult);
            }
        } else {
            String message = "Game " + gameName +  " does not exist, or has started.";
            toClient.rejectCommand(username, gameName, message);
        }
    }

   /****************************************ROUND ONE*********************************************/
    public void returnDestCard(String gameName, String playerName, int destCard) {
        try {
            StartedGame game = this.getGame(gameName);
            Result result = game.returnDestCard(playerName, destCard);
            sendToClients(playerName, game, result);
        }
        catch (GamePlayException ex){
            toClient.rejectCommand(playerName, gameName, ex.getMessage());
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

    /**************************************DrawDestCard*******************************************/

    public void drawThreeDestCards(String gameName, String playerName) {
        try {
            StartedGame game = this.getGame(gameName);
            Result result = game.drawThreeDestCards(playerName);
            sendToClients(playerName, game, result);
        }
        catch (GamePlayException ex) {
            toClient.rejectCommand(playerName, gameName, ex.getMessage());
        }
    }

    /*************************************DrawTrainCard********************************************/

    public void drawTrainCardFromDeck(String gameName, String playerName) {

        try {
            StartedGame game = this.getGame(gameName);
            Result result = game.drawTrainCardFromDeck(playerName);
            sendToClients(playerName, game, result);
        } catch (GamePlayException ex) {
            toClient.rejectCommand(playerName, gameName, ex.getMessage());
        }
    }

    public void drawTrainCardFromFaceUp(String gameName, String playerName, int index) {
        try {
            StartedGame game = this.getGame(gameName);
            List<Result> results = game.drawTrainCardFromFaceUp(playerName, index);

            while (game.getReplaceFaceUpFlag()) {
                Result nextResult = game.replaceFaceUpCards(playerName);
                //replace the original ReplaceFaceUpResult with the new one
                //previously was sending the new one first, then the old one.
                results.set(1, nextResult);
                //toClient.sendToGame(gameName, nextResult);
                allCommandLists.get(gameName).add(nextResult);
            }

            toClient.sendToUser(playerName, gameName, results.get(0));
            toClient.sendToGame(gameName, game.getGameHistory());
            toClient.sendToGame(gameName, results.get(1));

            Result turnResult = game.getThenNullifyTurnResult();
            if (turnResult != null) {
                toClient.sendToGame(game.getGameName(), turnResult);
                allCommandLists.get(game.getGameName()).add(turnResult);
            }
            Result endGameResult  = game.getEndGameResult();
            if (endGameResult !=  null) {
                toClient.sendToGame(game.getGameName(), endGameResult);
                allCommandLists.get(game.getGameName()).add(endGameResult);
                //remove the game from the list
                allStartedGames.remove(game.getGameName());
            }
            //game.printBoardState();

            //remove the game from the list


        } catch (GamePlayException ex) {
            toClient.rejectCommand(playerName, gameName, ex.getMessage());
        }
    }

    /************************************Claim Route***********************************************/
    public void claimRoute(String gameName, String playerName, int routeId, List<Integer> trainCards) {
        try {
            StartedGame game = this.getGame(gameName);
            Result result = game.claimRoute(playerName, routeId, trainCards);
            sendToClients(playerName, game, result);
            String finalTurnFlag = game.getAfterFinalTurnPlayer();

            if (finalTurnFlag != null) {
                Result finalRoundResult = game.getFinalTurnResult();
                toClient.sendToGame(gameName, finalRoundResult);
                allCommandLists.get(game.getGameName()).add(finalRoundResult);
            }
        } catch (GamePlayException ex) {
            toClient.rejectCommand(playerName, gameName, ex.getMessage());
        }
    }

    public void chat(String gameName, String playerName, String message) {
        try {
            Result result =
                    this.getGame(gameName).addChat(playerName, message);
            toClient.sendToGame(gameName, result);
        }
        catch (GamePlayException ex) {
            toClient.rejectCommand(playerName, gameName, ex.getMessage());
        }
    }

    private void sendToClients(String playerName, StartedGame game , Result result) {
        toClient.sendToUser(playerName, game.getGameName(), result);
        allCommandLists.get(game.getGameName()).add(result);
        toClient.sendToGame(game.getGameName(), game.getGameHistory());
        allCommandLists.get(game.getGameName()).add(game.getGameHistory());

        Result turnResult = game.getThenNullifyTurnResult();
        if (turnResult != null) {
            toClient.sendToGame(game.getGameName(), turnResult);
            allCommandLists.get(game.getGameName()).add(turnResult);
        }
        Result endGameResult  = game.getEndGameResult();
        if (endGameResult !=  null) {
            toClient.sendToGame(game.getGameName(), endGameResult);
            allCommandLists.get(game.getGameName()).add(endGameResult);
            //remove the game from the list
            allStartedGames.remove(game.getGameName());
        }
        //game.printBoardState();
    }

}
