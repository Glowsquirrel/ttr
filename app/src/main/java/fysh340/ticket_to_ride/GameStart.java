package fysh340.ticket_to_ride;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import model.ClientModel;
import serverproxy.ServerProxy;

public class GameStart extends AppCompatActivity {
    ClientModel clientModel = ClientModel.getMyClientModel();
    private PlayerData playerData;
    private PlayerCards playerCards;
    private Deck deck;
    private ChatHistory chatHistory;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        playerData = new PlayerData();
        playerCards = new PlayerCards();
        deck = new Deck();
        chatHistory=new ChatHistory();

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        mDrawerList = (FrameLayout) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        transaction.add(R.id.cards_fragment_container, playerCards);
        transaction.add(R.id.deck_fragment_container, deck);
        transaction.add(R.id.players_fragment_container, playerData);
        transaction.add(R.id.left_drawer,chatHistory);


        transaction.commit();

    }
    @Override
    public void onBackPressed(){
        finish();
    }

}
