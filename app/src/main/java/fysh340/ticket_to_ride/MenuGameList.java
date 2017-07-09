package fysh340.ticket_to_ride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Interfaces.Observer;
import Model.ClientModel;

//TODO: implement this class

public class MenuGameList extends AppCompatActivity implements Observer{
    private ClientModel clientModel = ClientModel.getMyClientModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_game_list);
        clientModel.register(this); //registers this controller as an observer to the ClientModel
    }

    @Override
    public void update() {

    }
}
