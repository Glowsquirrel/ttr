package model;


import java.util.ArrayList;
import java.util.Map;

public class Game {

    private Game(){};
    public static final Game myGame=new Game();
    private int numOfPlayers = 0;
    private String gameName;
    private ArrayList<String> playerUserNames = new ArrayList<>();
    private Map<String, Player> playerMap;

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

}
