package fysh340.ticket_to_ride.game.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

import fysh340.ticket_to_ride.R;
import fysh340.ticket_to_ride.game.fragments.mapsupport.MapHelper;
import fysh340.ticket_to_ride.game.fragments.mapsupport.MapRoute;
import model.ClientModel;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnPolylineClickListener {

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private GoogleMap mMap;
    private List<Polyline> myPolylines = new ArrayList<>();
    private Marker savedMarker;

    public MapFragment() {
        // Required empty public constructor
    }

    public Marker getSavedMarker() {
        return savedMarker;
    }

    public void setSavedMarker(Marker savedMarker) {
        this.savedMarker = savedMarker;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapHelper.initMap(getActivity(), mMap);

        mMap.setOnPolylineClickListener(this);


        if (savedMarker != null) {
            onMarkerClick(savedMarker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(savedMarker.getPosition()));
        }

    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onPolylineClick(Polyline polyline) {
        // TODO: 7/24/17 do something when the polyline is clicked
        MapRoute route = (MapRoute) polyline.getTag();
        int key = route.getKey();
        Toast.makeText(getActivity(), String.valueOf(key), Toast.LENGTH_SHORT).show();
//        ClientModel.getMyClientModel().setCurrentlySelectedRouteID(key);
    }
}
