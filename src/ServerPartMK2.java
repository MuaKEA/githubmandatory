import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerPartMK2 {

    final int PORT_LISTEN = 5656;
    String clientIp;
    String msgIn;
    InputStream input;
    byte[] dataIn;
    ArrayList<ChatUser> chatUsers=new ArrayList<>();
    String msgToSend;
    OutputStream output;
    byte[] dataToSend;
    boolean usergodkend;
    Socket s;

    public void Connection() throws IOException {
        usergodkend=false;
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
                        output=s.getOutputStream();
                        input = s.getInputStream();
                        System.out.println("1");
                        dataIn = new byte[1024];
                        input.read(dataIn);
                        msgIn = new String(dataIn);
                        msgIn = msgIn.trim();
                        System.out.println("2");
                        if (!msgIn.equals("JOIN")) {
                            String notJOIN = "press JOIN To Join the server";
                            output.write(notJOIN.getBytes());

                        } else
                            if(msgIn.equals("JOIN"))
                            System.out.println("3");
                        Commands(msgIn,s);
                        sendmessageToclient(s);
                        resivemessage(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                });
            T2.start();
        }
            }



    public void resivemessage(Socket socket) throws IOException {

        Thread T2 = new Thread(() -> {

        while (true) {
            try {

                input = socket.getInputStream();
                dataIn = new byte[1024];
                input.read(dataIn);
                msgIn = new String(dataIn);
                msgIn = msgIn.trim();
                if (msgIn.equals("JOIN") || msgIn.equals(" DATA") || msgIn.equals("IMAV") || msgIn.equals("QUIT")) {
                    System.out.println("IN -->" + msgIn + "<--");
                    Commands(msgIn,socket);

                } else
                    Commands("J_ER",socket);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        });
        T2.start();
    }





        public void sendmessageToclient(Socket socket) throws IOException {
            Thread T1= new Thread(()->{
        while(true) {

                 try {
                     output = socket.getOutputStream();
                     msgToSend = "SERVER: [sender:" + clientIp + " ]: " + msgIn;
                     dataToSend = msgToSend.getBytes();
                     output.write(dataToSend);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }



            }
            });
            T1.start();
            }


    public void Commands(String command,Socket socket) throws IOException {

        switch (command) {

            case "JOIN":
                ChatUser user = new ChatUser();
                socket.getInputStream();
                input.read(dataIn);
                user.setUsername(msgIn=new String(dataIn).trim());
                user.setSock(s);
                chatUsers.add(user);
                dataToSend=user.getUsername().getBytes();
                output.write(dataToSend);

                break;

            case "DATA":

                break;

            case "J_ER":
                break;

            case "QUIT":
                String Errormessage="Error unknown command";
                output.write(Errormessage.getBytes());
                break;


        }


    }




}






