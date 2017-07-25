package fysh340.ticket_to_ride.game.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fysh340.ticket_to_ride.R;
import fysh340.ticket_to_ride.game.GameView;
import interfaces.Observer;
import model.DestCard;
import model.Game;
import serverproxy.ServerProxy;

public class DestCardSelectFragment extends Fragment implements Observer{
    private Game mGame = Game.getGameInstance();
    private List<DestCard> possibleDestCards = new ArrayList<>();
    private List<DestCard> selectedDestCards = new ArrayList<>();
    private LinearLayout destCard1;
    private LinearLayout destCard2;
    private LinearLayout destCard3;

    public DestCardSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public void update() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        possibleDestCards = mGame.getPossibleDestCards();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get the view
        final View destCardSelectView = inflater.inflate(R.layout.fragment_dest_card_select, container, false);

        destCard1 = (LinearLayout) destCardSelectView.findViewById(R.id.dest_card_select_1);
        destCard2 = (LinearLayout) destCardSelectView.findViewById(R.id.dest_card_select_2);
        destCard3 = (LinearLayout) destCardSelectView.findViewById(R.id.dest_card_select_3);

        setUpDestCardListeners(destCardSelectView);


        Button confirmButton = (Button) destCardSelectView.findViewById(R.id.confirmButton);
        confirmButton.setEnabled(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerProxy serverProxy = new ServerProxy();
                for (DestCard destCard : possibleDestCards){
                    mGame.getMyself().addDestCard(destCard);
                }
                possibleDestCards.removeAll(selectedDestCards);
                for (DestCard destCard : possibleDestCards){
                    serverProxy.returnDestCards(mGame.getMyself().getMyUsername(), mGame.getMyGameName(), destCard.getMapValue());
                }
                selectedDestCards.clear();
                checkSelectedSize(destCardSelectView);
                ((GameView)getActivity()).switchDeckFragment();
            }
        });

        return destCardSelectView;
    }

    @Override
    public void onHiddenChanged(boolean hidden){
        if (!hidden && getView() != null){
            possibleDestCards = mGame.getPossibleDestCards();
            destCard1.setBackgroundResource(R.drawable.customborder);
            destCard2.setBackgroundResource(R.drawable.customborder);
            destCard3.setBackgroundResource(R.drawable.customborder);

            //set up card 1
            TextView card1city1 = (TextView) getView().findViewById(R.id.dest_card_select_1_city1);
            TextView card1city2 = (TextView) getView().findViewById(R.id.dest_card_select_1_city2);
            TextView card1score = (TextView) getView().findViewById(R.id.dest_card_select_1_score);
            DestCard destCard1 = possibleDestCards.get(0);
            card1city1.setText(destCard1.getStartCity().getPrettyName());
            card1city2.setText(destCard1.getEndCity().getPrettyName());
            card1score.setText(String.valueOf(destCard1.getPointValue()));

            //set up card 2
            TextView card2city1 = (TextView) getView().findViewById(R.id.dest_card_select_2_city1);
            TextView card2city2 = (TextView) getView().findViewById(R.id.dest_card_select_2_city2);
            TextView card2score = (TextView) getView().findViewById(R.id.dest_card_select_2_score);
            DestCard destCard2 = possibleDestCards.get(1);
            card2city1.setText(destCard2.getStartCity().getPrettyName());
            card2city2.setText(destCard2.getEndCity().getPrettyName());
            card2score.setText(String.valueOf(destCard2.getPointValue()));

            //set up card 3
            TextView card3city1 = (TextView) getView().findViewById(R.id.dest_card_select_3_city1);
            TextView card3city2 = (TextView) getView().findViewById(R.id.dest_card_select_3_city2);
            TextView card3score = (TextView) getView().findViewById(R.id.dest_card_select_3_score);
            DestCard destCard3 = possibleDestCards.get(2);
            card3city1.setText(destCard3.getStartCity().getPrettyName());
            card3city2.setText(destCard3.getEndCity().getPrettyName());
            card3score.setText(String.valueOf(destCard3.getPointValue()));

        }
    }

    private void setUpDestCardListeners(final View destCardsView){
            destCard1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (possibleDestCards.size() > 0) {
                        if (selectedDestCards.contains(possibleDestCards.get(0))) {
                            selectedDestCards.remove(possibleDestCards.get(0));
                            destCard1.setBackgroundResource(R.drawable.customborder);
                        } else {
                            selectedDestCards.add(possibleDestCards.get(0));
                            destCard1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.neon_grey));
                        }
                        checkSelectedSize(destCardsView);
                    }
                }
            });

            destCard2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (possibleDestCards.size() > 1) {
                        if (selectedDestCards.contains(possibleDestCards.get(1))) {
                            selectedDestCards.remove(possibleDestCards.get(1));
                            destCard2.setBackgroundResource(R.drawable.customborder);
                        } else {
                            selectedDestCards.add(possibleDestCards.get(1));
                            destCard2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.neon_grey));
                        }
                        checkSelectedSize(destCardsView);
                    }
                }
            });

            destCard3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (possibleDestCards.size() > 2) {
                        if (selectedDestCards.contains(possibleDestCards.get(2))) {
                            selectedDestCards.remove(possibleDestCards.get(2));
                            destCard3.setBackgroundResource(R.drawable.customborder);
                        } else {
                            selectedDestCards.add(possibleDestCards.get(2));
                            destCard3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.neon_grey));
                        }
                        checkSelectedSize(destCardsView);
                    }
                }
            });

    }

    private void checkSelectedSize(View destCardsView){
        Button confirmButton = (Button) destCardsView.findViewById(R.id.confirmButton);
        if (selectedDestCards.size() > 1){
            confirmButton.setEnabled(true);
        } else {
            confirmButton.setEnabled(false);
        }
    }
}