import java.net.*;

class Program_2 {
    public static void printDot(int i) {
        if (i != 3) System.out.print(".");
        else System.out.println();
    }

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("www.codeforces.com");
            System.out.println("IP address:\t\t" + address.getHostAddress());
            String[] str = address.getHostAddress().split("\\.");

            System.out.print("IP address in binary:\t");
            for (int i = 0; i < 4; i++) {
                int num = Integer.parseInt(str[i]);
                System.out.print(Integer.toBinaryString(num));
                if (i != 3) System.out.print(".");
                else System.out.println();
            }
            
            System.out.print("IP address in octal:\t");
            for (int i = 0; i < 4; i++) {
                int num = Integer.parseInt(str[i]);
                System.out.print(Integer.toOctalString(num));
                printDot(i);
            }

            System.out.print("IP address in hex:\t");
            for (int i = 0; i < 4; i++) {
                int num = Integer.parseInt(str[i]);
                System.out.print(Integer.toHexString(num));
                printDot(i);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}