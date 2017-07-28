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

public class MasterGamePresenter extends AppCompatActivity implements Observer {
    ClientModel clientModel = ClientModel.getMyClientModel();
    private AllPlayersPresenter allPlayersPresenter;
    private TrainCardPresenter trainCardPresenter;
    private DestCardPresenter destCardPresenter;
    private DeckPresenter deckPresenter;
    private DestCardSelectPresenter destCardSelectPresenter;
    private ChatPresenter chatPresenter;
    private MapPresenter mapPresenter;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerList;
    private boolean showingTrainCards = true;
    private boolean showingDeck = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        this.trainCardPresenter = new TrainCardPresenter();
        deckPresenter = new DeckPresenter();
        this.chatPresenter = new ChatPresenter();
        
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        ChatPresenter chatPresenter = new ChatPresenter();
        mapPresenter = MapPresenter.newInstance();
        trainCardPresenter = new TrainCardPresenter();
        destCardPresenter = new DestCardPresenter();
        allPlayersPresenter = new AllPlayersPresenter();
        deckPresenter = new DeckPresenter();
        destCardSelectPresenter = new DestCardSelectPresenter();

        transaction.add(R.id.players_fragment_container, allPlayersPresenter);
        transaction.add(R.id.map_fragment_container, mapPresenter);
        transaction.add(R.id.left_drawer, chatPresenter);

        transaction.add(R.id.cards_fragment_container, trainCardPresenter);
        transaction.add(R.id.cards_fragment_container, destCardPresenter);
        transaction.hide(destCardPresenter);

        transaction.add(R.id.deck_fragment_container, deckPresenter);
        transaction.add(R.id.deck_fragment_container, destCardSelectPresenter);
        transaction.hide(destCardSelectPresenter);

        transaction.commit();
    }

    @Override
    public void onBackPressed(){
        //finish();
        //do nothing on back pressed
    }

    //toggle between the TrainCardPresenter view and the DestCardPresenter view
    public void switchPlayerCards(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        if (showingTrainCards){
            showingTrainCards = false;
            ft.hide(trainCardPresenter);
            ft.show(destCardPresenter);
        } else {
            showingTrainCards = true;
            ft.hide(destCardPresenter);
            ft.show(trainCardPresenter);
        }
        ft.commit();
    }

    //toggle between the DeckPresenter view and the DestCardSelectPresenter view
    public void switchDeckFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (showingDeck){
            showingDeck = false;
            ft.hide(deckPresenter);
            ft.show(destCardSelectPresenter);
        } else{
            showingDeck = true;
            ft.hide(destCardSelectPresenter);
            ft.show(deckPresenter);
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
