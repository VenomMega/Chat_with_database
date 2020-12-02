//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class Server {
//    public static void main( String[] args ) {
//        try{
//            System.out.println("Waiting for client");
//            ServerSocket serverSocket = new ServerSocket(1400);
//            while (true){
//                Socket socket = serverSocket.accept();
//                ClientThread clientThread = new ClientThread(socket);
//                clientThread.start();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//}