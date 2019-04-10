import java.io.*;
import java.nio.file.*;
import java.net.*;
import java.util.*;

class Client {
    public static void main(String[] args) throws IOException {
        try {
            InetAddress ip = InetAddress.getByName(args[0]);

            Socket s = null;
            
            while (true) {
                s = new Socket(ip, Integer.valueOf(args[1]));
                if (s == null);
                else break;
            }

            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            String toSend = new String(Files.readAllBytes(Paths.get(args[2])));

            dos.write(toSend.getBytes("UTF-8"));

            dos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}