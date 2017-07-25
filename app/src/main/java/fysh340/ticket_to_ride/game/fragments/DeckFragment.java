package fysh340.ticket_to_ride.game.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import fysh340.ticket_to_ride.R;
import interfaces.Observer;
import model.Game;
import model.Route;
import model.TrainCard;
import serverproxy.ServerProxy;

/**
 *  <h1>DeckFragment Fragment</h1>
 *  Creates a view, which shows face-up deck cards and available destination cards when drawing
 *  from that deck, for the in-game activity.
 *
 *  @author         Nathan Finch
 *  @since          7-22-17
 */
public class DeckFragment extends Fragment implements Observer {
    private ServerProxy mServerProxy = new ServerProxy();
    private Game mGame = Game.getGameInstance();

    private TextView[] mFaceUpCards;
    
    private TextView mSelectedRoute;

    public DeckFragment() {
        // Required empty public constructor
    }

    @Override
    public void update() {
        if(mGame.iHaveDifferentFaceUpCards()) {
            repopulateFaceUpCards();
        }
        if(mGame.routeIDHasChanged()) {
            Route currentlySelected = Route.getRouteByID(mGame.getCurrentlySelectedRouteID());
            String routeText = currentlySelected.getStartCity().getPrettyName() + " to " +
                                       currentlySelected.getEndCity().getPrettyName();
            mSelectedRoute.setText(routeText);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get the view
        View deckView = inflater.inflate(R.layout.fragment_deck, container, false);

        //Set all the parts
        mFaceUpCards = new TextView[5];
        mFaceUpCards[0] = (TextView)deckView.findViewById(R.id.first);
        mFaceUpCards[1] = (TextView)deckView.findViewById(R.id.second);
        mFaceUpCards[2] = (TextView)deckView.findViewById(R.id.third);
        mFaceUpCards[3] = (TextView)deckView.findViewById(R.id.fourth);
        mFaceUpCards[4] = (TextView)deckView.findViewById(R.id.fifth);
    
        repopulateFaceUpCards();
        
        mSelectedRoute = (TextView)deckView.findViewById(R.id.selectedRoute);

        //Set click listeners for each view
        for(int i = 0; i < mFaceUpCards.length; ++i) {
            mFaceUpCards[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ServerProxy toServer = new ServerProxy();
                    //TODO: Find a way to get index within the inner class or don't use a loop
                }
            });
        }

        setUpDeckListeners(deckView);

        Button myContinueButton = (Button) deckView.findViewById(R.id.continueButton);
        myContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        
        Button claimRouteButton = (Button)deckView.findViewById(R.id.chooseRouteButton);
        claimRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerProxy.claimRoute(mGame.getMyself().getMyUsername(), mGame.getMyGameName(),
                                        mGame.getCurrentlySelectedRouteID(), mGame.getTrainCards());
            }
        });

        return deckView;
    }

    private void setUpDeckListeners(View deckView){
        TextView trainDeck = (TextView) deckView.findViewById(R.id.trainDeck);
        trainDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerProxy.drawTrainCardFromDeck(mGame.getMyself().getMyUsername(), mGame.getMyGameName());
            }
        });

        TextView destDeck = (TextView) deckView.findViewById(R.id.destinationDeck);
        destDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerProxy.drawThreeDestCards(mGame.getMyself().getMyUsername(), mGame.getMyGameName());
            }
        });

    }

    private void repopulateFaceUpCards() {
        //Get train cards that are up-for-grabs
        List<Integer> cardsByID = mGame.getFaceUpCards();
    
        //For each view, set the view text to the train card color/type
        for(int i = 0; i < mFaceUpCards.length; ++i) {
            TrainCard nextCard = TrainCard.getTrainCard(cardsByID.get(i));
            String cardTitle = nextCard.getPrettyname() + " Card";
            mFaceUpCards[i].setText(cardTitle);
        }
    }
}
