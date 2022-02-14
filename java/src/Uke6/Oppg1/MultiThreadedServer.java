package Uke6.Oppg1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {

    public static void main(String[] args) throws IOException {

        boolean running = true;
        int i = 0;

        ServerSocket serverSocket = new ServerSocket(1250);
        while(running){
            Socket socket;

            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                if(!running) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            i++;

            new Thread(new ServerThreads(socket, i)).start();
            System.out.println("Ny klient tilkoblet");
        }

    }
}
