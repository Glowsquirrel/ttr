package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.eclipse.jetty.annotations.AnnotationParser;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commandresults.CommandResult;
import serverfacade.commands.Command;
import serverfacade.commands.ICommand;


public class CommandHandler extends AbstractHandler {

    private static Logger logger = Logger.getLogger("serverlog");

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        logger.fine("CommandHandler has been accessed");

        logger.warning(request.getRequestURI());

        InputStream inputStream = baseRequest.getInputStream();
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
            response.sendError(0, "FAILED COMMAND HANDLER");
            return;
        }

        logger.finest("Command is of type: " + command.getClass().toString());


        CommandResult results = ((ICommand)command).execute();

        baseRequest.setHandled(true);

        String jsonStringOut = gson.toJson(results);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(jsonStringOut.getBytes());

        logger.info("Server sent out: " + jsonStringOut);

    }

}
