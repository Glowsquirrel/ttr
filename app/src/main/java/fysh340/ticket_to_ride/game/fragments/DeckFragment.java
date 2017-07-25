package fysh340.ticket_to_ride.game.fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import fysh340.ticket_to_ride.R;
import fysh340.ticket_to_ride.game.GameView;
import interfaces.Observer;
import model.Game;
import model.Route;
import model.TrainCard;
import serverproxy.ServerProxy;

import static fysh340.ticket_to_ride.R.drawable.grad;
import static model.TrainCard.RED;

/**
 *  <h1>DeckFragment Fragment</h1>
 *  Creates a view, which shows face-up deck cards and available destination cards when drawing
 *  from that deck, for the in-game activity.
 *
 *  @author         Nathan Finch
 *  @since          7-22-17
 */
public class DeckFragment extends Fragment implements Observer {
    private static final String TRAIN_DECK = "Train Card Deck\n";
    private static final String DEST_DECK = "Dest. Cards\n";
    
    private ServerProxy mServerProxy = new ServerProxy();
    private Game mGame = Game.getGameInstance();

    private TextView[] mFaceUpCards;
    private List<Integer> mDrawnTrainCards;
    
    private TextView mSelectedRoute;
    
    private TextView mFaceDownCards;
    private TextView mDestinationDeck;

    public DeckFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void update() {
        if(mGame.iHaveDifferentFaceUpCards()) {
            repopulateFaceUpCards();
        }
        if(mGame.routeIDHasChanged()) {
            Route currentlySelected = Route.getRouteByID(mGame.getCurrentlySelectedRouteID());
            String routeText = "Claim " + currentlySelected.getStartCity().getPrettyName() + " to "
                                       + currentlySelected.getEndCity().getPrettyName() + " Route";
            mSelectedRoute.setText(routeText);
        }
        if (mGame.iHavePossibleDestCards()){
            mGame.iHavePossibleDestCards(false);
            ((GameView)getActivity()).switchDeckFragment();
        }
        String deckDescription = TRAIN_DECK + mGame.getTrainCards().size();
        mFaceDownCards.setText(deckDescription);
    
        deckDescription = DEST_DECK + mGame.getPossibleDestCards().size();
        mDestinationDeck.setText(deckDescription);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGame.register(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        mGame.register(this);
        
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
    
        mDrawnTrainCards = new ArrayList<>();
        
        mFaceUpCards[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;
                mServerProxy.drawTrainCardFromFaceUp(mGame.getMyself().getMyUsername(),
                                                        mGame.getMyGameName(), index);
            }
        });
    
        mFaceUpCards[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 1;
                mServerProxy.drawTrainCardFromFaceUp(mGame.getMyself().getMyUsername(),
                                                        mGame.getMyGameName(), index);
            }
        });
    
        mFaceUpCards[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 2;
                mServerProxy.drawTrainCardFromFaceUp(mGame.getMyself().getMyUsername(),
                                                        mGame.getMyGameName(), index);
            }
        });
    
        mFaceUpCards[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 3;
                mServerProxy.drawTrainCardFromFaceUp(mGame.getMyself().getMyUsername(),
                                                        mGame.getMyGameName(), index);
            }
        });
    
        mFaceUpCards[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 4;
                mServerProxy.drawTrainCardFromFaceUp(mGame.getMyself().getMyUsername(),
                                                        mGame.getMyGameName(), index);
            }
        });
        
        mFaceDownCards = (TextView) deckView.findViewById(R.id.trainDeck);
        String deckDescription = TRAIN_DECK + mGame.getTrainCardDeckSize();
        mFaceDownCards.setText(deckDescription);
        
        mDestinationDeck = (TextView) deckView.findViewById(R.id.destinationDeck);
        deckDescription = DEST_DECK + mGame.getDestinationCardDeckSize();
        mDestinationDeck.setText(deckDescription);

        setUpDeckListeners(deckView);
        
        mSelectedRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedRoute.setText("");
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void repopulateFaceUpCards() {
        //Get train cards that are up-for-grabs
        List<Integer> cardsByID = mGame.getFaceUpCards();
    
        //For each view, set the view text to the train card color/type
        for(int i = 0; i < mFaceUpCards.length; ++i) {
            TrainCard nextCard = TrainCard.getTrainCard(cardsByID.get(i));
            switch(nextCard) {
                case RED:
                    mFaceUpCards[i].setBackgroundColor(getResources().getColor(R.color.red));
                    mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case BLUE:
                    mFaceUpCards[i].setBackgroundColor(getResources().getColor(R.color.blue));
                    mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case GREEN:
                    mFaceUpCards[i].setBackgroundColor(getResources().getColor(R.color.green));
                    mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case YELLOW:
                    mFaceUpCards[i].setBackgroundColor(getResources().getColor(R.color.yellow));
                    break;
                case BLACK:
                    mFaceUpCards[i].setBackgroundColor(getResources().getColor(R.color.black));
                    mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case PURPLE:
                    mFaceUpCards[i].setBackgroundColor(getResources().getColor(R.color.purple));
                    mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case ORANGE:
                    mFaceUpCards[i].setBackgroundColor(getResources().getColor(R.color.orange));
                    break;
                case WHITE:
                    mFaceUpCards[i].setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case WILD:
                    mFaceUpCards[i].setBackgroundResource(R.drawable.grad);
                    break;
                default:
                    break;
            }
            String cardTitle = "Draw " + nextCard.getPrettyname() + " Card";
            mFaceUpCards[i].setText(cardTitle);
        }
    }
}
