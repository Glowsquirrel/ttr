package fysh340.ticket_to_ride.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

import fysh340.ticket_to_ride.R;

import static fysh340.ticket_to_ride.R.id.map;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);

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


}
