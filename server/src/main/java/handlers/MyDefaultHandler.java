package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.logging.Logger;

/**
 * Created by glowsquirrel on 7/8/17.
 */

public class MyDefaultHandler implements HttpHandler{
    private static Logger logger = Logger.getLogger("log");

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        logger.warning("Default Handler has been accessed on this server.");

        try{


            URI url = exchange.getRequestURI();
            String path = url.getRawPath();

            StringBuilder sb = new StringBuilder("data/web");
            if (path.equals("/")) //if nothing is specified, give it the home (index) page
                sb.append("/index.html");
            else
                sb.append(path);
            String filePath = new String(sb);

            File file = new File(filePath);

            if (file.isFile()){
                System.out.println("This is a file");
            }
            else{
                System.out.println("This is NOT a file");
                file = new File("data/web/HTML/404.html");
            }

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            OutputStream os = exchange.getResponseBody();
            FileInputStream fs = new FileInputStream(file);

            final byte[] buffer = new byte[1024];
            int count;
            while ((count = fs.read(buffer)) >= 0) {
                os.write(buffer, 0, count);
            }
            os.flush();
            fs.close();
            os.close();

            exchange.close();
            System.out.println();

        }catch(IOException ex){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            ex.printStackTrace();
        }

    }
}
