package fysh340.ticket_to_ride;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import fysh340.ticket_to_ride.fragments.MapFragment;
import fysh340.ticket_to_ride.fragments.PlayerCards;
import fysh340.ticket_to_ride.fragments.PlayerData;
import model.ClientModel;

public class GameStart extends AppCompatActivity {
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

        FragmentTransaction transaction = fm.beginTransaction();

        MapFragment mapFragment = MapFragment.newInstance();
        PlayerCards playerCardsFragment = new PlayerCards();
        PlayerData playerDataFragment = new PlayerData();

        transaction.add(R.id.map_fragment_container, mapFragment);
        transaction.add(R.id.cards_fragment_container, playerCardsFragment);
        transaction.add(R.id.players_fragment_container, playerDataFragment);



        transaction.commit();
        

    }

    @Override
    public void onBackPressed(){
        //finish();
        //do nothing on back pressed
    }
    
}
