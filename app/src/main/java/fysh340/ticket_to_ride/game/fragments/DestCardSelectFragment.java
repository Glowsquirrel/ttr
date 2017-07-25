package fysh340.ticket_to_ride.game.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fysh340.ticket_to_ride.R;
import interfaces.Observer;
import model.DestCard;
import model.Game;
import serverproxy.ServerProxy;

public class DestCardSelectFragment extends Fragment implements Observer{
    private Game mGame = Game.getGameInstance();
    private List<DestCard> possibleDestCards = new ArrayList<>();
    private boolean atLeastOneDestCardSelected = false;


    public DestCardSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public void update() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //possibleDestCards = mGame.getPossibleDestCards();
        possibleDestCards.add(DestCard.getDestCardByID(0));
        possibleDestCards.add(DestCard.getDestCardByID(1));
        possibleDestCards.add(DestCard.getDestCardByID(2));
    }

    private List<DestCard> selectedDestCards = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get the view
        View destCardSelectView = inflater.inflate(R.layout.fragment_dest_card_select, container, false);

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
            }
        });

        return destCardSelectView;
    }

    private void setUpDestCardListeners(final View destCardsView){
        if (possibleDestCards.size() > 0) {
            final LinearLayout destCard1 = (LinearLayout) destCardsView.findViewById(R.id.dest_card_select_1);
            destCard1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedDestCards.contains(possibleDestCards.get(0))) {
                        destCard1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        selectedDestCards.remove(possibleDestCards.get(0));
                    } else{
                        destCard1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.neon_grey));
                        selectedDestCards.add(possibleDestCards.get(0));
                    }
                    checkSelectedSize(destCardsView);
                }
            });
        }

        if (possibleDestCards.size() > 1) {
            final LinearLayout destCard2 = (LinearLayout) destCardsView.findViewById(R.id.dest_card_select_2);
            destCard2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedDestCards.contains(possibleDestCards.get(1))) {
                        destCard2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        selectedDestCards.remove(possibleDestCards.get(1));
                    } else{
                        destCard2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.neon_grey));
                        selectedDestCards.add(possibleDestCards.get(1));
                    }
                    checkSelectedSize(destCardsView);
                }
            });
        }

        if (possibleDestCards.size() > 2) {
            final LinearLayout destCard3 = (LinearLayout) destCardsView.findViewById(R.id.dest_card_select_3);
            destCard3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedDestCards.contains(possibleDestCards.get(2))) {
                        destCard3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        selectedDestCards.remove(possibleDestCards.get(2));
                    } else{
                        destCard3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.neon_grey));
                        selectedDestCards.add(possibleDestCards.get(2));
                    }
                    checkSelectedSize(destCardsView);
                }
            });
        }



    }

    private void checkSelectedSize(View destCardsView){
        Button confirmButton = (Button) destCardsView.findViewById(R.id.confirmButton);
        if (selectedDestCards.size() > 1){
            confirmButton.setEnabled(true);
        } else {
            confirmButton.setEnabled(false);
        }
    }

        /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get the view
        View destCardSelectView = inflater.inflate(R.layout.fragment_dest_card_select, container, false);



        setUpDestCardListeners(destCardSelectView);

        Button confirmButton = (Button) destCardSelectView.findViewById(R.id.confirmButton);
        confirmButton.setEnabled(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return destCardSelectView;
    }

    private void setUpDestCardListeners(final View destCardsView){
        if (possibleDestCards.size() > 0){
            final LinearLayout destCard1 = (LinearLayout) destCardsView.findViewById(R.id.dest_card_select_1);
            destCard1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGame.getMyself().addDestCard(possibleDestCards.get(0));
                    possibleDestCards.remove(0);
                    atLeastOneDestCardSelected = true;
                    checkSelectedSize(destCardsView);
                    destCard1.setVisibility(View.GONE);
                }
            });
        }

        if (possibleDestCards.size() > 1){
            final LinearLayout destCard2 = (LinearLayout) destCardsView.findViewById(R.id.dest_card_select_2);
            destCard2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGame.getMyself().addDestCard(possibleDestCards.get(1));
                    possibleDestCards.remove(0);
                    atLeastOneDestCardSelected = true;
                    checkSelectedSize(destCardsView);
                    destCard2.setVisibility(View.GONE);
                }
            });
        }

        if (possibleDestCards.size() > 2){
            final LinearLayout destCard3 = (LinearLayout) destCardsView.findViewById(R.id.dest_card_select_3);
            destCard3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGame.getMyself().addDestCard(possibleDestCards.get(2));
                    possibleDestCards.remove(0);
                    atLeastOneDestCardSelected = true;
                    checkSelectedSize(destCardsView);
                    destCard3.setVisibility(View.GONE);
                }
            });
        }
    }

    private void checkSelectedSize(View destCardsView){
        Button confirmButton = (Button) destCardsView.findViewById(R.id.confirmButton);
        if (atLeastOneDestCardSelected){
            confirmButton.setEnabled(true);
        }
    }

*/
}
