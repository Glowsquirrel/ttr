package websocket;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.concurrent.TimeUnit;

import clientcommunicator.CommandResultSerializer;
import clientfacade.ClientFacade;
import commandresults.CommandResult;
import commandresults.PollGamesResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import serverproxy.ServerProxy;

public class ClientWebSocket extends WebSocketListener {
    private static final ClientWebSocket clientWebSocket = new ClientWebSocket();
    private ClientWebSocket(){}
    public static ClientWebSocket getClientWebSocket() {
        return clientWebSocket;
    }


    private WebSocket ws;
    private String ip;
    private String port;
    private int RECONNECT_CODE = 1;
    private boolean listening = false;
    private ClientFacade clientFacade = new ClientFacade();
    private OkHttpClient client;
    private String username;
    private String password;
    private boolean isDisconnected = false;
    ServerProxy serverProxy = new ServerProxy();

    public void sendJson(String myString){
        ws.send(myString);
    }
    public boolean initialize(String ip, String port, String username, String password){
        this.username = username;
        this.password = password;
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
            if (isDisconnected){
                serverProxy.login(username, password);
            }
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
                //At this point, the server has already accepted the register request as true because
                //it did not send a FailedResult. First parameter is for outputting a message (if one
                //exists, second is to match the proxy.
                clientFacade.registerUser(commandResult.getMessage(), null);
                break;
            case "login":
                //See registerUser note.
                clientFacade.loginUser(commandResult.getMessage(), null);
                break;
            case "pollgames":
                //Has extra information to get. Username unused here.
                PollGamesResult pollGamesResult = (PollGamesResult)commandResult;
                clientFacade.updateSingleUserGameList(pollGamesResult.getUsername(), pollGamesResult.getGameList());
                break;
            case "creategame":
                //
                clientFacade.createGame(commandResult.getUsername(), commandResult.getGameName());
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
        isDisconnected = true;
        try {
            Thread.sleep(5000);
            initialize(this.ip, this.port, this.username, this.password);
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