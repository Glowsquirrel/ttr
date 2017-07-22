package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private Game(){};
    public static final Game myGame=new Game();
    private int numOfPlayers = 0;
    private String gameName;
    private ArrayList<String> playerUserNames = new ArrayList<>();
    private Map<String, Player> playerMap;
    private List<Integer> destCards;
    private List<Integer> trainCards;
    private List<Integer> faceUpCards;
    private List<Player> playerListToDisplay;

    public List<Player> getPlayerListToDisplay() {
        playerListToDisplay=new ArrayList();
        for(int i=0;i<numOfPlayers;i++)
        {
            for(String player: playerMap.keySet())
            {
                Player p=playerMap.get(player);
                if(p.getTurn()==i)
                {
                    playerListToDisplay.add(p);
                }
            }
        }
        return playerListToDisplay;

    }


    public List<Integer> getDestCards() {
        return destCards;
    }

    public void setDestCards(List<Integer> destCards) {
        this.destCards = destCards;
    }

    public List<Integer> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(List<Integer> trainCards) {
        this.trainCards = trainCards;
    }

    public List<Integer> getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(List<Integer> faceUpCards) {
        this.faceUpCards = faceUpCards;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean addPlayer(String userName){

        if (numOfPlayers < 5) {
            playerUserNames.add(userName);
            numOfPlayers++;
            return true;
        }
        return false;
    }
    public void setPlayerMap(List<String> playerNames)
    {
        playerMap=new HashMap<>();
        int i=0;
        for(String name:playerNames)
        {
            Player p=new Player();
            p.setTurn(i);
            p.setUserName(name);
            playerMap.put(name,p);

        }
    }

}
