package model;

/**
 * Created by Rachael on 7/10/2017.
 */

public class GameToStart {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    private int playersIn;
    private int playersNeeded;

}
