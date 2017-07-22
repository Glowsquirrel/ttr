package model;

/**
 * Created by sjrme on 7/21/17.
 */

public class Chat {
    private String playerName;
    private String message;

    public Chat (String playerName, String message) {
        this.playerName = playerName;
        this.message = message;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getMessage() {
        return message;
    }
}
