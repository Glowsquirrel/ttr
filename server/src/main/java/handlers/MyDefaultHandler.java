package handlers;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyDefaultHandler extends ResourceHandler {
    private static Logger logger = Logger.getLogger("serverlog");

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        logger.info("Default Handler has been accessed.");

        try{

            String path = baseRequest.getRequestURI();

            if ("websocket".equals(baseRequest.getHeader("Upgrade"))){
                logger.info("doing websocket");
                WebSocketHandler wsHandler = new WebSocketHandler() {
                    @Override
                    public void configure(WebSocketServletFactory factory) {
                        factory.register(ServerWebSocket.class);
                    }
                };


                logger.info(baseRequest.getHeader("Sec-WebSocket-Key"));
                baseRequest.setHandled(true);
                response.setHeader("Connection", "Upgrade");
                response.setHeader("Upgrade", "websocket");
                response.setStatus(HttpServletResponse.SC_SWITCHING_PROTOCOLS);
                return;
            }
            if (path.equals("/command")){
                CommandHandler commandHandler = new CommandHandler();
                commandHandler.handle(target, baseRequest, request, response);
                return;
            }

            StringBuilder sb = new StringBuilder("server/web");
            if (path.equals("/")) //if nothing is specified, give it the home (index) page
                sb.append("/index.html");
            else
                sb.append(path);
            String filePath = new String(sb);

            File file = new File(filePath);

            if (file.isFile()){
                logger.info("Got the file at: " + filePath);
            }
            else{
                logger.info("Did not find this file: " + filePath);
                file = new File("data/web/HTML/404.html");
            }

            baseRequest.setHandled(true);

            OutputStream os = response.getOutputStream();
            FileInputStream fs = new FileInputStream(file);

            final byte[] buffer = new byte[1024];
            int count;
            while ((count = fs.read(buffer)) >= 0) {
                os.write(buffer, 0, count);
            }
            os.flush();
            fs.close();
            os.close();


        }catch(IOException ex){
            response.sendError(0, "FAILED DEFAULT HANDLER");
            ex.printStackTrace();
        }

    }
}
