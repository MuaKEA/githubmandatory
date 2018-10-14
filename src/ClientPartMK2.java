import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Timer;

public class ClientPartMK2 {
   private Scanner sc = new Scanner(System.in);
   private InputStream input;
   private OutputStream output;
   private Socket socket;
   private byte[] dataToSend;

   public void ClientStartop() throws IOException {
       System.out.println("=============CLIENT==============");
       sc = new Scanner(System.in);
       System.out.print("What is the IP for the server (type 0 for localhost): ");
       String IP = sc.next();
       if (IP.equals("0")) {
           IP = "127.0.0.1";
       }else
           if(IP.equals("1")){
               System.out.println("enter the ip of the Server");
              IP=sc.next();
       }
       System.out.print("What is the PORT for the server: ");
       int PORT_SERVER = sc.nextInt();
       socket = new Socket(IP, PORT_SERVER);
       input = socket.getInputStream();
       output = socket.getOutputStream();

   }

        public void sendmessageTOserver(){
         Thread T1= new Thread(()->{
while (true) {
    sc = new Scanner(System.in);
    System.out.println("Type message");
    String msgToSend = sc.nextLine();

    try {
        if (msgToSend.equals(msgToSend.equals("QUIT"))) {
               String Answer=sc.next();
               if(Answer.equals("yes")) {
                dataToSend=msgToSend.getBytes();
                output.write(dataToSend);
                System.exit(0);
               }
               }

            dataToSend = msgToSend.getBytes();
        output.write(dataToSend);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

         });
           T1.start();
   }

        public void resivemessage()  {
            Thread T1= new Thread(()->{
       while(true) {

               try {
                   byte[] dataIn = new byte[1024];
                   input.read(dataIn);
                   String msgIn = new String(dataIn);
                   msgIn = msgIn.trim();
                   System.out.println("msgFromServer-->" + msgIn + "<--" );


               } catch (IOException e) {
                   e.printStackTrace();
               }


       }
            });
            T1.start();
   }



    private  void commandcenter(String value) throws IOException {
        switch (value) {

            case "JOIN":
                ChatUser user = new ChatUser();
                System.out.println("enter username");
                user.setUsername(sc.next());
                dataToSend = user.getUsername().getBytes();
                output.write(dataToSend);
                break;

        }

    }



}
