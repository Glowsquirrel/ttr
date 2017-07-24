package fysh340.ticket_to_ride.game.fragments;

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
import model.DestCard;
import model.Game;


public class PlayerCardsFragment extends Fragment implements Observer{
    private adapter mAdapter;
    private RecyclerView mRV;
    private ClientModel mClientModel = ClientModel.getMyClientModel();
    private Game mGame;
    private TextView mDestination;

    @Override
    public void update() {

    }
    private void updateRV() {
        mRV.setLayoutManager( new LinearLayoutManager( getActivity()));
        mAdapter = new adapter( mClientModel.getMyDestinationCards());
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
        View v=inflater.inflate(R.layout.fragment_player_cards, container, false);
         mRV= (RecyclerView)  v.findViewById( R.id.destinationCardList);
        return v;
    }
    private class itemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public itemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.destination_card_item_view, parent, false));
            mDestination = (TextView) itemView.findViewById( R.id.destinations);
            // itemView.findViewById(R.id.sequence).setOnClickListener(this);
        }
        private DestCard mItem;
        public void bind( DestCard item)
        {
            mItem = item;
            mItem.toString();
            mDestination.setText(mItem.toString());

        }
        @Override
        public void onClick( View view)
        {

        }
    }

    private class adapter extends RecyclerView.Adapter <itemHolder>
    {
        private List<DestCard> itemlist=null;
        public adapter(List <DestCard> items) { itemlist = items; }
        @Override
        public itemHolder onCreateViewHolder(ViewGroup parent, int viewType)
        { LayoutInflater layoutInflater = LayoutInflater.from( getActivity());
            return new itemHolder( layoutInflater, parent); }
        @Override
        public void onBindViewHolder(itemHolder holder, int position)
        {
            DestCard item=itemlist.get(position);
            holder.bind(item);
            holder.setIsRecyclable(false);
        }
        @Override public int getItemCount()
        { return itemlist.size(); }

    }



}
