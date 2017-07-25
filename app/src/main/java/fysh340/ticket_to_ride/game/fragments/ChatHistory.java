package fysh340.ticket_to_ride.game.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import fysh340.ticket_to_ride.R;
import interfaces.Observer;
import model.ChatHistoryModel;
import model.ClientModel;
import serverproxy.ServerProxy;

public class ChatHistory extends Fragment implements Observer {

    private adapter mAdapter;
    private RecyclerView mRV;
    private ChatHistoryModel chatModel=ChatHistoryModel.myChat;
    private TextView action;
    private Button mToDisplay;
    private Button mSend;
    private EditText mChatEdit;
    private ServerProxy mServerProxy = new ServerProxy();
    private String mChatMessage;
    private ClientModel mClientModel = ClientModel.getMyClientModel();

    @Override
    public void update()
    {
        updateRV();
    }
    private void updateRV() {
        mRV.removeAllViewsInLayout();
        mRV.setLayoutManager( new LinearLayoutManager( getActivity()));
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
        mSend=(Button) v.findViewById((R.id.send));
        mChatEdit=(EditText) v.findViewById((R.id.editor));
       setButton();
        updateRV();
        chatModel.register(this);
        return v;
    }
    private void setButton() {
        mToDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatModel.switchChat();
                updateRV();
            }
        });
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mServerProxy.sendChatMessage(mClientModel.getMyUsername(),mClientModel.getMyGameName(),mChatMessage);
                mChatEdit.setText("");
                mSend.setEnabled(false);
            }
        });
        mChatEdit.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mChatMessage=s.toString();
                mSend.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mSend.setEnabled(false);

    }
    private class itemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public itemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.chat_item_view, parent, false));
            action = (TextView) itemView.findViewById( R.id.string_view);
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
        private List<String> itemlist;
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
