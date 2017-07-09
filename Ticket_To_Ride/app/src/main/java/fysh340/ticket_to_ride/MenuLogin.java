package fysh340.ticket_to_ride;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Interfaces.Observer;
import Model.ClientModel;
import ServerProxy.ServerProxy;

public class MenuLogin extends AppCompatActivity implements Observer {
    private ClientModel clientModel = ClientModel.getMyClientModel();
    private ServerProxy serverProxy = new ServerProxy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        clientModel.register(this);

    }

    public void login(View view){
        EditText ipEdit = (EditText)findViewById(R.id.ip_address);
        EditText usernameEdit = (EditText)findViewById(R.id.username);
        EditText passwordEdit = (EditText)findViewById(R.id.password);

        clientModel.setIp(ipEdit.getText().toString());
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        serverProxy.login(username, password);
    }

    public void register(View view){
        //TODO implement register
    }

    @Override
    public void update() {
        if (clientModel.hasUser()){
            //proceed to game list screen
            Intent intent = new Intent(this, MenuGameList.class);
            startActivity(intent);
        }
        else{ //get stored error message and display it
            Toast.makeText(getApplicationContext(), clientModel.getErrorMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
