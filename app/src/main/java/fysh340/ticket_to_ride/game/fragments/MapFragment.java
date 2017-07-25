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
import interfaces.IServer;
import interfaces.Observer;
import model.ClientModel;
import model.Game;
import model.MapModel;
import serverproxy.ServerProxy;


public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnPolylineClickListener, Observer {

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    private GoogleMap mMap;
    private List<Polyline> myPolylines = new ArrayList<>();
    private Marker savedMarker;
    private MapModel mMapModel = MapModel.getMapInstance();
    private IServer mServerProxy = new ServerProxy();
    private Game mGame = Game.getGameInstance();
    private ClientModel mClientModel = ClientModel.getMyClientModel();

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

        mMapModel.register(this);

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
        MapRoute route = (MapRoute) polyline.getTag();
        int key = route.getKey();
        Toast.makeText(getActivity(), String.valueOf(key), Toast.LENGTH_SHORT).show();
        // TODO: 7/25/17 get actual cards from somewhere 
        mServerProxy.claimRoute(mClientModel.getMyUsername(), mGame.getMyGameName(), key, new ArrayList<Integer>());
    }

    @Override
    public void update() {
        int color = getResources().getColor(mMapModel.getColor());
        int routeID = mMapModel.getLastRoute();
        MapRoute route = MapRoute.getRoute(routeID);
        MapHelper.changeColor(route, color);
    }
}
