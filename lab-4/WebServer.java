import java.io.*;
import java.util.*;
import java.net.*;
import com.sun.net.httpserver.*;

class WebServer {
    public static void main(String args[]) throws IOException {
        /* Create HttpServer */
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Server started at " + port);
        server.createContext("/", new RootHandler());
        server.createContext("/Get", new GetHandler());
        server.setExecutor(null);
        server.start();
    }
}

class RootHandler implements HttpHandler {
    public void handle(HttpExchange httpExch) throws IOException {
        int port = 8000;
        String resp = "Server started successfully at port: " + port;
        httpExch.sendResponseHeaders(200, resp.length());
        OutputStream os = httpExch.getResponseBody();
        os.write(resp.getBytes());
        os.close();
    }
}

class GetHandler implements HttpHandler {
    public void handle(HttpExchange httpExch) throws IOException {
        /* Parse request */
        Map<String, Object> params = new HashMap<String, Object>();
        URI reqUri = httpExch.getRequestURI();
        String qry = reqUri.getRawQuery();
        ParseQry.parseQry(qry, params);

        /* Send response */
        String resp = "";
        for (String key : params.keySet()) {
            resp += key + " = " + params.get(key) + "\n";
        }
        httpExch.sendResponseHeaders(200, resp.length());
        OutputStream os = httpExch.getResponseBody();
        os.write(resp.toString().getBytes());
        os.close();
    }
}

class ParseQry {
    public static void parseQry(String qry, Map<String, Object> params) throws UnsupportedEncodingException {
        if (qry != null) {
            String pairs[] = qry.split("[&]");
            for (String pair : pairs) {
                String paramsStr[] = pair.split("[=]");
                String key = null;
                String val = null;
                if (paramsStr.length > 0) {
                    key = URLDecoder.decode(paramsStr[0], System.getProperty("file.encoding"));
                }

                if (paramsStr.length > 1) {
                    val = URLDecoder.decode(paramsStr[1], System.getProperty("file.encoding"));
                }

                if (params.containsKey(key)) {
                    Object obj = params.get(key);
                    if (obj instanceof List<?>) {
                        List<String> vals = (List<String>) obj;
                        vals.add(val);
                    } else if (obj instanceof String) {
                        List<String> vals = new ArrayList<String>();
                        vals.add((String) obj);
                        vals.add(val);
                        params.put(key, vals);
                    }
                } else {
                    params.put(key, val);
                }
            }
        }
    }
}