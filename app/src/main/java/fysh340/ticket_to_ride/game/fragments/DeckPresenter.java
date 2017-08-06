package fysh340.ticket_to_ride.game.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private ImageView[] mFaceUpCards;
    private TextView mSelectedRoute;
    private TextView mFaceDownCards;
    private TextView mDestinationDeck;
    private int FLIP_DURATION = 200;

    //Constructors

    /**
     * <h1>DeckPresenter</h1>
     * Constructs a default Android fragment, which will be used as part of the MasterGamePresenter
     */
    public DeckPresenter() {
        // Required empty public constructor
    }

    /**
     * <h1>update</h1>
     * Updates any one of the face-up cards, train card and destination card decks, as well as
     * the route currently selected, depending on whether the model notifies this object through
     * the update method and shows a changed state
     *
     * @pre none
     * @post Updated model data will be reflected in the deckView
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void update() {
        //Check for updated face-up cards
        if (mGame.iHaveDifferentFaceUpCards()) {
            mGame.iHaveDifferentFaceUpCards(false);
            repopulateFaceUpCards();
        }

        //Check for new selected route
        if (mGame.routeIDHasChanged()) {
            Route currentlySelected = Route.getRouteByID(mGame.getCurrentlySelectedRouteID());
            String routeText = "Claim " + currentlySelected.getStartCity().getPrettyName() + " to "
                    + currentlySelected.getEndCity().getPrettyName() + " Route";
            mSelectedRoute.setText(routeText);
        }

        //Check if destination cards are being drawn
        if (mGame.iHavePossibleDestCards()) {
            mGame.iHavePossibleDestCards(false);
            ((MasterGamePresenter) getActivity()).switchDeckFragment();
        }

        //Check deck sizes
        String deckDescription;
        if (mGame.iHaveDifferentTrainDeckSize()) {
            mGame.iHaveDifferentTrainDeckSize(false);
            deckDescription = String.valueOf(mGame.getTrainCardDeckSize());
            //if (!mFaceDownCards.getText().equals("0")) //as long as there are cards in the deck, keep flipping
            flipView(mFaceDownCards.getBackground(), mFaceDownCards, 50, deckDescription);
        }
        if (mGame.iHaveDifferentDestDeckSize()) {
            mGame.iHaveDifferentDestDeckSize(false);
            deckDescription = String.valueOf(mGame.getDestCardDeckSize());
            flipView(mDestinationDeck.getBackground(), mDestinationDeck, 0, deckDescription);
        }

    }

    /**
     * <h1>onCreate</h1>
     * Sets up the fragment prior to creating a view
     *
     * @param savedInstanceState Any data to be restored
     * @pre none
     * @post The fragment will be created, ready for a view
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get ready for updates from the model
        mGame.register(this);
    }

    /**
     * <h1>onCreateView</h1>
     * Inflates the fragment_deck layout to create a deckView, which is then populated with
     * TextViews representing the face-up cards, train card and destination card decks, as well as
     * the currently selected route. OnClickListeners are established here for each, with each
     * calling a ServerProxy method, which will ultimately update the Game model object, update
     * this object to make updates to the deckView.
     *
     * @param inflater           The utility to convert the layout to a View
     * @param container          Allows children views to be a part of this one where
     *                           necessary
     * @param savedInstanceState Any data to be restored
     * @return deckView            The view being created here, once it is set up
     * @pre The fragment is created
     * @pre All needed layouts are available
     * @pre repopulateFaceUpCards method sets text and color for face-up cards per model
     * @post deckView will contain accurate data, based on the current model data
     * @post deckView TextViews' clicks will udpate the model through the ServerProxy object
     * or switch to the DestCardPresenter in the case of the mDestinationDeck TextView
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Get the view
        View deckView = inflater.inflate(R.layout.fragment_deck, container, false);

        //Set all the face-up cards
        mFaceUpCards = new ImageView[5];
        mFaceUpCards[0] = (ImageView) deckView.findViewById(R.id.first);
        mFaceUpCards[1] = (ImageView) deckView.findViewById(R.id.second);
        mFaceUpCards[2] = (ImageView) deckView.findViewById(R.id.third);
        mFaceUpCards[3] = (ImageView) deckView.findViewById(R.id.fourth);
        mFaceUpCards[4] = (ImageView) deckView.findViewById(R.id.fifth);

        List<Boolean> initializeFaceUpTrue = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            initializeFaceUpTrue.add(true);
        mGame.setFaceUpDifferences(initializeFaceUpTrue);
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
                Route sis;
                if(route.getSisterRouteKey()!=-1)
                {sis=Route.getRouteByID(route.getSisterRouteKey());}
                else
                {
                    sis=null;
                }
                if(route.isClaimed())
                {
                    Toast.makeText(getActivity(), "Route Already Claimed", Toast.LENGTH_SHORT).show();
                }
                else if((route.getLength()>mGame.getMyself().getNumTrains()))
                {
                    Toast.makeText(getActivity(), "You don't have enough cars!", Toast.LENGTH_SHORT).show();
                }
                else if(sis!=null&&sis.isClaimed()&&Game.getGameInstance().getVisiblePlayerInformation().size()<4)
                {
                    Toast.makeText(getActivity(), "Double Route already claimed!", Toast.LENGTH_SHORT).show();
                }
                else if(sis!=null&&Route.getRouteByID(route.getSisterRouteKey()).isClaimed()&&Route.getRouteByID(route.getSisterRouteKey()).getUser()==Game.getGameInstance().getMyself().getMyUsername())
                {
                    Toast.makeText(getActivity(), "You can't claim double routes!", Toast.LENGTH_SHORT).show();
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
     * <h1>Repopulate Face-up Cards</h1>
     * Sets the TextView text and color to represent the associated face-up card
     *
     * @pre mFaceUpCards[0:5] contains active TextViews
     * @post Each TextView in mFaceUpCards will accurately represent the model's face up cards
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void repopulateFaceUpCards() {
        //Get train cards that are up-for-grabs
        List<Integer> cardsByID = mGame.getFaceUpCards();
        List<Boolean> faceUpDifferences = mGame.getFaceUpDifferences();
        int faceupl = mFaceUpCards.length;
        //For each view, set the view text to the train card color/type
        int cardFlipDelay = 0;
        int NEXT_FLIP_DELAY = 50;
        int numCardsFlipped = 0;
        int cardIndex;
        for (cardIndex = 0; cardIndex < cardsByID.size(); cardIndex++) {
            boolean cardIsDifferent = faceUpDifferences.get(cardIndex);
            if (!cardIsDifferent)
                continue;
            TrainCard nextCard = TrainCard.getTrainCard(cardsByID.get(cardIndex));

            final Drawable drawable;
            final ImageView imageView = mFaceUpCards[cardIndex];

            switch (nextCard) {
                case RED:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.red, null);
                    break;
                case BLUE:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.blue, null);
                    break;
                case GREEN:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.green, null);
                    break;
                case YELLOW:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.yellow, null);
                    break;
                case BLACK:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.black, null);
                    break;
                case PURPLE:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.purple, null);
                    break;
                case ORANGE:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.orange, null);
                    break;
                case WHITE:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.white, null);
                    break;
                case WILD:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.wild, null);
                    break;
                default:
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tickettoride, null);
                    break;
            }

            flipView(drawable, imageView, cardFlipDelay, null);
            cardFlipDelay += NEXT_FLIP_DELAY;
            numCardsFlipped++;

            mFaceUpCards[cardIndex].setEnabled(true);
        }

        if (cardIndex < faceupl) {
            final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.tickettoride, null);
            final ImageView imageView = mFaceUpCards[cardIndex];

            flipView(drawable, imageView, cardFlipDelay, null);
            mFaceUpCards[cardIndex].setEnabled(false);
        }


        while (numCardsFlipped-- > 0 && mFaceDownCards != null){
            if (mGame.getTrainCardDeckSize() == 0)
                break;
            String flipCounter = String.valueOf(mGame.getTrainCardDeckSize() + numCardsFlipped);
            flipView(mFaceDownCards.getBackground(), mFaceDownCards, cardFlipDelay, flipCounter);
            cardFlipDelay += FLIP_DURATION;
        }
    }

    private void flipView(final Drawable drawable, final View view, int flipDelay, final String newText){
        ObjectAnimator animStage1 = (ObjectAnimator) AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_stage_1);
        final ObjectAnimator animStage2 = (ObjectAnimator) AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_stage_2);
        animStage1.setTarget(view);
        animStage2.setTarget(view);
        animStage1.setDuration(FLIP_DURATION/2);
        animStage2.setDuration(FLIP_DURATION/2);
        animStage1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if (view instanceof ImageView)
                    ((ImageView) view).setImageDrawable(drawable);
                if (view instanceof TextView && newText != null)
                    ((TextView) view).setText(String.valueOf(newText));
                animStage2.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animStage2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        if (drawable != null) {
            animStage1.setStartDelay(flipDelay);
            animStage1.start();
        }
    }



}