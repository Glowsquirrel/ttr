package fysh340.ticket_to_ride.game.fragments.mapsupport;

import android.graphics.Color;

/**
 * Colors for routes on the map
 *
 * @author Shun Sambongi
 * @version 1.0
 * @since 2017-07-22
 */
public enum MapRouteColor {
    RED("#F44336"),
    ORANGE("#FF9800"),
    YELLOW("#FFEB3B"),
    GREEN("#4CAF50"),
    BLUE("#2196F3"),
    PURPLE("#9C27B0"),
    WHITE("#FFFFFF"),
    BLACK("#000000"),
    GRAY("#9E9E9E");

    private String color;

    MapRouteColor(String color) {
        this.color = color;
    }

    public int getColor() {
        return Color.parseColor(color);
    }
}
