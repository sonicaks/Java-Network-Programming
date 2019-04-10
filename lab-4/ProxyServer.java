import java.io.*;
import java.util.*;
import java.net.*;

class ProxyServer {
    public ProxyServer(int port) {
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

                ClientHandler2 handler = new ClientHandler2(sock, name, dis, dos);

                Thread th = new Thread(handler);

                th.start();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ProxyServer(5554);
    }
}

class ClientHandler2 implements Runnable {
    Socket sock;
    String name;
    final DataInputStream dis;
    final DataOutputStream dos;

    public ClientHandler2(Socket sock, String name, DataInputStream dis, DataOutputStream dos) {
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

            if (recvd.equals("Logout")) {
                try {
                    this.sock.close();
                } catch (IOException ioEx) {
                    ioEx.printStackTrace();
                }
                ex = true;
            }

            if (ex) break;

            try {
                SendAndRecvFromServer sAndR = new SendAndRecvFromServer();
                this.dos.writeUTF(sAndR.getMsgFromServ(5555, name, recvd));
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }

        try {
            this.dis.close();
            this.dos.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}

class SendAndRecvFromServer {
    public String getMsgFromServ(int port, String name, String sendMsg) {
        InetAddress ip = null;

        try {
            ip = InetAddress.getByName("localhost");
        } catch (UnknownHostException unkHostEx) {
            unkHostEx.printStackTrace();
        }

        Socket sock = null;

        try {
            sock = new Socket(ip, port);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        DataInputStream dis = null;
        DataOutputStream dos = null;

        try {
            dis = new DataInputStream(sock.getInputStream());
            dos = new DataOutputStream(sock.getOutputStream());
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        try {
            dos.writeUTF(name);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        try {
            dos.writeUTF(sendMsg);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        
        String servMsg = "";

        try {
            System.out.println("Server: ");
            servMsg = dis.readUTF();
            System.out.print(servMsg);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        try {
            dos.writeUTF("Logout");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        return servMsg;
    }
}