package fysh340.ticket_to_ride;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import interfaces.Observer;
import model.ChatHistoryModel;
import model.ClientModel;
import model.DestCard;
import model.Game;

public class ChatHistory extends Fragment implements Observer {

    private adapter mAdapter;
    private RecyclerView mRV;
    private ChatHistoryModel chatModel=ChatHistoryModel.model;
    private TextView action;
    private Button mToDisplay;

    @Override
    public void update()
    {

    }
    private void updateRV() {

        if(chatModel.isChat()) {
            mToDisplay.setText("History");
            mAdapter = new adapter(chatModel.getChatStrings());
            mRV.setAdapter(mAdapter);
        }
        else
        {
            mToDisplay.setText("Chat");
            mAdapter = new adapter(chatModel.getHistoryStrings());
            mRV.setAdapter(mAdapter);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_chat_history, container, false);
        mRV= (RecyclerView)  v.findViewById( R.id.rv_display);
        mToDisplay=(Button)  v.findViewById((R.id.todisplay));
       setButton();
        updateRV();
        return v;
    }
    private void setButton() {
        mToDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRV();
            }
        });
    }
    private class itemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public itemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.chat_item_view, parent, false));
            action = (TextView) itemView.findViewById( R.id.destinations);
            // itemView.findViewById(R.id.sequence).setOnClickListener(this);
        }
        private String mItem;
        public void bind( String item)
        {
            mItem = item;
            mItem.toString();
            action.setText(mItem.toString());

        }
        @Override
        public void onClick( View view)
        {

        }

    }
    private class adapter extends RecyclerView.Adapter <itemHolder>
    {
        private List<String> itemlist=null;
        public adapter(List <String> items) { itemlist = items; }
        @Override
        public itemHolder onCreateViewHolder(ViewGroup parent, int viewType)
        { LayoutInflater layoutInflater = LayoutInflater.from( getActivity());
            return new itemHolder( layoutInflater, parent); }
        @Override
        public void onBindViewHolder(itemHolder holder, int position)
        {
            String item=itemlist.get(position);
            holder.bind(item);
            holder.setIsRecyclable(false);
        }
        @Override public int getItemCount()
        { return itemlist.size(); }

    }





}
