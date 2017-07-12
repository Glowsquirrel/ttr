package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.util.logging.Logger;

import commandresults.CommandResult;
import commands.Command;
import commands.ICommand;


public class CommandHandler implements HttpHandler{

    private static Logger logger = Logger.getLogger("serverlog");


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.fine("CommandHandler has been accessed");

        InputStream inputStream = exchange.getRequestBody();
        StringBuilder sb =  new StringBuilder();
        try (Scanner scanner = new Scanner(inputStream)){
            while (scanner.hasNextLine()){
                sb.append(scanner.nextLine());
            }
        }
        String jsonStringIn = sb.toString();
        logger.info("Server receieved : " + jsonStringIn);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Command.class, new CommandSerializer());
        Gson gson = gsonBuilder.create();

        Command command = gson.fromJson(jsonStringIn, Command.class);

        if (command == null){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.close();
            return;
        }

        logger.finest("Command is of type: " + command.getClass().toString());


        CommandResult results = ((ICommand)command).execute();


        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        String jsonStringOut = gson.toJson(results);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(jsonStringOut.getBytes());

        logger.info("Server sent out: " + jsonStringOut);
        exchange.close();


    }

}
