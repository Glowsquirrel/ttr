package clientfacade;

import android.graphics.Color;

import java.util.List;

import interfaces.IClient;
import model.AbstractPlayer;
import model.ChatHistoryModel;
import model.ClientModel;
import model.Deck;
import model.Game;
import model.MapModel;
import model.Player;
import model.Route;
import model.RunningGame;
import model.TrainCard;
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
        Player myself = new Player(username, trainCards);
        game.initializeMyGame(myself, gameName, playerNames, faceUpCards);
        game.setPossibleDestCards(destCards);
        game.iHavePossibleDestCards(true);
        game.notifyObserver();

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
        Route myRoute = Route.getRouteByID(routeID);
        TrainCard myTrainCardType = myRoute.getOriginalColor();
        int routeSize = myRoute.getLength();
        if (game.getMyself().getNumOfTypeCards(myTrainCardType) >= routeSize
                && game.getMyself().getNumTrains() >= routeSize) {
            map.claimRoute(game.getPlayerByName(username).getColor(), routeID);
            game.getMyself().incrementNumRoutesClaimed();
            game.getMyself().removeMultipleCardsOfType(myTrainCardType, routeSize);
            game.getMyself().addToScore(myRoute.getPointValue());
            game.getMyself().removeTrains(routeSize);
            game.getMyself().removeMultipleTrainCards(routeSize);
            game.iHaveDifferentTrainCards(true);
            game.aPlayerHasChanged(true);
            game.notifyObserver();
            String message = "Claimed route " + routeID;
            chatModel.addHistory(username, message);
        } else {
            showRejectMessage("You don't have the cards for that");
        }
    }

    @Override
    public void drawDestCards(String username, List<Integer> destCards){
        if (destCards.size() != 0) { //if there are no destCards returned, don't cause the fragment to switch
            game.setPossibleDestCards(destCards);
            game.iHaveDifferentFaceUpCards();
            game.iHavePossibleDestCards(true);
    
            //TODO: Remove this once the model is properly updated from the server
            game.setDestinationCardDeckSize(game.getDestinationCardDeckSize() - 3);
            game.iHaveDifferentDestDeckSize(true);
            
            
            game.notifyObserver();
            String message = "Drew " + destCards.size() + " destination cards";
            chatModel.addHistory(username, message);
        }

    }

    @Override
    public void drawTrainCardDeck(String username, int trainCard){
        Player myself = game.getMyself();
        myself.addTrainCardByInt(trainCard);
        game.aPlayerHasChanged(true);
        game.iHaveDifferentTrainCards(true);
    
        //TODO: Remove this once the model is properly updated from the server
        game.setTrainCardDeckSize(game.getTrainCardDeckSize() - 1);
        game.iHaveDifferentTrainDeckSize(true);
        
        game.notifyObserver();
        String message = "Drew train card";
        chatModel.addHistory(username, message);
    }

    @Override
    public void drawTrainCardFaceUp(String username, int trainCard){
        Player myself = game.getMyself();
        myself.addTrainCardByInt(trainCard);
        game.aPlayerHasChanged(true);
        game.iHaveDifferentTrainCards(true);
        game.notifyObserver();
        String message = "Drew train card face up";
        chatModel.addHistory(username, message);
    
        //TODO: Remove this once the model is properly updated from the server
        game.setTrainCardDeckSize(game.getTrainCardDeckSize() - 1);
        game.iHaveDifferentTrainDeckSize(true);
        
    }

    @Override
    public void returnDestCards(String username, int destCard){
        String message = "Returned destination card";
        chatModel.addHistory(username, message);
    }

    @Override
    public void returnFirstDestCards(String username, int cardReturned){
        String message = "Returned destination card";
        chatModel.addHistory(username, message);
    }
//if the server sends back any non zero results that given usernames info will be updated to the results
    public void addHistory(String username, String message, int numTrainCars, int numTrainCardsHeld,
                           int numDestCardsHeld, int numRoutesOwned, int score, int claimedRouteNumber){
        chatModel.addHistory(username,message);
        AbstractPlayer player = game.getPlayerByName(username);
        //updates the players score
        if(score>0)
        {
            game.aPlayerHasChanged(true);
            player.setScore(score);
        }
        //updates the players number of train cars
        if(numTrainCars>0)
        {
            game.aPlayerHasChanged(true);
            player.setNumTrains(numTrainCars);
        }
        //updates the number of trains cards held by a player
        if(numTrainCardsHeld>0)
        {
            game.aPlayerHasChanged(true);
            game.notifyObserver();
            player.setNumCards(numTrainCardsHeld);
        }
        //updates destination cards held by a player
        if(numDestCardsHeld>0)
        {

            game.aPlayerHasChanged(true);
            game.notifyObserver();
            player.setDestCardNum(numDestCardsHeld);
        }
        //update routes owned by a player
        if(numRoutesOwned>0)
        {
            game.aPlayerHasChanged(true);
            player.setNumRoutes(numRoutesOwned);
        }
        //claims a route by a player
        if(claimedRouteNumber>0)
        {
            game.aPlayerHasChanged(true);
            map.claimRoute(game.getPlayerByName(username).getColor(),claimedRouteNumber);
        }
    }

    public void replaceFaceUpCards(List<Integer> trainCards) {
        game.setFaceUpCards(trainCards);
        game.iHaveDifferentFaceUpCards(true);
        game.notifyObserver();
    }


    public void updateAllPlayerInformation(List<String> usernames, List<Integer> numRoutesOwned, List<Integer> numTrainCardsHeld,
                                           List<Integer> numDestCardsHeld, List<Integer> numTrainCars, List<Integer> score){

    }
    //use a username to get the players color. might go into game?
    private Color getPlayerColor(String username){

        return null;
    }

    public void showRejectMessage(String message) {
        Game.getGameInstance().getServerError().setMessage(message);
    }
}
