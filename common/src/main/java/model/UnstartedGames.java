package model;

import java.util.List;

/**
 * Created by Rachael on 7/10/2017.
 */

public class UnstartedGames {

    private String name;
    private List<String> usernames;
    private int playersIn;
    private int playersNeeded;

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


}
