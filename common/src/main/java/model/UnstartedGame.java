package model;

import java.util.List;

public class UnstartedGame {

    private String gameName;
    private List<String> usernames;
    private int playersIn;
    private int playersNeeded;

    public UnstartedGame(){}
    public UnstartedGame(String gameName, int playersIn, int playersNeeded, List<String> usernames){
        this.gameName = gameName;
        this.playersIn = playersIn;
        this.playersNeeded = playersNeeded;
        this.usernames = usernames;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    private boolean started=false;


    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public int getPlayersIn() {
        return playersIn;
    }

    public void setPlayersIn(int playersIn) {
        this.playersIn = playersIn;
    }

    public int getPlayersNeeded() {
        return playersNeeded;
    }

    public void setPlayersNeeded(int playersNeeded) {
        this.playersNeeded = playersNeeded;
    }


}
