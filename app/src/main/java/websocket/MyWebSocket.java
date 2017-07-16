package websocket;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.concurrent.TimeUnit;

import clientcommunicator.CommandResultSerializer;
import clientfacade.ClientFacade;
import commandresults.CommandResult;
import model.ClientModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static fysh340.ticket_to_ride.R.id.output;

public class MyWebSocket extends WebSocketListener {
    private static final MyWebSocket myWebSocket = new MyWebSocket();
    private MyWebSocket(){}
    public static MyWebSocket getMyWebSocket() {
        return myWebSocket;
    }
    private WebSocket ws;
    private String ip;
    private String port;
    private int RECONNECT_CODE = 1;


    private ClientFacade clientFacade = new ClientFacade();
    private boolean listening = false;


    OkHttpClient client;

    public void sendJson(String myString){
        ws.send(myString);
    }
    public boolean initialize(String ip, String port){
        if (!listening){
            if (ip.trim().equals(""))
                return false;
            this.ip = ip;
            this.port = port;
            String serverEndpoint = "ws://" + ip + ":" + port + "/";
            Request request = new Request.Builder().url(serverEndpoint).build();
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .build();
            ws = client.newWebSocket(request, this);
            listening = true;
        }
        return true;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response respone){
        //webSocket.send("Its me, Mario!");
        //webSocket.close(NORMAL_CLOSURE_STATUS, "GOODBYE");
    }

    @Override
    public void onMessage(WebSocket webSocket, String serverMessage){
        //should parse and call correct clientfacade function
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CommandResult.class, new CommandResultSerializer());
        Gson customGson = gsonBuilder.create();
        CommandResult commandResult = customGson.fromJson(serverMessage, CommandResult.class);

        switch (commandResult.getType()){
            case "register":
                clientFacade.registerUser(commandResult);
                break;
            case "login":
                clientFacade.loginUser(commandResult);
                break;
            case "pollgames":
                clientFacade.updateGameList(commandResult);
                break;
            case "creategame":
                clientFacade.createGame(commandResult);
                break;
            case "startgame":
                clientFacade.startGame(commandResult);
                break;
            case "leavegame":
                clientFacade.leaveGame(commandResult);
                break;
            case "joingame":
                clientFacade.joinGame(commandResult);
                break;
        }

    }



    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        //??
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason){
        //output("Closing : " + code + " / " + reason);
        listening = false;
        try {
            Thread.sleep(5000);
            initialize(this.ip, this.port);
        }catch (InterruptedException ex){
            //Thread.currentThread().interrupt();
        }
        //the server has disconnected you or you purposely disconnected yourself
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response){
        if (t.getClass() == JsonSyntaxException.class){
            onClosing(webSocket, RECONNECT_CODE, t.getMessage());
        }
        else
            listening = false;
        //could not connect to server
    }

    private void output(final String txt){

        final Handler uiHandler = new Handler(Looper.getMainLooper()); //gets the UI thread
        Runnable runnable = new Runnable() { //
            @Override
            public void run() {
            }
        };
        uiHandler.post(runnable); //do the run() method in previously declared runnable on UI thread
    }

    public void quit(){
        client.dispatcher().executorService().shutdown();
    }

}