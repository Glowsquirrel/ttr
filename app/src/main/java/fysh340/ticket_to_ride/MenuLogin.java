package fysh340.ticket_to_ride;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import commands.LoginCommandData;
import commands.RegisterCommandData;
import interfaces.Observer;
import model.ClientModel;
import serverproxy.ServerProxy;

public class MenuLogin extends AppCompatActivity implements Observer {
    private ClientModel clientModel = ClientModel.getMyClientModel();
    private ServerProxy serverProxy = new ServerProxy();
    private String username;
    private String password;
    private String ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        //begin load previous input
        //SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.default_string), Context.MODE_PRIVATE);
        //String ipString = sharedPref.getString("ip", "");
        //String usernameString = sharedPref.getString("username", "");
        //String passwordString = sharedPref.getString("password", "");

        //EditText ip = (EditText)findViewById(R.id.ip_address);
        //EditText username = (EditText)findViewById(R.id.username);
        //EditText password = (EditText)findViewById(R.id.password);

        //ip.setText(ipString);
        //username.setText(usernameString);
        //password.setText(passwordString);
        //end load previous input

        Button register=(Button)findViewById(R.id.register);
        Button login=(Button) findViewById(R.id.login);


    }

    @Override
    protected void onStart(){
        super.onStart();
        clientModel.register(this); //adds this controller to the list of observers
    }
    @Override
    protected void onStop(){
        super.onStop();
        clientModel.unregister(this); //removes this controller from the list of observers
    }

    public void login(View view){
        EditText ipEdit = (EditText)findViewById(R.id.ip_address);
        EditText usernameEdit = (EditText)findViewById(R.id.username);
        EditText passwordEdit = (EditText)findViewById(R.id.password);

        String ip = ipEdit.getText().toString();
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        clientModel.setIp(ipEdit.getText().toString());

        //begin save current input to load later
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.default_string), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("ip", ip);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
        //end save current input to load later

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
