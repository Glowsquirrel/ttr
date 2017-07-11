package model;


import java.util.ArrayList;

public class Game {

    private int numOfPlayers = 0;
    private String gameName;
    private ArrayList<String> playerUserNames = new ArrayList<>();


    Game(String gameName, String userName){
        this.gameName = gameName;
        addPlayer(userName);
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
