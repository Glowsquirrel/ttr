package fysh340.ticket_to_ride.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import fysh340.ticket_to_ride.game.fragments.PlayerDestCardsFragment;
import fysh340.ticket_to_ride.game.fragments.PlayerTrainCardsFragment;
import model.ClientModel;

public class GameView extends AppCompatActivity {
    ClientModel clientModel = ClientModel.getMyClientModel();
    private AllPlayerDataFragment allPlayerDataFragment;
    private PlayerTrainCardsFragment playerTrainCardsFragment;
    private PlayerDestCardsFragment playerDestCardsFragment;
    private DeckFragment deckFragment;
    private ChatHistory chatHistory;
    private MapFragment mapFragment;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerList;
    private boolean showingTrainCards = true;
    //private SupportMapFragment mapFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        this.playerTrainCardsFragment = new PlayerTrainCardsFragment();
        deckFragment = new DeckFragment();
        chatHistory=new ChatHistory();

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        mapFragment = MapFragment.newInstance();
        playerTrainCardsFragment = new PlayerTrainCardsFragment();
        playerDestCardsFragment = new PlayerDestCardsFragment();
        allPlayerDataFragment = new AllPlayerDataFragment();
        deckFragment = new DeckFragment();

        transaction.add(R.id.map_fragment_container, mapFragment);

        transaction.add(R.id.cards_fragment_container, playerTrainCardsFragment);
        transaction.add(R.id.cards_fragment_container, playerDestCardsFragment);
        transaction.hide(playerDestCardsFragment);
        transaction.add(R.id.players_fragment_container, allPlayerDataFragment);
        transaction.add(R.id.deck_fragment_container, deckFragment);

        transaction.commit();

    }

    @Override
    public void onBackPressed(){
        //finish();
        //do nothing on back pressed
    }

    public void switchPlayerCards(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        if (showingTrainCards){
            showingTrainCards = false;
            ft.hide(playerTrainCardsFragment);
            ft.show(playerDestCardsFragment);
        } else {
            showingTrainCards = true;
            ft.hide(playerDestCardsFragment);
            ft.show(playerTrainCardsFragment);
        }
        ft.commit();
    }
}
