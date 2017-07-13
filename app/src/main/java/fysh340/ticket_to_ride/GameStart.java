package fysh340.ticket_to_ride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameStart extends AppCompatActivity {

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
