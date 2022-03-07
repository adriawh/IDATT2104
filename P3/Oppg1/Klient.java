package Uke6.Oppg1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Klient {


    public static void main(String[] args) throws IOException {

        final int PORTNR = 1250;

        Scanner input = new Scanner(System.in);
        System.out.println("Running on localhost:1250");
        String tjenermaskin = "localhost";

        Socket socket = new Socket(tjenermaskin, PORTNR);
        System.out.println("**Connection established**");

        InputStreamReader leseforbindelse = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(leseforbindelse);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);


        /* Reading intro from server*/
        System.out.println("\n"+ reader.readLine());
        System.out.println(reader.readLine());

        System.out.println("\n"+ reader.readLine());

        boolean running = true;

        while(running){
            System.out.println("\n"+  reader.readLine() + "\n");
            String userInput  = input.nextLine();
            if(userInput.equals("stop")){
                running = false;
            }
            writer.println(userInput);

            System.out.println(reader.readLine());
        }


        reader.close();
        writer.close();
        socket.close();
    }

}
