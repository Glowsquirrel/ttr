package fysh340.ticket_to_ride.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fysh340.ticket_to_ride.R;
import interfaces.Observer;
import model.ClientModel;
import model.Game;
import model.Player;

public class PlayerData extends Fragment implements Observer {
    private adapter mAdapter;
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
        mAdapter = new adapter( mGame.getPlayerListToDisplay());
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
            mCards.setText(mItem.getCardNum());
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

}
