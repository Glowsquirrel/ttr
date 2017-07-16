package servercommunicator;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import handlers.*;
import handlers.CommandHandler;
import serverfacade.ServerFacade;
import serverfacade.commands.Command;

import com.sun.corba.se.impl.activation.*;
import com.sun.jndi.cosnaming.IiopUrl;
import com.sun.net.httpserver.*;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

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
                factory.register(MyWebSocket.class);
                ServerFacade serverFacade = new ServerFacade();

            }
        };
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder holder = new ServletHolder("ws-events", EventServlet.class);
        context.addServlet(holder, "/ws");

        server.setHandler(context);
        server.start();
        server.join();

        //new ServerCommunicator2().run(portNumber);
    }

}


