import java.net.Socket;
import java.util.Scanner;

public class ChatUser {
public String Username;
public String IP;
public int PORT;
public Socket sock;
public Thread T1;


    public ChatUser() {

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username)  {
        Scanner scan = new Scanner(System.in);
        String user;
        if (!username.contains("# € % & / ( ) =]+") && (username.length() <= 12)) {
            this.Username=username;
        } else
            do {
                System.out.println("please pick another username");
                user = scan.next();
                username=user;
            } while (user.length() > 12 && user.contains("!#€%&/()=?"));
              this.Username = username;
    }

    public Thread getT1() {
        return T1;
    }

    public void setT1(Thread t1) {
        T1 = t1;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    @Override
    public String toString() {
        return "<<"+ Username +">>" + " " + "<<" + IP + ">>" + ":<<"+ PORT +">>";
    }
}
