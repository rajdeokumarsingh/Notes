import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    public static void main(String args[]){
        InetAddress addr;
        try {
            addr = InetAddress.getByName("127.0.0.1");
            Socket monkeySocket;
            long now = System.currentTimeMillis();
            long timeout = 5000;
            while(System.currentTimeMillis() - now < timeout){
                try {
                    monkeySocket = new Socket(addr, 12345);
                    break;
                } catch (IOException e) {
                    continue;
                }
            }
        } catch (Exception e) {
        }
    }
}
