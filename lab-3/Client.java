import java.io.*;
import java.net.*;
import java.util.*;

class Client {
    Client() {
        try {
            Socket s = new Socket("localhost", 5050);

            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            System.out.print("Client:\t");
            Scanner sc = new Scanner(System.in);
            String msg = sc.next();
            dos.writeUTF(msg);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            String rep = dis.readUTF();
            System.out.println("Server:\t" + rep);

            s.close();
            sc.close();
        } catch(IOException ioe) {
            System.out.println("Error: Input / Output problem in socket");
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}