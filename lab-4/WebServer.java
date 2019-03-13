import java.io.*;

class WebServer {
    public static void main(String args[]) {
        /* Create HttpServer */
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Server started at " + port);
        server.createContext("/", new RootHandler());
        server.createContext("/echoGet", new EchoGetHandler());
        server.createContext("/echoPost", new EchoPostHandler());
        server.setExecutor(null);
        server.start();
    }
}

class RootHandler implements HttpHandler {
    public void handle(HttpExchange httpExch) throws IOException {
        String resp = "<h4>Server started successfully at port: </h4>" + "<h4>" + port + "</h4>";
        httpExch.sendResponseHeader(200, resp.length());
        OutputStream os = httpExch.getResponseBody();
        os.write(resp.getBytes());
        os.close();
    }
}

/* https://www.codeproject.com/tips/1040097/create-a-simple-web-server-in-java-http-server */