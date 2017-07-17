package fysh340.ticket_to_ride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import model.ClientModel;
import serverproxy.ServerProxy;

public class GameStart extends AppCompatActivity {
    ClientModel clientModel = ClientModel.getMyClientModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
