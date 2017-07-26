package fysh340.ticket_to_ride.game.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fysh340.ticket_to_ride.R;
import interfaces.Observer;
import model.AbstractPlayer;
import model.Game;

import static fysh340.ticket_to_ride.R.color.neon_green;
import static fysh340.ticket_to_ride.R.color.neon_grey;
import static fysh340.ticket_to_ride.R.color.neon_orange;
import static fysh340.ticket_to_ride.R.color.neon_pink;
import static fysh340.ticket_to_ride.R.color.neon_yellow;

public class AllPlayerDataFragment extends Fragment implements Observer {
    private Game mGame = Game.getGameInstance();
    private MyPlayerListAdapter mAdapter = new MyPlayerListAdapter(mGame.getVisiblePlayerInformation());

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

        mRV.setAdapter( mAdapter);
        return v;
    }

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
