package fysh340.ticket_to_ride.game.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import fysh340.ticket_to_ride.R;
import interfaces.Observer;
import model.City;
import model.DestCard;
import model.TrainCard;
import serverproxy.ServerProxy;

/**
 *  <h1>Deck Fragment</h1>
 *  Creates a view, which shows face-up deck cards and available destination cards when drawing
 *  from that deck, for the in-game activity.
 *
 *  @author         Nathan Finch
 *  @since          7-22-17
 */
public class Deck extends Fragment implements Observer {
    private static model.Deck mViewableDeck;
    static {
        mViewableDeck = model.Deck.getInstance();
    }

    private TextView[] mFaceUpCards;

    private TextView[] mDestinationCards;

    public Deck() {
        // Required empty public constructor
    }

    @Override
    public void update() {
        //Get train cards that are up-for-grabs
        List<Integer> cardsByID = mViewableDeck.getFaceUpCards();

        //For each view, set the view text to the train card color/type
        for(int i = 0; i < mFaceUpCards.length; ++i) {
            TrainCard nextCard = TrainCard.getTrainCard(cardsByID.get(i));
            mFaceUpCards[i].setText(nextCard.getPrettyname() + " Card");
        }

        //When drawing destination cards, get the available ones
        cardsByID = mViewableDeck.getDestinationCards();
        
        Map<Integer, DestCard> cardsReference = DestCard.createDestCardMap();

        //For each view, set the text to the destination's from/to cities.
        for(int i = 0; i < mDestinationCards.length; ++i) {
            DestCard nextCard = cardsReference.get(cardsByID.get(i));
            City from = nextCard.getStartCity();
            City to = nextCard.getEndCity();
            mDestinationCards[i].setText(from.getPrettyName() + " to " + to.getPrettyName());
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

        mDestinationCards = new TextView[3];
        mDestinationCards[0] = (TextView)deckView.findViewById(R.id.destination1);
        mDestinationCards[1] = (TextView)deckView.findViewById(R.id.destination2);
        mDestinationCards[2] = (TextView)deckView.findViewById(R.id.destination3);

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

        for(int i = 0; i < mDestinationCards.length; ++i) {
            mDestinationCards[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return deckView;
    }

}
