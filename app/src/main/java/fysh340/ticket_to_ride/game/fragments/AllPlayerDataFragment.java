package fysh340.ticket_to_ride.game.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import clientfacade.ClientFacade;
import fysh340.ticket_to_ride.R;
import interfaces.Observer;
import model.AbstractPlayer;
import model.DestCard;
import model.Game;
import model.Player;
import model.PlayerCardsModel;
import model.TrainCard;

import static fysh340.ticket_to_ride.R.color.neon_green;
import static fysh340.ticket_to_ride.R.color.neon_grey;
import static fysh340.ticket_to_ride.R.color.neon_orange;
import static fysh340.ticket_to_ride.R.color.neon_pink;
import static fysh340.ticket_to_ride.R.color.neon_yellow;

public class AllPlayerDataFragment extends Fragment implements Observer {
    private Game mGame = Game.getGameInstance();
    private MyPlayerListAdapter mAdapter = new MyPlayerListAdapter(mGame.getVisiblePlayerInformation());
    private Button routine;

    public AllPlayerDataFragment(){
        mGame.register(this);
    }
    @Override
    public void update() {
        if (mGame.aPlayerHasChanged()) {
            mGame.aPlayerHasChanged(false);
            mAdapter.swapData(mGame.getVisiblePlayerInformation());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MyPlayerListAdapter( mGame.getVisiblePlayerInformation());
        setRetainInstance(true);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mGame.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_player_data, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView mRV = (RecyclerView)  v.findViewById( R.id.player_list);
        mRV.setLayoutManager(layoutManager);
        Button routine=(Button)  v.findViewById( R.id.routine);
        mRV.setAdapter( mAdapter);
        routine.setOnClickListener(new View.OnClickListener() {
            private int i = 0;

            @Override
            public void onClick(View view) {
               /* addHistory(String username, String message, int numTrainCars, int numTrainCardsHeld,
                int numDestCardsHeld, int numRoutesOwned, int score, int claimedRouteNumber)
            }*/
                final ClientFacade cf=new ClientFacade();

                i++;
                switch (i) {

                    case (1):
                        //add points
                        Toast.makeText(getActivity(), "Add points to "+mGame.getVisiblePlayerInformation().get(1).getMyUsername(), Toast.LENGTH_SHORT).show();
                        cf.addHistory(mGame.getVisiblePlayerInformation().get(1).getMyUsername(), "Scored 5 points", 0, 0, 0, 0, 5, 0);
                        break;
                    case (2):
                        //claim a route
                        Toast.makeText(getActivity(), mGame.getVisiblePlayerInformation().get(1).getMyUsername()+" claims route 50", Toast.LENGTH_SHORT).show();
                        cf.addHistory(mGame.getVisiblePlayerInformation().get(1).getMyUsername(), "claimed route", 0, 0, 0, 1, 0, 50);
                        break;
                    case (3):
                        //add destination cards
                        Toast.makeText(getActivity(), mGame.getVisiblePlayerInformation().get(1).getMyUsername()+" claims gets destination cards", Toast.LENGTH_SHORT).show();
                        cf.addHistory(mGame.getVisiblePlayerInformation().get(1).getMyUsername(), "Gest destination cards", 0, 0, 3, 0, 0, 0);
                        break;
                    case (4)://add train cards
                        Toast.makeText(getActivity(), mGame.getVisiblePlayerInformation().get(1).getMyUsername()+" gets train cards", Toast.LENGTH_SHORT).show();
                        cf.addHistory(mGame.getVisiblePlayerInformation().get(1).getMyUsername(), "Gets train cards", 0, 4, 0, 0, 0, 0);
                        break;
                    case(5):
                        //Add/remove train cards for this player
                        Toast.makeText(getActivity(), "Add train cards and a destination card", Toast.LENGTH_SHORT).show();
                        List<TrainCard> card=new ArrayList<TrainCard>();
                        TrainCard tc=TrainCard.getTrainCardTypeByInt(5);
                        card.add(tc);
                        TrainCard tc2=TrainCard.getTrainCardTypeByInt(80);
                        card.add(tc2);
                        mGame.getMyself().addCards(card);
                        mGame.iHaveDifferentDestCards(true);

                        //add destination card
                        mGame.getMyself().addDestCard(DestCard.getDestCardByID(5));
                        mGame.iHaveDifferentTrainCards(true);
                        mGame.notifyObserver();
                        break;
                    case(6):
                        Toast.makeText(getActivity(), "Remove train card and a destination card", Toast.LENGTH_SHORT).show();
                        mGame.getMyself().setNumOfPurpleCards(  mGame.getMyself().getNumOfPurpleCards()-1);
                        mGame.getMyself().removeDestCard(DestCard.getDestCardByID(5));
                        mGame.iHaveDifferentTrainCards(true);
                        mGame.iHaveDifferentDestCards(true);
                        mGame.notifyObserver();

                        //Add/remove player destination cards for this player
                        break;
                    case(7):
                        Toast.makeText(getActivity(), "Train card deck size changed", Toast.LENGTH_SHORT).show();
                        mGame.setTrainCardDeckSize(30);
                        mGame.iHaveDifferentTrainDeckSize(true);
                        mGame.notifyObserver();
                        //Update visible cards and number of invisible cards in train card deck
                        break;
                    case(8):
                        Toast.makeText(getActivity(), "Destination card deck size has changed", Toast.LENGTH_SHORT).show();
                        mGame.setDestinationCardDeckSize(30);
                        mGame.iHaveDifferentDestDeckSize(true);
                        mGame.notifyObserver();
                        //Update number of cards in destination card deck
                        break;
                }


            }
        });
        return v;
    }
/*    public void setButton()
    {

        final ClientFacade cf=new ClientFacade();
        routine.setOnClickListener(new View.OnClickListener() {
            private int i = 0;

            @Override
            public void onClick(View view) {
               /* addHistory(String username, String message, int numTrainCars, int numTrainCardsHeld,
                int numDestCardsHeld, int numRoutesOwned, int score, int claimedRouteNumber)
            }
                i++;
                switch (i) {

                    case (1):
                        //add points
                        Toast.makeText(getActivity(), "Add points to player 2", Toast.LENGTH_SHORT).show();
                        cf.addHistory(mGame.getVisiblePlayerInformation().get(0).getMyUsername(), "Scored 5 points", 0, 0, 0, 0, 5, 0);
                        break;
                    case (2):
                        //claim a route
                        Toast.makeText(getActivity(), "Player 2 claims route 50", Toast.LENGTH_SHORT).show();
                        cf.addHistory(mGame.getVisiblePlayerInformation().get(0).getMyUsername(), "claimed route", 0, 0, 0, 1, 0, 50);
                        break;
                    case (3):
                        //add destination cards
                        Toast.makeText(getActivity(), "Player 2 claims gets destination cards", Toast.LENGTH_SHORT).show();
                        cf.addHistory(mGame.getVisiblePlayerInformation().get(0).getMyUsername(), "Gest destination cards", 0, 4, 0, 0, 0, 0);
                        break;
                    case (4)://add train cards
                        Toast.makeText(getActivity(), "Player 2 claims gets train cards", Toast.LENGTH_SHORT).show();
                        cf.addHistory(mGame.getVisiblePlayerInformation().get(0).getMyUsername(), "Gets train cards", 0, 4, 3, 0, 0, 0);
                        break;
                }


            }
        });
    }*/

    private class MyPlayerListAdapter extends RecyclerView.Adapter<AllPlayerDataFragment.MyPlayerListAdapter.ViewHolder> {
        private List<AbstractPlayer> allPlayers = new ArrayList<>();

        private MyPlayerListAdapter(List<AbstractPlayer> newList){
            allPlayers = newList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemUsername;
            TextView itemRoutes;
            TextView itemCards;
            TextView itemTrains;
            TextView itemScore;
            View myView;
            TextView itemDCards;

            ViewHolder(View view){
                super(view);
                itemUsername = (TextView) view.findViewById(R.id.username);
                itemRoutes = (TextView) view.findViewById(R.id.routesNum);
                itemCards = (TextView) view.findViewById(R.id.cardsNum);
                itemTrains = (TextView) view.findViewById(R.id.trainsNum);
                itemScore = (TextView) view.findViewById(R.id.scoreNum);
                itemDCards = (TextView) view.findViewById(R.id.dcardsNum);
                myView = view;
            }
        }

        void swapData(List<AbstractPlayer> newGameList){
            allPlayers = newGameList;
            notifyDataSetChanged();
        }

        @Override
        public AllPlayerDataFragment.MyPlayerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_player_item_view, parent, false);
            return new AllPlayerDataFragment.MyPlayerListAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AllPlayerDataFragment.MyPlayerListAdapter.ViewHolder holder, final int position) {
            AbstractPlayer myPlayer = allPlayers.get(position);

            String playerName = myPlayer.getMyUsername();
            int myTrains = myPlayer.getNumTrains();
            int myCards = myPlayer.getNumCards();
            int myRoutes = myPlayer.getNumRoutes();
            int myScore = myPlayer.getScore();
            int color=myPlayer.getColor();
            int colors=getResources().getColor(color);
            int myDCards=myPlayer.getDestCardNum();

            holder.itemUsername.setText(playerName);
            holder.itemTrains.setText(String.valueOf(myTrains));
            holder.itemCards.setText(String.valueOf(myCards));
            holder.itemRoutes.setText(String.valueOf(myRoutes));
            holder.itemScore.setText(String.valueOf(myScore));
            holder.itemUsername.setBackgroundColor(colors);
            holder.itemDCards.setText(String.valueOf(myDCards));
        }

        @Override
        public int getItemCount() {
            return allPlayers.size();
        }

    }
}
