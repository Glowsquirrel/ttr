package clientfacade;

import android.graphics.Color;

import java.util.List;

import interfaces.IClient;
import model.ClientModel;
import model.UnstartedGame;

/**
 * This is the only class who may change information in the ClientModel. Other classes may retrieve
 * data, but not modify data in the ClientModel.
 */
public class ClientFacade implements IClient{
    private ClientModel clientModel = ClientModel.getMyClientModel();

    public void postMessage(String message){
        clientModel.setErrorMessage(message);
    }

    @Override
    public void loginUser(String username, String password, String sessionID){
        clientModel.setMyUser(username);
    }

    @Override
    public void registerUser (String username, String password, String message, String sessionID){
        if (message != null)
            this.postMessage(message); //registering should only ever do a toast at most
    }

    @Override
    public void startGame(String username, String gameName, List<String> playerNames, List<Integer> destCards,
                          List<Integer> trainCards, List<Integer> faceUpCards) {
        clientModel.setStartedGame(true);
        //TODO get playerNames and put into ClientModel
    }

    @Override
    public void updateSingleUserGameList(String username, List<UnstartedGame> gameList){
        clientModel.setGamesToStart(gameList);
    }

    @Override
    public void createGame(String username, String gameName){ //only called if server already accepted a true createGame
        clientModel.setHasCreatedGame(true);
        clientModel.setHasGame(true);
    }

    @Override
    public void joinGame(String username, String gameName){
        clientModel.setHasGame(true);
    }

    @Override
    public void leaveGame(String username, String gameName){
        clientModel.setHasGame(false);
    }

    @Override
    public void addChat(String username, String message){

    }

    @Override
    public void claimRoute(String username, int routeID){

    }

    @Override
    public void drawDestCards(String username, List<Integer> destCards){

    }

    @Override
    public void drawTrainCardDeck(String username, int trainCard){

    }

    @Override
    public void drawTrainCardFaceUp(String username, int trainCard){

    }

    @Override
    public void returnDestCards(String username, List<Integer> destCards){

    }

    @Override
    public void returnFirstDestCards(String username, int cardReturned){

    }

    public void addHistory(String message){

    }

    //use a username to get the players color. might go into clientmodel?
    private Color getPlayerColor(String username){

        return null;
    }
}
