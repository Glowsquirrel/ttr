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

import java.util.ArrayList;
import java.util.List;

import fysh340.ticket_to_ride.R;
import fysh340.ticket_to_ride.game.GameView;
import interfaces.Observer;
import model.DestCard;
import model.Game;

public class PlayerDestCardsFragment extends Fragment implements Observer{
    private MyPlayerListAdapter mAdapter;
    private Game mGame = Game.getGameInstance();
    private RecyclerView mRecyclerView;

    public PlayerDestCardsFragment(){

    }

    @Override
    public void update() {
        if (mGame.aPlayerHasChanged()) {
            mGame.aPlayerHasChanged(false);
            mAdapter.swapData(mGame.getMyDestCards());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MyPlayerListAdapter( mGame.getMyDestCards());
        mGame.register(this);
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
        View v=inflater.inflate(R.layout.fragment_player_dest_cards, container, false);
        mRecyclerView = (RecyclerView)  v.findViewById( R.id.dest_card_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter( mAdapter);

        Button playerCardSwitchButton = (Button) v.findViewById(R.id.player_switch_cards_button);
        playerCardSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameView)getActivity()).switchPlayerCards();
            }
        });

        return v;
    }

    private class MyPlayerListAdapter extends RecyclerView.Adapter<MyPlayerListAdapter.ViewHolder> {
        private List<DestCard> allPlayers = new ArrayList<>();

        private MyPlayerListAdapter(List<DestCard> newList){
            allPlayers = newList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemCity1;
            TextView itemCity2;
            TextView itemScore;
            View myView;

            ViewHolder(View view){
                super(view);
                itemCity1 = (TextView) view.findViewById(R.id.dest_card_city_name_1);
                itemCity2 = (TextView) view.findViewById(R.id.dest_card_city_name_2);
                itemScore = (TextView) view.findViewById(R.id.dest_card_score);
                myView = view;
            }
        }

        void swapData(List<DestCard> newGameList){
            allPlayers = newGameList;
            notifyDataSetChanged();
        }

        @Override
        public MyPlayerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dest_card, parent, false);
            return new MyPlayerListAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyPlayerListAdapter.ViewHolder holder, final int position) {
            DestCard myDestCard = allPlayers.get(position);

            String city1 = myDestCard.getStartCity().getPrettyName();
            String city2 = myDestCard.getEndCity().getPrettyName();
            int score = myDestCard.getPointValue();

            holder.itemCity1.setText(city1);
            holder.itemCity2.setText(city2);
            holder.itemScore.setText(String.valueOf(score));

        }

        @Override
        public int getItemCount() {
            return allPlayers.size();
        }
    }
}