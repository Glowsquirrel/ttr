package model;

/**
 * Created by sjrme on 7/21/17.
 */

public enum PlayerColor {
    RED,
    BLUE,
    GREEN,
    BLACK,
    YELLOW;

    private static PlayerColor[] values = PlayerColor.values();

    public static PlayerColor getPlayerColor(int index) {
        return values[index];
    }
}