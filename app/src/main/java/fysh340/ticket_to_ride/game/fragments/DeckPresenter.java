package fysh340.ticket_to_ride.game.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import fysh340.ticket_to_ride.R;
import fysh340.ticket_to_ride.game.MasterGamePresenter;
import fysh340.ticket_to_ride.game.fragments.gameplaystate.ClientState;
import interfaces.Observer;
import model.Game;
import model.Route;
import model.TrainCard;

import static model.TrainCard.WILD;

/**
 *  <h1>Deck Presenter Fragment</h1>
 *  Creates a deckView, which shows face-up and face-down deck cards, as well as the destination
 *  card deck and selected route information.
 *
 *  @author         Nathan Finch
 *  @since          7-22-17
 */
public class DeckPresenter extends Fragment implements Observer {
    //Data Members
    
    //Constant labels for the mFaceDownCards and mDestinationDeck TestViews
    //private static final String TRAIN_DECK = "Train Card Deck\n";
    //private static final String DEST_DECK = "Dest. Cards\n";
    
    //Model object is referenced when it notifies this object of an update
    private Game mGame = Game.getGameInstance();

    //The interactive parts of the view
    private TextView[] mFaceUpCards;
    private TextView mSelectedRoute;
    private TextView mFaceDownCards;
    private TextView mDestinationDeck;

    //Constructors
    
    /**
     *  <h1>DeckPresenter</h1>
     *  Constructs a default Android fragment, which will be used as part of the MasterGamePresenter
     */
    public DeckPresenter() {
        // Required empty public constructor
    }
    
