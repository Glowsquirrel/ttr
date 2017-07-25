package clientfacade;

import android.graphics.Color;

import java.util.List;

import interfaces.IClient;
import model.ChatHistoryModel;
import model.ClientModel;
import model.Deck;
import model.Game;
import model.MapModel;
import model.Player;
import model.RunningGame;
import model.UnstartedGame;

/**
 * This is the only class who may change information in the ClientModel. Other classes may retrieve
 * data, but not modify data in the ClientModel.
 */
public class ClientFacade implements IClient{
    private ClientModel clientModel = ClientModel.getMyClientModel();
    private Game game = Game.getGameInstance();
    private ChatHistoryModel chatModel = ChatHistoryModel.myChat;
    private MapModel map=MapModel.getMapInstance();

    public void postMessage(String message){
        clientModel.setMessageToToast(message);
    }

    @Override
    public void loginUser(String username, String password, String sessionID){
        clientModel.setMyUser(username, true);
    }

    @Override
    public void registerUser (String username, String password, String message, String sessionID){
        this.postMessage(message); //registering should only ever do a toast
    }

    @Override
    public void startGame(String username, String gameName, List<String> playerNames, List<Integer> destCards,
                          List<Integer> trainCards, List<Integer> faceUpCards) {
        clientModel.startGame();
        Player myself = new Player(username, trainCards, destCards);
        game.initializeMyGame(myself, gameName, playerNames, faceUpCards);
        Deck.getInstance().setAvailableDestCards(destCards);
        Deck.getInstance().setAvailableFaceUpCards(faceUpCards);
    }

    @Override
    public void updateSingleUserGameList(String username, List<UnstartedGame> unstartedGameList, List<RunningGame> runningGameList){
        clientModel.setGameLists(unstartedGameList, runningGameList);
    }

    @Override
    public void createGame(String username, String gameName){ //only called when server sends a create game result
        clientModel.setCreatedGame(gameName);
    }

    @Override
    public void joinGame(String username, String gameName){
        clientModel.setHasGame(gameName);
    }

    @Override
    public void leaveGame(String username, String gameName){
        //clientModel.setInGameLobby(false);
    }

    @Override
    public void addChat(String username, String message){
        chatModel.addChat(username,message);
    }

    @Override
    public void claimRoute(String username, int routeID){
        map.claimRoute(game.getPlayerByName(username).getColor(),routeID);
    }

    @Override
    public void drawDestCards(String username, List<Integer> destCards){
        if(username.equals(game.getMyself().getMyUsername())) { //calling this method should always mean it is meant for this client in the first place
            Deck.getInstance().setAvailableDestCards(destCards);
        }

    }

    @Override
    public void drawTrainCardDeck(String username, int trainCard){
        Player myself = game.getMyself();
        myself.addTrainCardByInt(trainCard);
        game.aPlayerHasChanged(true);
        game.iHaveDifferentTrainCards(true);
        game.notifyObserver();
    }

    @Override
    public void drawTrainCardFaceUp(String username, int trainCard){

    }

    @Override
    public void returnDestCards(String username, int destCard){

    }

    @Override
    public void returnFirstDestCards(String username, int cardReturned){

    }

    public void addHistory(String username, String message, int numTrainCards, int numTrainCardsHeld,
                           int numDestCardsHeld, int numRoutesOwned, int score, int claimedRouteNumber){
        chatModel.addHistory(username,message);
        //TODO:
    }

    public void replaceFaceUpCards(List<Integer> trainCards) {
        Deck.getInstance().setAvailableFaceUpCards(trainCards);
    }


    public void updateAllPlayerInformation(List<String> usernames, List<Integer> numRoutesOwned, List<Integer> numTrainCardsHeld,
                                           List<Integer> numDestCardsHeld, List<Integer> numTrainCars, List<Integer> score){

    }
    //use a username to get the players color. might go into game?
    private Color getPlayerColor(String username){

        return null;
    }
}
