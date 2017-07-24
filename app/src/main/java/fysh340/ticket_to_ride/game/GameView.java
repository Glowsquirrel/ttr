package fysh340.ticket_to_ride.game;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import fysh340.ticket_to_ride.R;
import fysh340.ticket_to_ride.game.fragments.AllPlayerDataFragment;
import fysh340.ticket_to_ride.game.fragments.ChatHistory;
import fysh340.ticket_to_ride.game.fragments.DeckFragment;
import fysh340.ticket_to_ride.game.fragments.MapFragment;
import fysh340.ticket_to_ride.game.fragments.PlayerCardsFragment;
import model.ClientModel;

public class GameView extends AppCompatActivity {
    ClientModel clientModel = ClientModel.getMyClientModel();
    private AllPlayerDataFragment playerData;
    private PlayerCardsFragment playerCardsFragment;
    private DeckFragment deckFragment;
    private ChatHistory chatHistory;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerList;
    //private SupportMapFragment mapFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        playerData = new AllPlayerDataFragment();
        playerCardsFragment = new PlayerCardsFragment();
        deckFragment = new DeckFragment();
        chatHistory=new ChatHistory();
        //getSupportFragmentManager().beginTransaction().add()


        FragmentManager fm = this.getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();

        MapFragment mapFragment = MapFragment.newInstance();
        PlayerCardsFragment playerCardsFragment = new PlayerCardsFragment();
        AllPlayerDataFragment playerDataFragment = new AllPlayerDataFragment();
        DeckFragment deckFragment = new DeckFragment();

        transaction.add(R.id.map_fragment_container, mapFragment);
        transaction.add(R.id.cards_fragment_container, playerCardsFragment);
        transaction.add(R.id.players_fragment_container, playerDataFragment);
        transaction.add(R.id.deck_fragment_container, deckFragment);



        transaction.commit();
        

    }

    @Override
    public void onBackPressed(){
        //finish();
        //do nothing on back pressed
    }
    
}
