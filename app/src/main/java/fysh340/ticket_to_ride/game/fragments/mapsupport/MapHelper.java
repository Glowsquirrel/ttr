package fysh340.ticket_to_ride.game.fragments.mapsupport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fysh340.ticket_to_ride.R;

/**
 * Class that the google maps for TTR by drawing all of the routes, the cities, and the names for
 * the city. It also provides functions to change color of routes when they are claimed.
 *
 * @author Shun Sambongi
 * @version 1.0
 * @since 2017-07-22
 */
public class MapHelper {

    /**
     * The width of the routes drawn on the screen
     */
    private static final int ROUTE_WIDTH = 25;

    /**
     * The gap between the segments of a route
     */
    private static final int ROUTE_GAP = 30000;

    /**
     * The maximum zoom level for the map
     */
    private static final float MAX_ZOOM = 6.0f;

    /**
     * The minimum zoom level for the map
     */
    private static final float MIN_ZOOM = 4.0f;

    /**
     * The radius for the circles drawn at the city locations
     */
    private static final int CITY_RADIUS = 30000;

    /**
     * The color of the cities
     */
    private static final int CITY_FILL = Color.GRAY;

    /**
     * The color of the stroke for the cities
     */
    private static final int CITY_STROKE = Color.BLACK;

    /**
     * Map that keeps a reference from each route to the segments that make up that route. Since
     * each route is actually drawn using multiple (1 - 6) polylines, this makes sure that the
     * segments can be referenced together (for example, when changing the color)
     */
    private static Map<MapRoute, Set<Polyline>> routePolyLineMap = new HashMap<>();

    // initializes the the map
    static {
        for (MapRoute route : MapRoute.values()) {
            routePolyLineMap.put(route, new HashSet<Polyline>());
        }
    }

    /**
     * Initializes the TTR map by drawing all of the cities and the routes to the map. In addition,
     * it also sets the location for the map to the US as well as restricting some of the user
     * gestures.
     *
     * @param context The Android context. Can just pass in an instance of the Activity hosting the
     *                map fragment
     * @param map     the GoogleMap object which will be initialized
     */
    public static void initMap(final Context context, final GoogleMap map) {

        // set the map style
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style));

        // move camera to USA
        LatLng usa = new LatLng(39.8283, -98.5795);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(usa, 4.6f));

        // gets the bounds for all of the cities in the game
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MapCity city : MapCity.values())
            builder.include(city.getLatLng());
        final LatLngBounds bounds = builder.build();

        // disable all of the settings besides zooming and scrolling
        UiSettings settings = map.getUiSettings();
        settings.setAllGesturesEnabled(false);
        settings.setZoomGesturesEnabled(true);
        settings.setScrollGesturesEnabled(true);
        settings.setMapToolbarEnabled(false);

        // restrict the area for zooming and scrolling
        map.setMaxZoomPreference(MAX_ZOOM);
        map.setMinZoomPreference(MIN_ZOOM);
        map.setLatLngBoundsForCameraTarget(bounds);

        // draw the cities to the map
        double radius = CITY_RADIUS;
        for (MapCity city : MapCity.values()) {
            map.addCircle(new CircleOptions()
                    .center(city.getLatLng())
                    .radius(radius)
                    .fillColor(CITY_FILL)
                    .strokeColor(CITY_STROKE))
                    .setZIndex(1);
            addText(context, map, city.getLatLng(), city.getName(), 30, 15);
        }

        // draw the routes to the map
        for (MapRoute route : MapRoute.values()) {
            drawMapRoute(map, route, null);
        }
    }

    /**
     * Draws a route on the map
     *
     * @param map   the map that will be drawn on
     * @param route the route to draw
     * @param color the color to draw the route
     */
    public static void drawMapRoute(GoogleMap map, MapRoute route, Integer color) {
        routePolyLineMap.put(route, new HashSet<Polyline>());

        if (color == null) {
            color = route.getColor();
        }

        MapCity city1 = route.getCity1();
        MapCity city2 = route.getCity2();
        LatLng currPos = city1.getLatLng();
        LatLng destPos = city2.getLatLng();

        double heading = SphericalUtil.computeHeading(currPos, destPos);

        if (route.getDir() != 0) {
            currPos = SphericalUtil.computeOffset(currPos, CITY_RADIUS, heading + 90.0 * route.getDir());
            destPos = SphericalUtil.computeOffset(destPos, CITY_RADIUS, heading + 90.0 * route.getDir());
        }

        currPos = SphericalUtil.computeOffset(currPos, CITY_RADIUS, heading);
        destPos = SphericalUtil.computeOffsetOrigin(destPos, CITY_RADIUS, heading);

        double distance = SphericalUtil.computeDistanceBetween(currPos, destPos);

        int segments = route.getLength();

        int gaps = segments - 1;

        double gap = ROUTE_GAP;

        distance -= gap * gaps;

        double segment = distance / segments;

        heading = SphericalUtil.computeHeading(currPos, destPos);

        LatLng nextPos;
        for (int i = 0; i < segments; i++) {
            nextPos = SphericalUtil.computeOffset(currPos, segment, heading);
            heading = SphericalUtil.computeHeading(nextPos, destPos);
            Polyline line = map.addPolyline(new PolylineOptions()
                    .add(currPos, nextPos)
                    .color(color)
                    .width(ROUTE_WIDTH)
                    .clickable(true));
            line.setTag(route);
            currPos = SphericalUtil.computeOffset(nextPos, gap, heading);
            heading = SphericalUtil.computeHeading(currPos, destPos);
            routePolyLineMap.get(route).add(line);
        }
    }

    public static void changeColor(MapRoute route, int color) {
        for (Polyline line : routePolyLineMap.get(route)) {
            line.setColor(color);
        }
    }

    private static Marker addText(final Context context, final GoogleMap map,
                                  final LatLng location, final String text, final int padding,
                                  final int fontSize) {
        Marker marker = null;

        if (context == null || map == null || location == null || text == null
                || fontSize <= 0) {
            return marker;
        }

        final TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(fontSize);

        final Paint paintText = textView.getPaint();

        final Rect boundsText = new Rect();
        paintText.getTextBounds(text, 0, textView.length(), boundsText);
        paintText.setTextAlign(Paint.Align.CENTER);

        final Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        final Bitmap bmpText = Bitmap.createBitmap(boundsText.width() + 2
                * padding, boundsText.height() + 2 * padding, conf);

        final Canvas canvasText = new Canvas(bmpText);
        paintText.setColor(Color.WHITE);
        paintText.setShadowLayer(5, 5, 5, Color.BLACK);

        canvasText.drawText(text, canvasText.getWidth() / 2,
                canvasText.getHeight() - padding - boundsText.bottom, paintText);

        final MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .icon(BitmapDescriptorFactory.fromBitmap(bmpText))
                .zIndex(2)
                .anchor(0f, 0.75f);

        marker = map.addMarker(markerOptions);

        return marker;
    }
}
