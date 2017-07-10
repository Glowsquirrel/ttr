package fysh340.ticket_to_ride;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import interfaces.Observer;
import model.ClientModel;
import model.GameToStart;

//TODO: implement this class

public class MenuGameList extends AppCompatActivity implements Observer{
    private ClientModel clientModel = ClientModel.getMyClientModel();
    private SearchAdapter fAdapter;
    private RecyclerView FilterRecyclerView;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_game_list);
        clientModel.register(this); //registers this controller as an observer to the ClientModel
        FilterRecyclerView = (RecyclerView)  findViewById( R.id.recyclerView);
        FilterRecyclerView.setLayoutManager( new LinearLayoutManager( this));
        updateUI();

    }
    private void updateUI()
    {
        List<GameToStart>games=clientModel.getGamestoStart();
        fAdapter = new SearchAdapter( games);
        FilterRecyclerView.setAdapter( fAdapter);
    }

    @Override
    public void update() {

    }
    private class FilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public FilterHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.game_list_item_view, parent, false));
            text = (TextView) itemView.findViewById( R.id.toJoin);
            text.setOnClickListener(this);

        }
        private GameToStart mfilter;
        public void bind( GameToStart filter)
        {
            mfilter = filter;

                text.setText(mfilter.getName());


        }
        @Override
        public void onClick( View view)
        {

        }

    }

    private class SearchAdapter extends RecyclerView.Adapter <MenuGameList.FilterHolder>
    {
        private List<GameToStart> itemlist=null;
        public SearchAdapter(List <GameToStart> items) { itemlist = items; }
        @Override
        public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType)
        { LayoutInflater layoutInflater = LayoutInflater.from( MenuGameList.this);
            return new FilterHolder( layoutInflater, parent); }
        @Override
        public void onBindViewHolder(FilterHolder holder, int position)
        {
            GameToStart filter=itemlist.get(position);
            holder.bind(filter);
            holder.setIsRecyclable(false);
        }
        @Override public int getItemCount()
        { return itemlist.size(); }

    }

}
