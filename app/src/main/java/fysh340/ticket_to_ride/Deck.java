package fysh340.ticket_to_ride;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import java.util.Map;
import interfaces.Observer;
import model.City;
import model.DestCard;
import model.TrainCard;
import static model.City.BOSTON;
import static model.City.MIAMI;


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
        List<Integer> cardsByID = mViewableDeck.getFaceUpCards();

        for(int i = 0; i < mFaceUpCards.length; ++i) {
            TrainCard nextCard = TrainCard.getTrainCard(cardsByID.get(i));
            mFaceUpCards[i].setText(nextCard.getPrettyname() + " Card");
        }

        cardsByID = mViewableDeck.getDestinationCards();

        //Just need the map, so card initialization values are irrelevant
        Map<Integer, DestCard> cardsReference = new DestCard(0, BOSTON, MIAMI, 12).getDestCardMap();

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
        View deckView = inflater.inflate(R.layout.fragment_deck, container, false);

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

        return deckView;
    }

}
