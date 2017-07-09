package ServerCommunicator;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Handlers.*;

import com.sun.jndi.cosnaming.IiopUrl;
import com.sun.net.httpserver.*;

public class ServerCommunicator {


    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;
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
        logger = Logger.getLogger("log");
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

        //TODO add the same logger to all critical server classes
        //critical - not a facade or wrapper class. has meaningful/complex code.
    }

    private void run(String portNumber) {

        logger.finest("Initializing HTTP Server");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);

        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        logger.finest("Creating contexts");

        server.createContext("/", new MyDefaultHandler());
        server.createContext("/command", new CommandHandler());

        logger.finest("Starting server");

        server.start();

        InetAddress ip;

        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                String info = netint.getName();
                logger.info("Name: " + info);
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();

                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    logger.info("InetAddress: " + inetAddress);
                }
            }
        }catch (SocketException ex){
            logger.severe("Socket exception in Network interface");
        }


        logger.finest("Server started");
    }


    public static void main(String[] args) {
        String portNumber = "8080";
        new ServerCommunicator().run(portNumber);
    }
}