    /**
     *  <h1>update</h1>
     *  Updates any one of the face-up cards, train card and destination card decks, as well as
     *  the route currently selected, depending on whether the model notifies this object through
     *  the update method and shows a changed state
     *
     *  @pre    none
     *
     *  @post   Updated model data will be reflected in the deckView
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void update() {
        //Check for updated face-up cards
        if(mGame.iHaveDifferentFaceUpCards()) {
            mGame.iHaveDifferentFaceUpCards(false);
            repopulateFaceUpCards();
        }
        
        //Check for new selected route
        if(mGame.routeIDHasChanged()) {
            Route currentlySelected = Route.getRouteByID(mGame.getCurrentlySelectedRouteID());
            String routeText = "Claim " + currentlySelected.getStartCity().getPrettyName() + " to "
                                       + currentlySelected.getEndCity().getPrettyName() + " Route";
            mSelectedRoute.setText(routeText);
        }
        
        //Check if destination cards are being drawn
        if (mGame.iHavePossibleDestCards()){
            mGame.iHavePossibleDestCards(false);
            ((MasterGamePresenter)getActivity()).switchDeckFragment();
        }
    
        //Check deck sizes
        String deckDescription;
        if(mGame.iHaveDifferentTrainDeckSize()) {
            mGame.iHaveDifferentTrainDeckSize(false);
            //deckDescription = TRAIN_DECK + mGame.getTrainCardDeckSize();
            deckDescription = String.valueOf(mGame.getTrainCardDeckSize());
            mFaceDownCards.setText(deckDescription);
        }
        if(mGame.iHaveDifferentDestDeckSize()) {
            mGame.iHaveDifferentDestDeckSize(false);
            //deckDescription = DEST_DECK + mGame.getDestCardDeckSize();
            deckDescription = String.valueOf(mGame.getDestCardDeckSize());
            mDestinationDeck.setText(deckDescription);
        }
        
    }
    
    /**
     *  <h1>onCreate</h1>
     *  Sets up the fragment prior to creating a view
     *
     *  @param          savedInstanceState          Any data to be restored
     *
     *  @pre    none
     *
     *  @post   The fragment will be created, ready for a view
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Get ready for updates from the model
        mGame.register(this);
    }
    
    /**
     *  <h1>onCreateView</h1>
     *  Inflates the fragment_deck layout to create a deckView, which is then populated with
     *  TextViews representing the face-up cards, train card and destination card decks, as well as
     *  the currently selected route. OnClickListeners are established here for each, with each
     *  calling a ServerProxy method, which will ultimately update the Game model object, update
     *  this object to make updates to the deckView.
     *
     *  @param          inflater            The utility to convert the layout to a View
     *  @param          container           Allows children views to be a part of this one where
     *                                      necessary
     *  @param          savedInstanceState  Any data to be restored
     *
     *  @return         deckView            The view being created here, once it is set up
     *
     *  @pre    The fragment is created
     *  @pre    All needed layouts are available
     *  @pre    repopulateFaceUpCards method sets text and color for face-up cards per model
     *
     *  @post   deckView will contain accurate data, based on the current model data
     *  @post   deckView TextViews' clicks will udpate the model through the ServerProxy object
     *          or switch to the DestCardPresenter in the case of the mDestinationDeck TextView
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        //Get the view
        View deckView = inflater.inflate(R.layout.fragment_deck, container, false);

        //Set all the face-up cards
        mFaceUpCards = new TextView[5];
        mFaceUpCards[0] = (TextView)deckView.findViewById(R.id.first);
        mFaceUpCards[1] = (TextView)deckView.findViewById(R.id.second);
        mFaceUpCards[2] = (TextView)deckView.findViewById(R.id.third);
        mFaceUpCards[3] = (TextView)deckView.findViewById(R.id.fourth);
        mFaceUpCards[4] = (TextView)deckView.findViewById(R.id.fifth);
    
        repopulateFaceUpCards();
        
        //Make the face-up cards clickable
        for (int i = 0; i < mFaceUpCards.length; i++) {
            final int index = i;
            mFaceUpCards[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClientState.INSTANCE.getState().drawTrainCardFaceUp(mGame.getMyself().getMyUsername(),
                            mGame.getMyGameName(), index);
                }
            });
        }
        
        //Set the face-down card deck and make it clickable
        mFaceDownCards = (TextView) deckView.findViewById(R.id.trainDeck);
        //String deckDescription = TRAIN_DECK + mGame.getTrainCardDeckSize();
        String deckDescription = String.valueOf(mGame.getTrainCardDeckSize());
        mFaceDownCards.setText(deckDescription);
    
        mFaceDownCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientState.INSTANCE.getState().drawTrainCardFromDeck(mGame.getMyself().getMyUsername(), mGame.getMyGameName());
            }
        });
    
        //Set the destination card deck and make it clickable
        mDestinationDeck = (TextView) deckView.findViewById(R.id.destinationDeck);
        //deckDescription = DEST_DECK + mGame.getDestCardDeckSize();
        deckDescription = String.valueOf(mGame.getDestCardDeckSize());
        mDestinationDeck.setText(deckDescription);
    
        mDestinationDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientState.INSTANCE.getState().drawThreeDestCards(mGame.getMyself().getMyUsername(), mGame.getMyGameName());
            }
        });
    
        //Set the selected route are and make it clickable
        mSelectedRoute = (TextView)deckView.findViewById(R.id.selectedRoute);
        mSelectedRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedRoute.setText("");
                ArrayList<Integer> cards=new ArrayList<>();
                Route route=Route.getRouteByID(mGame.getCurrentlySelectedRouteID());
                if(route.isClaimed()||(route.getLength()>mGame.getMyself().getNumTrains()))
                {
                    Toast.makeText(getActivity(), "Route Already Claimed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                if(route.getOriginalColor()==WILD)
                {
                    ColorChoiceDialog cd=new ColorChoiceDialog();
                    cd.show(getActivity().getSupportFragmentManager(), "NoticeDialogFragment");
                }
                else
                {
                    int colored=mGame.getMyself().getNumOfTypeCards(route.getOriginalColor());
                    int wild=mGame.getMyself().getNumOfTypeCards(WILD);
                    int cardsLeft=route.getLength();
                    if(colored+wild>=route.getLength()) {
                        for(int i=0;i<colored;i++)
                        {
                            if(cardsLeft>0) {
                                cardsLeft--;
                                cards.add(TrainCard.getTrainCardKey(route.getOriginalColor()));
                            }


                        }
                        while(cardsLeft>0)
                        {
                            cards.add(TrainCard.getTrainCardKey(WILD));
                            cardsLeft--;
                        }
                        mGame.setCardsToDiscard(cards);
                        ClientState.INSTANCE.getState().claimRoute(mGame.getMyself().getMyUsername(), mGame.getMyGameName(),
                                mGame.getCurrentlySelectedRouteID(), cards);
//                        Toast.makeText(getActivity(), "Route Claimed Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "You don't have enough cards!", Toast.LENGTH_SHORT).show();
                    }


                }
                }
            }
        });

        return deckView;
    }
    
    /**
     *  <h1>Repopulate Face-up Cards</h1>
     *  Sets the TextView text and color to represent the associated face-up card
     *
     *  @pre    mFaceUpCards[0:5] contains active TextViews
     *
     *  @post   Each TextView in mFaceUpCards will accurately represent the model's face up cards
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void repopulateFaceUpCards() {
        //Get train cards that are up-for-grabs
        List<Integer> cardsByID = mGame.getFaceUpCards();
        int faceupl=mFaceUpCards.length;
        int i = 0;
        //For each view, set the view text to the train card color/type
        for(i=0; i < cardsByID.size(); ++i) {
            TrainCard nextCard = TrainCard.getTrainCard(cardsByID.get(i));
            switch(nextCard) {
                case RED:
                    mFaceUpCards[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.red, null));
                    //mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case BLUE:
                    mFaceUpCards[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.blue, null));
                    //mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case GREEN:
                    mFaceUpCards[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.green, null));
                    //mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case YELLOW:
                    mFaceUpCards[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.yellow, null));
                    //mFaceUpCards[i].setTextColor(getResources().getColor(R.color.black));
                    break;
                case BLACK:
                    mFaceUpCards[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.black, null));
                    //mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case PURPLE:
                    mFaceUpCards[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.purple, null));
                   //mFaceUpCards[i].setTextColor(getResources().getColor(R.color.white));
                    break;
                case ORANGE:
                    mFaceUpCards[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.orange, null));
                    //mFaceUpCards[i].setTextColor(getResources().getColor(R.color.black));
                    break;
                case WHITE:
                    mFaceUpCards[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.white, null));
                    //mFaceUpCards[i].setTextColor(getResources().getColor(R.color.black));
                    break;
                case WILD:
                    mFaceUpCards[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.wild, null));
                    //mFaceUpCards[i].setTextColor(getResources().getColor(R.color.black));
                    break;
                default:
                    break;
            }
            //String cardTitle = "Draw " + nextCard.getPrettyname() + " Card";
            //mFaceUpCards[i].setText(cardTitle);
            mFaceUpCards[i].setEnabled(true);

        }
        if(i<faceupl)
        {
            mFaceUpCards[i].setBackgroundColor(getResources().getColor(R.color.white));
            mFaceUpCards[i].setTextColor(getResources().getColor(R.color.black));
            String cardTitle = "Out of cards!";
            mFaceUpCards[i].setText(cardTitle);
            mFaceUpCards[i].setEnabled(false);
            i++;
        }
    }
}
