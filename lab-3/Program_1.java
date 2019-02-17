import java.net.*;

public class Program_1 {
    public static void main(String args[]) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("IP Address: " + address.getHostAddress());
            System.out.println("Host Name: " + address.getHostName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}