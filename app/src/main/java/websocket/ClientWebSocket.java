package websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.concurrent.TimeUnit;

import clientcommunicator.CommandResultSerializer;
import clientfacade.ClientFacade;
import commandresults.CommandResult;
import interfaces.ICommand;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import serverproxy.ServerProxy;

public class ClientWebSocket extends WebSocketListener
{
    private static final ClientWebSocket clientWebSocket = new ClientWebSocket();
    private ClientWebSocket(){}
    public static ClientWebSocket getClientWebSocket()
    {
        return clientWebSocket;
    }

    private WebSocket ws;
    private String ip;
    private String port;
    private final int RECONNECT_CODE = 1;
    private boolean listening = false;
    private OkHttpClient client;
    private String username;
    private String password;
    private boolean isDisconnected = false;
    private ServerProxy serverProxy = new ServerProxy();

    public void sendJson(String myString)
    {
        ws.send(myString);
    }
    public boolean initialize(String ip, String port, String username, String password)
    {
        this.username = username;
        this.password = password;
        if (!listening)
        {
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
            if (isDisconnected)
                serverProxy.login(username, password, null);
        }
        return true;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response respone)
    {


    }

    @Override
    public void onMessage(WebSocket webSocket, String serverMessage)
    {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CommandResult.class, new CommandResultSerializer());
        Gson customGson = gsonBuilder.create();

        CommandResult commandResult = customGson.fromJson(serverMessage, CommandResult.class);
        ((ICommand)commandResult).execute();
    }



    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes)
    {
        //TODO: figure out how to send/receive an input/output stream via websocket
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason)
    {
        //output("Closing : " + code + " / " + reason);
        listening = false;
        isDisconnected = true;
        try {
            Thread.sleep(5000);
            initialize(this.ip, this.port, this.username, this.password);
        }catch (InterruptedException ex){
            //
        }
        //the server has disconnected you or you purposely disconnected yourself
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response)
    {
        if (t.getClass() == JsonSyntaxException.class){
            onClosing(webSocket, RECONNECT_CODE, t.getMessage());
        }
        else
            listening = false;
        //could not connect to server
    }

    public void quit()
    {
        client.dispatcher().executorService().shutdown();
    }

}