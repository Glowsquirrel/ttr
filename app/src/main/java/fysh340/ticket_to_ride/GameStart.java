package fysh340.ticket_to_ride;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import fysh340.ticket_to_ride.fragments.MapFragment;
import fysh340.ticket_to_ride.map.MapInitializer;
import model.ClientModel;

public class GameStart extends AppCompatActivity implements OnMapReadyCallback {
    ClientModel clientModel = ClientModel.getMyClientModel();
    private PlayerData playerData;
    private PlayerCards playerCards;
    private Deck deck;
    private ChatHistory chatHistory;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerList;
    //private SupportMapFragment mapFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        playerData = new PlayerData();
        playerCards = new PlayerCards();
        deck = new Deck();
        chatHistory=new ChatHistory();
        //getSupportFragmentManager().beginTransaction().add()


        FragmentManager fm = this.getSupportFragmentManager();

        MapFragment mapFragment = MapFragment.newInstance();

        fm.beginTransaction().add(R.id.map_fragment_container, mapFragment).commit();
        


        //MapFragment mapFragment = MapFragment.newInstance();
        //getSupportFragmentManager().beginTransaction().add(R.id.map_fragment_container, mapFragment).commit();

        /*
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        mDrawerList = (FrameLayout) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        transaction.add(R.id.cards_fragment_container, playerCards);
        transaction.add(R.id.deck_fragment_container, deck);
        transaction.add(R.id.players_fragment_container, playerData);
        transaction.add(R.id.left_drawer,chatHistory);
//        transaction.add(R.id.map_fragment_container, mapFragment);

        transaction.commit();
        */

    }
    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapInitializer.initMap(this, googleMap);
    }
}
