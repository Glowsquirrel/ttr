package fysh340.ticket_to_ride;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import interfaces.Observer;
import model.ClientModel;
import serverproxy.ServerProxy;

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

        //load the previous login attempt data
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.default_string), Context.MODE_PRIVATE);
        String ipString = sharedPref.getString("ip", "");
        String usernameString = sharedPref.getString("username", "");
        String passwordString = sharedPref.getString("password", "");

        EditText ip = (EditText)findViewById(R.id.ip_address);
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);

        ip.setText(ipString);
        username.setText(usernameString);
        password.setText(passwordString);
    }

    @Override
    protected void onStart(){
        super.onStart();
        clientModel.register(this); //adds this controller to the list of observers
    }

    public void login(View view){
        EditText ipEdit = (EditText)findViewById(R.id.ip_address);
        EditText usernameEdit = (EditText)findViewById(R.id.username);
        EditText passwordEdit = (EditText)findViewById(R.id.password);

        String ip = ipEdit.getText().toString();
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        //begin save current input to load later
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.default_string), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("ip", ip);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
        //end save current input to load later

        clientModel.setIp(ip);
        serverProxy.login(username, password);
    }

    public void register(View view){
        EditText ipEdit = (EditText)findViewById(R.id.ip_address);
        EditText usernameEdit = (EditText)findViewById(R.id.username);
        EditText passwordEdit = (EditText)findViewById(R.id.password);

        String ip = ipEdit.getText().toString();
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        clientModel.setIp(ip);
        serverProxy.register(username, password);
    }

    @Override
    public void update() {
        if (clientModel.hasUser()){
            clientModel.unregister(this); //removes this controller from list of observers
            Intent intent = new Intent(this, MenuGameList.class);
            startActivity(intent); //proceed to game list screen
        }
        else{ //get stored error message and display it
            Toast.makeText(getApplicationContext(), clientModel.getErrorMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
