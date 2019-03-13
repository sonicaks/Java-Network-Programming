import java.io.*;
import java.net.*;
import java.util.*;

class ListNetworkInterfaces {
    public static void main(String args[]) throws SocketException {
        Enumeration<NetworkInterface> netInfs = NetworkInterface.getNetworkInterfaces();

        for (NetworkInterface netInf : Collections.list(netInfs)) {
            System.out.println("Display Name:\t" + netInf.getDisplayName());
            System.out.println("Name:\t" + netInf.getName());
            Enumeration<InetAddress> inetAddrs = netInf.getInetAddresses();
            for (InetAddress inetAddr : Collections.list(inetAddrs)) {
                System.out.println("InetAddress:\t" + inetAddr);
            }
            System.out.println();
        }
    }
}