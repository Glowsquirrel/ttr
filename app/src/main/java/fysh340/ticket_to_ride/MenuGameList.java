package fysh340.ticket_to_ride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import clientcommunicator.PollerTask;
import interfaces.Observer;
import model.ClientModel;
import model.UnstartedGames;
import serverproxy.ServerProxy;

//TODO: implement this class

public class MenuGameList extends AppCompatActivity implements Observer, AdapterView.OnItemSelectedListener{
    private ClientModel clientModel = ClientModel.getMyClientModel();
    private SearchAdapter fAdapter;
    private RecyclerView recyclerView;
    private TextView text;
    private ServerProxy serverProxy = new ServerProxy();
    private String gameName;
    private PollerTask pt;

    public Button getCreateGame() {
        return createGame;
    }

    public void setCreateGame(Button createGame) {
        this.createGame = createGame;
    }

    Button createGame;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    private int playerNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_game_list);
        clientModel.register(this); //registers this controller as an observer to the ClientModel
        recyclerView = (RecyclerView)  findViewById( R.id.recyclerView);
        recyclerView.setLayoutManager( new LinearLayoutManager( this));
        updateUI();
        EditText gameName=(EditText) findViewById(R.id.gamename);
        Spinner spinner = (Spinner) findViewById(R.id.playernum_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.player_num_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        createGame=(Button) findViewById(R.id.createGameButton);
        createGame.setEnabled(false);
        createGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                serverProxy.createGame(clientModel.getMyUsername(),getGameName(), getPlayerNum());

            }

        });
        gameName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                setGameName(s.toString());
                getCreateGame().setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        pt= new PollerTask();
        pt.execute();

    }
    @Override
    public void onStop()
    {
        super.onStop();
        pt.endTimer();


    }
    private void updateUI()
    {
        recyclerView.removeAllViewsInLayout();
        List<UnstartedGames>games=clientModel.getGamestoStart();
        fAdapter = new SearchAdapter( games);
        recyclerView.setAdapter( fAdapter);
    }

    @Override
    public void update() {
        if(clientModel.hasGame()) {
            Intent intent = new Intent(this, MenuGameLobby.class);
            startActivity(intent);
        }
        else
        {
            updateUI();
        }

    }
    private class FilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public FilterHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.game_list_item_view, parent, false));
            text = (TextView) itemView.findViewById( R.id.toJoin);
            text.setOnClickListener(this);

        }
        private UnstartedGames game;
        public void bind( UnstartedGames tobind)
        {
            game = tobind;

                text.setText(game.getName());


        }
        @Override
        public void onClick( View view)
        {
            serverProxy.joinGame(clientModel.getMyUsername(),game.getName());
        }

    }

    private class SearchAdapter extends RecyclerView.Adapter <MenuGameList.FilterHolder>
    {
        private List<UnstartedGames> itemlist=null;
        public SearchAdapter(List <UnstartedGames> items) { itemlist = items; }
        @Override
        public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType)
        { LayoutInflater layoutInflater = LayoutInflater.from( MenuGameList.this);
            return new FilterHolder( layoutInflater, parent); }
        @Override
        public void onBindViewHolder(FilterHolder holder, int position)
        {
            UnstartedGames filter=itemlist.get(position);
            holder.bind(filter);
            holder.setIsRecyclable(false);
        }
        @Override public int getItemCount()
        { return itemlist.size(); }

    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (pos) {
            case 0:
                setPlayerNum(2);
                break;
            case 1:
                setPlayerNum(3);
                break;
            case 2:
                setPlayerNum(4);
                break;
            case 3:
                setPlayerNum(5);
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> parent)
    {
    }

}
