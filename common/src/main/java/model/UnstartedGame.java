package model;

import java.util.List;

public class UnstartedGame {

    private String name;
    private List<String> usernames;
    private int playersIn;
    private int playersNeeded;

    //TODO I need a constructor!

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    private boolean started=false;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
