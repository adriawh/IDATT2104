package Uke6.Oppg2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);

        Socket socket = serverSocket.accept();

        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        writer.println("HTTP/1.0 200 OK");
        writer.println("Content-Type: text/html; charset=utf-8");
        writer.println("");
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<body>");
        writer.println("<h1>Hello World!</h1>");
        writer.println("<h3>The returned headers</h3>");
        writer.println("<ul>");

        String header = reader.readLine();

        while(!header.equals("")){
            writer.println("<li>" + header + "</li>");
            header = reader.readLine();
        }

        writer.println("</ul>");
        writer.println("</body>");
        writer.println("</html>");

        writer.close();
        reader.close();
        socket.close();
    }
}
