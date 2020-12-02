import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
public class ClientThread extends Thread {

    private Socket socket;
    public ClientThread( Socket socket) {
        this.socket = socket;
    }
    public void run(){
        try{
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            User user = null;
            while ((user = (User) inputStream.readObject())!=null){
                System.out.println(user.message());
                outputStream.writeObject(null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}