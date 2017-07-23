package clientfacade;

import android.graphics.Color;

import java.util.List;

import interfaces.IClient;
import model.ChatHistoryModel;
import model.ClientModel;
import model.Deck;
import model.Game;
import model.RunningGame;
import model.UnstartedGame;

/**
 * This is the only class who may change information in the ClientModel. Other classes may retrieve
 * data, but not modify data in the ClientModel.
 */
public class ClientFacade implements IClient{
    private ClientModel clientModel = ClientModel.getMyClientModel();
    private Game game=Game.myGame;
    private ChatHistoryModel chatModel=ChatHistoryModel.myChat;

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
        game.setGameName(gameName);
        game.setDestCards(destCards); //May want to remove this here and from Game model
        Deck.getInstance().setAvailableDestCards(destCards);
        game.setTrainCards(trainCards);
        game.setFaceUpCards(faceUpCards); //May want to remove this here and from Game model
        Deck.getInstance().setAvailableFaceUpCards(faceUpCards);
        game.setPlayerMap(playerNames);
        //TODO: put all the start game info into clientModel.
    }

    @Override
    public void updateSingleUserGameList(String username, List<UnstartedGame> unstartedGameList, List<RunningGame> runningGameList){
        clientModel.setGameLists(unstartedGameList, runningGameList);
    }

    @Override
    public void createGame(String username, String gameName){ //only called if server already accepted a true createGame
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

    }

    @Override
    public void drawDestCards(String username, List<Integer> destCards){
        if(username.equals(clientModel.getMyUsername())) {
            Deck.getInstance().setAvailableDestCards(destCards);
        }
    }

    @Override
    public void drawTrainCardDeck(String username, int trainCard){

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
    //use a username to get the players color. might go into clientmodel?
    private Color getPlayerColor(String username){

        return null;
    }
}
