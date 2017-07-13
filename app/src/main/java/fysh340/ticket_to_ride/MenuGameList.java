package fysh340.ticket_to_ride;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import clientcommunicator.PollerTask;
import model.UnstartedGame;
import interfaces.Observer;
import model.ClientModel;
import serverproxy.ServerProxy;

public class MenuGameList extends AppCompatActivity implements Observer{

    private ClientModel clientModel = ClientModel.getMyClientModel();
    private ServerProxy serverProxy = new ServerProxy();
    private PollerTask poller;
    private MyGameListAdapter mAdapter;
    private int pollCounter = 0; //counts # of polls. for testing
    private int playerNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_game_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.game_list_title));
        setSupportActionBar(toolbar);

        setupUI(findViewById(android.R.id.content));

        clientModel.register(this); //registers this controller as an observer to the ClientModel

        RecyclerView mRecyclerView = (RecyclerView)  findViewById( R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(llm);

        //text watcher for create game button
        EditText gameName = (EditText) findViewById(R.id.gamename);
        gameName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Button createGameButton = (Button)findViewById(R.id.createGameButton);
                String inputGameName = s.toString().trim();
                if (inputGameName.length() > 0){ //a game name must be at least 1 char long
                    createGameButton.setEnabled(true);
                }
                else
                    createGameButton.setEnabled(false);
            }
        });

        mAdapter = new MyGameListAdapter(); //create the search adapter once, update its data later
        mRecyclerView.setAdapter(mAdapter);

        //spinner
        Spinner numPlayerSpinner = (Spinner)findViewById(R.id.playernum_spinner);
        ArrayAdapter<CharSequence> numPlayerAdapter = ArrayAdapter.createFromResource(
                this, R.array.player_num_array, R.layout.support_simple_spinner_dropdown_item);
        numPlayerSpinner.setAdapter(numPlayerAdapter);
        playerNum = Integer.parseInt(numPlayerSpinner.getSelectedItem().toString()); //spinner is hard coded into xml, so is safe
        numPlayerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                playerNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });

        mAdapter = new MyGameListAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        poller = new PollerTask(clientModel.getMyUsername(), 10000); //poll every 10s. currently slow for debug purposes
        poller.pollGameList();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        poller.stopPoller();
        clientModel.unregister(this); //registers this controller as an observer to the ClientModel
    }

    @Override
    public void update() {
        String pollerUpdateCount = "Poll # " + String.valueOf(++pollCounter);
        Toast.makeText(getApplicationContext(), pollerUpdateCount,Toast.LENGTH_SHORT).show(); //Toast poller count

        if(clientModel.hasGame()) { //If the model has a game, switch to Lobby view.
            clientModel.unregister(this);
            Intent intent = new Intent(this, MenuGameLobby.class);
            startActivity(intent);
        }
        else if (clientModel.hasMessage()){ //If the model has a message, Toast the message
            Toast.makeText(getApplicationContext(), clientModel.getErrorMessage(),Toast.LENGTH_LONG).show();
            clientModel.receivedMessage();
        }
        else { //If nothing else, refresh the data inside the adapter.
            mAdapter.swapData(clientModel.getGamesToStart());
        }
    }

    public void createGame(View view){
        EditText gameNameEdit = (EditText)findViewById(R.id.gamename);
        String gameName = gameNameEdit.getText().toString();
        serverProxy.createGame(clientModel.getMyUsername(), gameName, playerNum);
    }

    public void showSpinner(View view){ //allows the layout consisting of text and spinner to open up the spinner item selection dropdown
        findViewById(R.id.playernum_spinner).performClick();
    }

    private class MyGameListAdapter extends RecyclerView.Adapter<MyGameListAdapter.ViewHolder> {
        private List<UnstartedGame> allGames = new ArrayList<>();


        class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemGameName;
            TextView itemPlayers;
            View myView;

            ViewHolder(View view){
                super(view);
                itemGameName = (TextView) view.findViewById(R.id.item_game_name);
                itemPlayers = (TextView) view.findViewById(R.id.item_players);
                myView = view;
            }
        }

        void swapData(List<UnstartedGame> newGameList){
            allGames = newGameList;
            notifyDataSetChanged();
        }

        @Override
        public MyGameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item_view, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            UnstartedGame myGame = allGames.get(position);

            final String gameName;
            gameName = myGame.getName();
            int currentPlayers = myGame.getPlayersIn();
            int neededPlayers = myGame.getPlayersNeeded();
            String playerString = currentPlayers + "/" + neededPlayers + " " + getString(R.string.players);

            holder.itemGameName.setText(gameName);
            holder.itemPlayers.setText(playerString);
           // holder.myView.setBackgroundColor(getRainbowColor(position, getItemCount()));

            holder.myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.myView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_item));
                    serverProxy.joinGame(clientModel.getMyUsername(), gameName);
                }
            });
        }

        @Override
        public int getItemCount() {
            return allGames.size();
        }

        public int getRainbowColor(int position, int size){
            if (position == 0)
                return Color.parseColor("#50000000");
            int RGBMax = 255;
            int colorValue = (255 * position) % size;
            DecimalFormat twoDigits = new DecimalFormat("00");
            int hexColor = Integer.valueOf(String.valueOf(colorValue), 16);
            String myColor = twoDigits.format(hexColor);
            String ARGBColor = "#dd" + myColor + myColor + myColor;
            return Color.parseColor(ARGBColor);
        }

    }

    public void setupUI(View view) { //Modifies onClick of view to check type of widget clicked.

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MenuGameList.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) { //Hides the keyboard.

        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
