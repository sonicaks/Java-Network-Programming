import java.net.*;
import java.io.*;
import java.util.*;

class Server {
    static Queue<Socket> clients = new LinkedList<>();

    public Server(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);

            while (true) {
                Socket s = ss.accept();
                
                Server.clients.add(s);

                System.out.println("New client connected is: " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());

                String recvd;

                while (Server.clients.peek() != s) ;

                byte[] readMsg = new byte[2];
                dis.read(readMsg);

                recvd = new String(readMsg);
                System.out.println("Client " + s + " sent:");
                System.out.println(recvd);

                Server.clients.remove();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(5002);
    }
}