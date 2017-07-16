package fysh340.ticket_to_ride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import websocket.MyWebSocket;


public class SocketTest extends AppCompatActivity {

    //private Button start;
    private TextView output;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);
        output = (TextView)findViewById(R.id.output);

    }

    public void start(View view){
        MyWebSocket webSocket = MyWebSocket.getMyWebSocket();
        OkHttpClient client = new OkHttpClient();

        String serverEndpoint = "ws://10.14.178.129:8080/";
        Request request = new Request.Builder().url(serverEndpoint).build();
        WebSocket ws = client.newWebSocket(request, webSocket);
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
