import java.io.IOException;


public class TCPClientMK2 {

    public static void main(String[] args) throws IOException {
       ClientPartMK2 client= new ClientPartMK2();
       client.ClientStartop();
       client.sendmessageTOserver();
       client.resivemessage();
    }
}