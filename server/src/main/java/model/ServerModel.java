package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    Map<String, UnstartedGame> allUnstartedGames = new HashMap<>();
    Map<String, StartedGame> allStartedGames = new HashMap<>();
    Set<User> allUsers = new HashSet<>();

   /***********************************BEFORE GAME*******************************************/
    public boolean addUnstartedGame(UnstartedGame newGame){
        if (allUnstartedGames.containsKey(newGame.getGameName())){
            return false;
        }

        allUnstartedGames.put(newGame.getGameName(), newGame);
        return true;
    }

    public void setAllUnstartedGames( Map<String, UnstartedGame> allUnstartedGame) {
        this.allUnstartedGames = allUnstartedGames;
    }

    public void setAllStartedGames(Map<String, StartedGame> startedGames) {
        allStartedGames = startedGames;
    }

    public boolean addUser(User user) {
        if(allUsers.add(user)) {
            return true;
        }
        return false;
    }

    public boolean userExists(User user) {
        if (allUsers.contains(user)){
            return true;
        }
        return false;
    }

    public boolean passwordMatches(User user) {
        for (User currentUser : allUsers){
            if (currentUser.getUsername().equals(user.getUsername())){
                if (currentUser.getPassword().equals(user.getPassword())){
                    return true;
                }
            }
        }
        return false;
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

    public void clameRoute(String gameName, String playerName, int routeId) {
        try {
            this.getGame(gameName).claimRoute(playerName, routeId);
        }
        catch (GamePlayException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
