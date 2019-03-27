import java.io.*;
import java.net.*;
import java.util.*;

class Client {
    public Client(int port) {
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

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter unique name: ");
        String name = sc.nextLine();
        
        try {
            dos.writeUTF(name);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        while (true) {
            System.out.print("Client: ");
            String msg = sc.nextLine();

            try {
                dos.writeUTF(msg);
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }

            if (msg.equals("Logout")) {
                break;
            }
            
            try {
                System.out.println("Server: ");
                String servMsg = dis.readUTF();
                System.out.print(servMsg);
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client(5554);
    }
}