import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

public class ServerPartMK2  {

    final int PORT_LISTEN = 5656;
    String clientIp;
    byte[] dataIn;
    ArrayList<ChatUser> chatUsers = new ArrayList<>();
    byte[] dataToSend;
    boolean usergodkend;
    Socket s;


    public void Connection() throws IOException {
        usergodkend = false;
        System.out.println("=============SERVER==============");

        ServerSocket server = new ServerSocket(PORT_LISTEN);
        while (true) {

            s = server.accept();


            System.out.println("New client request received : " + s);
            System.out.println("Client connected");
            clientIp = s.getInetAddress().getHostAddress();
            System.out.println("IP: " + clientIp);
            System.out.println("PORT: " + s.getPort());

            Thread T2 = new Thread(() -> {
                try {
                    OutputStream output = s.getOutputStream();
                    InputStream input = s.getInputStream();
                    System.out.println("1");
                    dataIn = new byte[1024];
                    input.read(dataIn);
                    String msgIn = new String(dataIn);
                    msgIn = msgIn.trim();
                    System.out.println("2");
                    if (!msgIn.equals("JOIN")) {
                        String notJOIN = "access denied";
                        output.write(notJOIN.getBytes());
                    } else if (msgIn.equals("JOIN")) {
                        System.out.println("3");
                        Commands(msgIn);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                resivemessage();

            });
            T2.start();
        }
    }


    public  void resivemessage() {

         new Thread(() -> {
            InputStream input;
            while (true) {
                try {
                    input = s.getInputStream();
                    dataIn = new byte[1024];
                    input.read(dataIn);
                    System.out.println("6");
                    String msgIn = new String(dataIn);
                    msgIn = msgIn.trim();
                    System.out.println(msgIn);
                    if (msgIn.equals("DATA") || msgIn.equals("IMAV") || msgIn.equals("QUIT") || msgIn.equals("LIST")) {
                        System.out.println("7");
                        System.out.println(s.getLocalAddress().getHostAddress() + " " + msgIn + "<--");
                        Commands(msgIn);
                        System.out.println();

                    } else
                        Commands("J_ER");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


//        public void sendmessageToclient(Socket socket) {
//            Thread T1= new Thread(()->{
//              Scanner scan= new Scanner(System.in);
//                while(true) {
//
//                 try {
//
//                     String msgIn=scan.nextLine();
//                     output = s.getOutputStream();
//                     msgToSend = "SERVER: [sender:" + clientIp + " ]: " + msgIn;
//                     dataToSend = msgToSend.getBytes();
//                     output.write(dataToSend);
//
//
//
//                     System.out.println("idiot sendmessage to server");
//
//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }
//
//
//
//            }
//            });
//            T1.start();
//            }


    public void Commands(String command) throws IOException {
        OutputStream output = s.getOutputStream();
        Scanner sc = new Scanner(System.in);
        InputStream input;


        switch (command) {

            case "JOIN":
                ChatUser user = new ChatUser();
                String RequestUsername = "enter username";
                dataToSend = RequestUsername.getBytes();
                output.write(dataToSend);
                input = s.getInputStream();
                byte[] dataIn = new byte[1024];
                input.read(dataIn);
                String msgIn = new String(dataIn);
                msgIn = msgIn.trim();

//                for (int i = 0; i <chatUsers.size() ; i++) {
//
//                    if (msgIn.equals(chatUsers.get(i).getUsername()) && chatUsers.size()==0) {
//                        System.out.println("4");
//                        socket.close();
//                        break;
//                    }
//                    }
                System.out.println("4");
                user.setUsername(msgIn);
                user.setPORT(s.getPort()); //< lige her abi
                user.setIP(s.getInetAddress().getHostAddress());
                user.setSock(s);
                String msgTOSend = user.toString();
                String JOK = "J_OK";
                byte[] dataToSend = msgTOSend.getBytes();
                output.write(dataToSend);
                dataToSend = JOK.getBytes();
                output.write(dataToSend);
                chatUsers.add(user);
                System.out.println(user.toString());
                System.out.println("5");

                break;

            case "DATA":
                System.out.println("6");
                String error = "J_ER <<406>>: <<Usernotfound>>";
                String sendmessage = "send message";

                //resiving data from client, ask whom to send message to
                String msg = "To whom,whould you like to send message to, press ALL for everyone";
                dataToSend = msg.getBytes();
                output.write(dataToSend);
                //resiving the clients name, so server can direct the message to client
                input = s.getInputStream();

                byte[] re = new byte[1024];
                input.read(re);
                String messageTO = new String(re);
                msgIn = messageTO.trim();
                System.out.println(msgIn);

                if (msgIn.equals("SERVER")) {

                    output.write(sendmessage.getBytes());
                    input.read(re);
                    System.out.println("7");
                    String message = new String(re);
                    message = message.trim();
                    System.out.println(message + " " + s.getInetAddress().getHostName() + " " + s.getInetAddress());
                    break;


                } else if (msgIn.equals("ALL")) {
                    String msg1 = "Write message To Clients";
                    dataToSend = msg1.getBytes();
                    output.write(dataToSend);
                    System.out.println("8");
                    //resiving the clients name, so server can direct the message to client
                    input = s.getInputStream();
                    dataIn = new byte[1024];
                    input.read(dataIn);
                    System.out.println("9");
                    String msgIn2 = new String(dataIn);
                    msgIn = msgIn2.trim();
                    System.out.println("10");
                    System.out.println(msgIn);

                    for (int h = 0; h < chatUsers.size(); h++) {
                        chatUsers.get(0).getSock().getOutputStream().write(msgIn.getBytes());

                        }

                    }else
                        output.write(error.getBytes());
                        break;



        case "J_ER":
        String a = "J_ER <<404>>: <<Command not found>>";
        dataToSend = a.getBytes();
        output.write(dataToSend);

        break;

        case "QUIT":
        String exit = "bye";
        output.write(exit.getBytes());
        s.close();
        break;




            case "LIST":
                for (int i = 0; i <chatUsers.size() ; i++) {
                    System.out.println(chatUsers.get(i));

                }
                break;
    }


}



    }












