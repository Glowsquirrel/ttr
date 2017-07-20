package servercommunicator;

import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import handlers.*;
import utils.Utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class ServerCommunicator{

    private static Logger logger;

    static{
        try{
            initLog();
        }catch (IOException e){
            System.out.println("Could not initialize log: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initLog() throws IOException{
        Level logLevel = Level.ALL;
        logger = Logger.getLogger(Utils.SERVER_LOG);
        logger.setLevel(logLevel);
        logger.setUseParentHandlers(false);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logLevel);
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord logRecord) {
                return logRecord.getLevel() + " " + logRecord.getMessage() + "\n";
            }
        });
        logger.addHandler(consoleHandler);
    }

    public static void main(String[] args) throws Exception{
        String portNumber = "8080";
        int port = 8080;

        WebSocketHandler wsHandler = new WebSocketHandler()
        {
            @Override
            public void configure(WebSocketServletFactory factory)
            {
                factory.register(ServerWebSocket.class);
            }
        };

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ResourceHandler rh = new ResourceHandler();
        rh.setDirectoriesListed(true);
        rh.setResourceBase("./server/web");

        wsHandler.setHandler(rh);

        server.setHandler(wsHandler);
        server.start();
        server.join();

    }

}


