import java.io.*;
import java.net.*;
import java.util.*;

class Server {
    Server() {
        try {
            ServerSocket ss = new ServerSocket(5050);
            Socket s = ss.accept();

            System.out.println("Default socket timeout:\t" + s.getSoTimeout());
            s.setSoTimeout(10 * 1000);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            String rep = dis.readUTF();
            System.out.println("Client:\t" + rep);

            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            System.out.print("Server:\t");
            Scanner sc = new Scanner(System.in);
            String msg = sc.next();
            dos.writeUTF(msg);

            ss.close();
            s.close();
            sc.close();
        } catch(SocketTimeoutException ste) {
            System.out.println("Error: Socket timed out");
        } catch(IOException ioe) {
            System.out.println("Error: Input / Output problem in socket");
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}