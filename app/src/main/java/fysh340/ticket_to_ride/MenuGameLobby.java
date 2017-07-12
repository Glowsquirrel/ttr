package fysh340.ticket_to_ride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import interfaces.Observer;
import model.ClientModel;
import serverproxy.ServerProxy;

//TODO implement this class
public class MenuGameLobby extends AppCompatActivity implements Observer {
        private ClientModel clientModel = ClientModel.getMyClientModel();
        private SearchAdapter fAdapter;
        private RecyclerView recyclerView;
        private TextView text;
    private ServerProxy serverProxy = new ServerProxy();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu_game_lobby);
            clientModel.register(this); //registers this controller as an observer to the ClientModel
            recyclerView = (RecyclerView) findViewById(R.id.playerList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            Button start=(Button) findViewById(R.id.StartButton);
            Button leave=(Button) findViewById(R.id.LeaveButton);
            start.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    serverProxy.startGame(clientModel.getMyUsername());

                }

            });
            leave.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {

                    serverProxy.leaveGame(clientModel.getMyUsername(), "");
                }

            });
            updateUI();

        }

        private void updateUI() {
            recyclerView.removeAllViewsInLayout();
            List<String> players = clientModel.getPlayersinGame();
            fAdapter = new SearchAdapter(players);
            recyclerView.setAdapter(fAdapter);
        }

        @Override
        public void update() {
            if(clientModel.hasGame()) {
                updateUI();
            }
            else if(clientModel.isStartedGame())
            {
                Intent intent = new Intent(this, GameStart.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(this, MenuGameList.class);
                startActivity(intent);

            }

        }

        private class FilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public FilterHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.lobby_players_item_view, parent, false));
                text = (TextView) itemView.findViewById(R.id.player);
                text.setOnClickListener(this);

            }

            private String username;

            public void bind(String tobind) {
                username = tobind;

                text.setText(username);


            }

            @Override
            public void onClick(View view) {
            }

        }

        private class SearchAdapter extends RecyclerView.Adapter<MenuGameLobby.FilterHolder> {
            private List<String> itemlist = null;

            public SearchAdapter(List<String> items) {
                itemlist = items;
            }

            @Override
            public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(MenuGameLobby.this);
                return new FilterHolder(layoutInflater, parent);
            }

            @Override
            public void onBindViewHolder(FilterHolder holder, int position) {
                String p = itemlist.get(position);
                holder.bind(p);
                holder.setIsRecyclable(false);
            }

            @Override
            public int getItemCount() {
                return itemlist.size();
            }

        }
    }

