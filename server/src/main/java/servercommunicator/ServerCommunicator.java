package servercommunicator;

import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import handlers.*;

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
        logger = Logger.getLogger("serverlog");
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
        //HandlerCollection handlerCollection = new HandlerCollection();
        //handlerCollection.setHandlers(new org.eclipse.jetty.server.Handler[] {new CommandHandler(), new MyDefaultHandler()});
        //server.setHandler(handlerCollection);

        //ServletContextHandler context0 = new ServletContextHandler(ServletContextHandler.SESSIONS);


        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(ServerWebSocket.class);
            }
        };

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        //ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        //context.setContextPath("/");
        //server.setHandler(context);

        ResourceHandler rh = new ResourceHandler();
        rh.setDirectoriesListed(true);
        rh.setResourceBase("./server/web");

        wsHandler.setHandler(rh);

        //ServletHolder holder = new ServletHolder("ws-events", EventServlet.class);
        //context.addServlet(holder, "/ws");


        server.setHandler(wsHandler);
        server.start();
        server.join();

        //new ServerCommunicator2().run(portNumber);
    }

}


