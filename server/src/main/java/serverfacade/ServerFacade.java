package serverfacade;

import java.util.List;

import interfaces.IServer;
import model.ServerModel;
import model.UnstartedGame;
import model.User;

public class ServerFacade implements IServer {

    private static ServerModel mSingletonModel;

    public ServerFacade() {
        mSingletonModel = ServerModel.getInstance();
    }

    //Will be needed later; does nothing now
    public void clearDatabase(String username) {}

    /**
     * The register method attempts to register a new user with the ServerModel with the given
     * parameters. The result will be sent to the single client via the ClientProxy. If unsuccessful,
     * the client which sent the command will be sent an error message via the ClientProxy.
     * @param username Unique identifier to register by.
     * @param password Password to verify identity.
     */
    @Override
    public void register(String username, String password, String sessionID) {
        mSingletonModel.addUser(new User(username, password), sessionID);
    }

    @Override
    public void login(String username, String password, String sessionID) {
        mSingletonModel.validateUser(new User(username, password), sessionID);
    }

    /**
     * The pollGameList method queries the SeverModel for all unstarted games and then sends them to
     * the single client that requested it via the ClientProxy.
     * @param username The identifier of the single client who should receive the data.
     */
    @Override
    public void pollGameList(String username) {
        mSingletonModel.pollGameList(username);
    }

    /**
     * The createGame method attempts to create a new game by giving the ServerModel parameters
     * by which to create a new unstarted game. If successful, every logged in user will have their
     * game list updated via the ClientProxy. If unsuccessful, the client which sent the command will
     * be sent an error message via the ClientProxy.
     * @param username Unique identifier of which client wants to create a game.
     * @param gameName Unique identifier of the game to be created.
     * @param playerNum Number of players required for the game to start.
     */
    @Override
    public void createGame(String username, String gameName, int playerNum) {
        UnstartedGame newGame = new UnstartedGame(gameName, playerNum);
        //newGame.addPlayer(username);
        mSingletonModel.addUnstartedGame(username, newGame);
    }

    /**
     * The joinGame method is an attempt by a client to join an existing game. If successful, every
     * logged in user will have their game list updated via the ClientProxy. If unsuccessful, the client
     * which sent the command will be sent an error message via the ClientProxy.
     * @param username Unique identifier of which client wants to join a game.
     * @param gameName The name of the game to be joined.
     */
    @Override
    public void joinGame(String username, String gameName) {
        mSingletonModel.addPlayerToGame(username, gameName);
    }

    /**
     * The leaveGame method is an attempt by a client to leave a game. If successful, every logged in
     * user will have their game list updated via the ClientProxy. If unsuccessful, the client which
     * sent the command will be sent an error message via the ClientProxy.
     * @param username Unique identifier of which client wants to leave a game.
     * @param gameName Unique identifier of which game the client wants to leave.
     */
    @Override
    public void leaveGame(String username, String gameName) {
        mSingletonModel.removePlayerFromGame(username, gameName);
    }

    /**
     * The startGame method is an attempt by a client to start the game identified by the given string.
     * If successful, every logged in user will have their game list updated via the ClientProxy. Each
     * client that is in the current game will be started along with the client who sent the request. If
     * unsuccessful, the client which sent the command will be sent an error message via the ClientProxy.
     * @param gameName Unique identifier of the game to be started.
     * @param username Unique identifier of the client which wants to start the game.
     */
    @Override
    public void startGame(String gameName, String username) {
        //username might be useless?
        //Answering question above--no, its useful to send fail messages.
        mSingletonModel.startGame(gameName, username);
    }

    @Override
    public void rejoinGame(String username, String gameName) {
        mSingletonModel.reJoinGame(username, gameName);
    }

    @Override
    public void drawThreeDestCards(String username, String gameName) {
        mSingletonModel.drawThreeDestCards(gameName, username);
    }

    @Override
    public void returnDestCards(String username, String gameName, int destCard) {
        mSingletonModel.returnDestCard(gameName, username, destCard); //TODO:  Need a way to account for only one card
    }

    @Override
    public void drawTrainCardFromDeck(String username, String gameName) {
        mSingletonModel.drawTrainCardFromDeck(gameName, username);
    }

    @Override
    public void drawTrainCardFromFaceUp(String username, String gameName, int index) {
        mSingletonModel.drawTrainCardFromFaceUp(gameName, username, index);
    }

    @Override
    public void claimRoute(String username, String gameName, int routeID, List<Integer> trainCards) {
        mSingletonModel.claimRoute(gameName, username, routeID, trainCards);
    }

    @Override
    public void sendChatMessage(String username, String gameName, String message) {
        mSingletonModel.chat(gameName, username, message);
    }
}
