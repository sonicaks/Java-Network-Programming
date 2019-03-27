import java.io.*;
import java.util.*;
import java.net.*;

class Server {
    static Vector<ClientHandler> active = new Vector<ClientHandler>();

    public Server(int port) {
        ServerSocket servSock = null;
        
        try {
            servSock = new ServerSocket(port);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        Socket sock = null;

        while (true) {
            try {
                sock = servSock.accept();

                System.out.println("New Client request arrived: " + sock);

                DataInputStream dis = new DataInputStream(sock.getInputStream());
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

                System.out.println("Assign new handler for this client...");

                String name = dis.readUTF();

                ClientHandler handler = new ClientHandler(sock, name, dis, dos);

                Thread th = new Thread(handler);
                
                active.add(handler);

                th.start();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Server(5555);
    }
}

class ClientHandler implements Runnable {
    Socket sock;
    String name;
    final DataInputStream dis;
    final DataOutputStream dos;

    public ClientHandler(Socket sock, String name, DataInputStream dis, DataOutputStream dos) {
        this.sock = sock;
        this.name = name;
        this.dis = dis;
        this.dos = dos;
    }

    public void run() {
        boolean ex = false;
        while (true) {
            String recvd = "";
            try {
                recvd = this.dis.readUTF();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }

            System.out.println("Client: " + name + " "  + recvd);

            switch (recvd) {
                case "Information":
                    String toSend = "";
                    toSend += "Total Clients: " + Server.active.size() + "\n";
                    toSend += "List of Clients:\n";
                    
                    for (ClientHandler clntHandlr : Server.active) {
                        toSend += clntHandlr.name + " " + clntHandlr.sock.getInetAddress() + " " + clntHandlr.sock.getPort() + "\n";
                    }

                    try {
                        this.dos.writeUTF(toSend);
                    } catch (IOException ioEx) {
                        ioEx.printStackTrace();
                    }

                    break;

                case "Logout":
                    Server.active.remove(this.sock);

                    try {
                        this.sock.close();
                    } catch (IOException ioEx) {
                        ioEx.printStackTrace();
                    }
                    ex = true;

                    break;
            }

            if (ex) break;
        }

        try {
            this.dis.close();
            this.dos.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}