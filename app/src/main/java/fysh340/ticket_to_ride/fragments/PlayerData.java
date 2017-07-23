package fysh340.ticket_to_ride.fragments;





import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fysh340.ticket_to_ride.MenuGameList;
import fysh340.ticket_to_ride.R;
import interfaces.Observer;
import model.ClientModel;
import model.Game;
import model.Player;
import model.UnstartedGame;

public class PlayerData extends Fragment implements Observer {
    private MyPlayerListAdapter mAdapter;
    private RecyclerView mRV;
    private ClientModel mClientModel = ClientModel.getMyClientModel();
    private Game mGame=Game.myGame;


    @Override
    public void update()
    {
        updateRV();

    }
    private void updateRV() {
        mRV.removeAllViewsInLayout();
        mAdapter = new MyPlayerListAdapter( mGame.getPlayerListToDisplay());
        mRV.setAdapter( mAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_player_data, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mRV = (RecyclerView)  v.findViewById( R.id.player_list);
        mRV.setLayoutManager(layoutManager);
        updateRV();
        mGame.register(this);
        return v;
    }

    private class MyPlayerListAdapter extends RecyclerView.Adapter<PlayerData.MyPlayerListAdapter.ViewHolder> {
        private List<Player> allPlayers = new ArrayList<>();

        private MyPlayerListAdapter(List<Player> newList){
            allPlayers = newList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemUsername;
            TextView itemRoutes;
            TextView itemCards;
            TextView itemTrains;
            TextView itemPoints;
            View myView;

            ViewHolder(View view){
                super(view);
                itemUsername = (TextView) view.findViewById(R.id.username);
                itemRoutes = (TextView) view.findViewById(R.id.routesNum);
                itemCards = (TextView) view.findViewById(R.id.cardsNum);
                itemTrains = (TextView) view.findViewById(R.id.trainsNum);
                itemPoints = (TextView) view.findViewById(R.id.pointsNum);
                myView = view;
            }
        }

        void swapData(List<Player> newGameList){
            allPlayers = newGameList;
            notifyDataSetChanged();
        }

        @Override
        public PlayerData.MyPlayerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_player_item_view, parent, false);
            return new PlayerData.MyPlayerListAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final PlayerData.MyPlayerListAdapter.ViewHolder holder, final int position) {
            Player myPlayer = allPlayers.get(position);

            String playerName = myPlayer.getUserName();
            int myRoutes = myPlayer.getRoutes();
            int myCards = myPlayer.getCards();
            int myTrains = myPlayer.getTrains();
            int myPoints = myPlayer.getPoints();

            holder.itemUsername.setText(playerName);
            holder.itemRoutes.setText(String.valueOf(myRoutes));
            holder.itemCards.setText(String.valueOf(myCards));
            holder.itemTrains.setText(String.valueOf(myTrains));
            holder.itemPoints.setText(String.valueOf(myPoints));

        }

        @Override
        public int getItemCount() {
            return allPlayers.size();
        }

    }

    /*
    private class itemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mUsername;
        private TextView mRoute;
        private TextView mCards;
        private TextView mTrains;
        private TextView mPoints;

        public itemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.game_player_item_view, parent, false));
            mUsername = (TextView) itemView.findViewById( R.id.username);
            mRoute = (TextView) itemView.findViewById( R.id.routesNum);
            mCards = (TextView) itemView.findViewById( R.id.cardsNum);
            mTrains = (TextView) itemView.findViewById( R.id.trainsNum);
            mPoints = (TextView) itemView.findViewById( R.id.pointsNum);
            // itemView.findViewById(R.id.sequence).setOnClickListener(this);
        }
        private Player mItem;
        public void bind( Player item)
        {
            mItem = item;
            mUsername.setText(mItem.getUserName());
            mRoute.setText(mItem.getRoutes());
            mCards.setText(mItem.getCards());
            mTrains.setText(mItem.getTrains());
            mPoints.setText(mItem.getRoutes());


        }
        @Override
        public void onClick( View view)
        {

        }

    }
    private class adapter extends RecyclerView.Adapter <itemHolder>
    {
        private List<Player> itemlist=null;
        public adapter(List <Player> items) { itemlist = items; }
        @Override
        public itemHolder onCreateViewHolder(ViewGroup parent, int viewType)
        { LayoutInflater layoutInflater = LayoutInflater.from( getActivity());
            return new itemHolder( layoutInflater, parent); }
        @Override
        public void onBindViewHolder(itemHolder holder, int position)
        {
            Player item=itemlist.get(position);
            holder.bind(item);
            holder.setIsRecyclable(false);
        }
        @Override public int getItemCount()
        { return itemlist.size(); }

    }
    */
}
