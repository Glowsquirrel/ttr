package fysh340.ticket_to_ride.game;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import fysh340.ticket_to_ride.R;
import fysh340.ticket_to_ride.game.fragments.*;
import interfaces.Observer;
import model.ClientModel;
import model.Game;
import model.ServerError;

public class GameView extends AppCompatActivity implements Observer {
    ClientModel clientModel = ClientModel.getMyClientModel();
    private AllPlayerDataFragment allPlayerDataFragment;
    private PlayerTrainCardsFragment playerTrainCardsFragment;
    private PlayerDestCardsFragment playerDestCardsFragment;
    private DeckFragment deckFragment;
    private DestCardSelectFragment destCardSelectFragment;
    private ChatHistory chatHistory;
    private MapFragment mapFragment;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerList;
    private boolean showingTrainCards = true;
    private boolean showingDeck = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        this.playerTrainCardsFragment = new PlayerTrainCardsFragment();
        deckFragment = new DeckFragment();
        chatHistory = new ChatHistory();

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        ChatHistory chatHistory = new ChatHistory();
        mapFragment = MapFragment.newInstance();
        playerTrainCardsFragment = new PlayerTrainCardsFragment();
        playerDestCardsFragment = new PlayerDestCardsFragment();
        allPlayerDataFragment = new AllPlayerDataFragment();
        deckFragment = new DeckFragment();
        destCardSelectFragment = new DestCardSelectFragment();

        transaction.add(R.id.players_fragment_container, allPlayerDataFragment);
        transaction.add(R.id.map_fragment_container, mapFragment);
        transaction.add(R.id.left_drawer, chatHistory);

        transaction.add(R.id.cards_fragment_container, playerTrainCardsFragment);
        transaction.add(R.id.cards_fragment_container, playerDestCardsFragment);
        transaction.hide(playerDestCardsFragment);

        transaction.add(R.id.deck_fragment_container, deckFragment);
        transaction.add(R.id.deck_fragment_container, destCardSelectFragment);
        transaction.hide(destCardSelectFragment);

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

    public void switchDeckFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (showingDeck){
            showingDeck = false;
            ft.hide(deckFragment);
            ft.show(destCardSelectFragment);
        } else{
            showingDeck = true;
            ft.hide(destCardSelectFragment);
            ft.show(deckFragment);
        }

        ft.commit();
    }

    @Override
    public void update() {
        ServerError serverError = Game.getGameInstance().getServerError();
        String message = serverError.getMessage();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Game.getGameInstance().getServerError().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Game.getGameInstance().getServerError().unregister(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Game.getGameInstance().notifyObserver();
    }
}
