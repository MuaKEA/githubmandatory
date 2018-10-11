import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientPartMK2 {
    Scanner sc = new Scanner(System.in);
   public  InputStream input;
   public OutputStream output;
   public  Socket socket;
   public byte[] dataToSend;

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
       System.out.println("");

   }

        public void sendmessageTOserver(){
         Thread T1= new Thread(()->{
while (true) {
    sc = new Scanner(System.in);
    System.out.println("What do you want to send? ");
    String msgToSend = sc.nextLine();

    try {
        if (msgToSend.equals("JOIN") || msgToSend.equals(" DATA") || msgToSend.equals("J_ER") || msgToSend.equals("QUIT")) {
            dataToSend = msgToSend.getBytes();
            output.write(dataToSend);
            commandcenter(msgToSend);

        } else
            dataToSend = msgToSend.getBytes();
        output.write(dataToSend);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

         });
           T1.start();
   }

        public void resivemessage() throws IOException {
            Thread T1= new Thread(()->{
       while(true) {

           byte[] dataIn = new byte[1024];
               try {
                   input.read(dataIn);
                   String msgIn = new String(dataIn);
                   msgIn = msgIn.trim();
                   System.out.println("IN -->" + msgIn + "<--");
               } catch (IOException e) {
                   e.printStackTrace();
               }


       }
            });
            T1.start();
   }



    private  void commandcenter(String value) throws IOException, InterruptedException {
        switch (value) {
            case "QUIT":
                System.out.println("sure? type:yes OR NO");
                String answer = sc.next();
                if (answer.equals("yes")) {
                    socket.close();

                }
                break;

            case "DATA":
                break;

            case "JOIN":
             ChatUser user=new ChatUser();
             System.out.println("enter username");
             user.setUsername(sc.next());
             dataToSend=user.getUsername().getBytes();
             output.write(dataToSend);
        }


    }

}
