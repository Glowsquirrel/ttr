package fysh340.ticket_to_ride.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.TextView;
import android.widget.Toast;

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

import fysh340.ticket_to_ride.R;

/**
 * Initializes the google maps for TTR
 *
 * @author Shun Sambongi
 * @version 1.0
 * @since 2017-07-22
 */
public class MapInitializer {

    public static void initMap(final Context context, final GoogleMap map) {

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style));

//        LatLng usa = new LatLng(39.8283, -98.5795);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usa, 4.6f));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MapCity city : MapCity.values())
            builder.include(city.getLatLng());
        final LatLngBounds bounds = builder.build();

        UiSettings settings = map.getUiSettings();
        settings.setAllGesturesEnabled(false);

        double radius = 30000;

        for (MapCity city : MapCity.values()) {
            map.addCircle(new CircleOptions()
                    .center(city.getLatLng())
                    .radius(radius)
                    .fillColor(Color.GRAY)
                    .strokeColor(Color.BLACK))
                    .setZIndex(1);
//            addText(this, mMap, city.getLatLng(), city.getName(), 30, 15);
        }

        for (MapRoute route : MapRoute.values()) {
            MapCity city1 = route.getCity1();
            MapCity city2 = route.getCity2();
            LatLng p1 = city1.getLatLng();
            LatLng p2 = city2.getLatLng();

            double heading = SphericalUtil.computeHeading(p1, p2);

            if (route.getDir() != 0) {
                p1 = SphericalUtil.computeOffset(p1, radius, heading + 90.0 * route.getDir());
                p2 = SphericalUtil.computeOffset(p2, radius, heading + 90.0 * route.getDir());
            }

            p1 = SphericalUtil.computeOffset(p1, radius, heading);
            p2 = SphericalUtil.computeOffsetOrigin(p2, radius, heading);

            double distance = SphericalUtil.computeDistanceBetween(p1, p2);

            int segments = route.getLength();

            int gaps = segments - 1;

            double gap = 30000;

            distance -= gap * gaps;

            double segment = distance / segments;

            heading = SphericalUtil.computeHeading(p1, p2);

            LatLng p3;
            for (int i = 0; i < segments; i++) {
                p3 = SphericalUtil.computeOffset(p1, segment, heading);
                heading = SphericalUtil.computeHeading(p3, p2);
                Polyline line = map.addPolyline(new PolylineOptions()
                        .add(p1, p3)
                        .color(route.getColor())
                        .width(20)
                        .clickable(true));
                line.setTag(route);
                p1 = SphericalUtil.computeOffset(p3, gap, heading);
                heading = SphericalUtil.computeHeading(p1, p2);
            }
        }

        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }
        });
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
