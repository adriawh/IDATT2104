package Uke6.Oppg1;

import java.io.*;
import java.net.Socket;

public class ServerThreads implements Runnable {

    Socket socket;
    int threadNumber;

    public ServerThreads(Socket socket, int threadNumber){
        this.socket = socket;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {

        try {
            InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println("Hello this is the server speaking, you are connection number " + threadNumber);
            writer.println("To stop the connection, write 'stop'");

            writer.println("I am a basic calculator, i can subtract or add two numbersst");

            boolean running = true;

            while(running){
                writer.println("Give me an equation in the form a +/- b, use a space to differentiate them");

                String userInput = reader.readLine();

                String[] equationList = userInput.split("\\s+", 3);

                System.out.println(equationList.length);
                if(equationList.length == 3){

                    int a = Integer.parseInt(equationList[0]);
                    int b = Integer.parseInt(equationList[2]);


                    String operator = equationList[1];

                    int c;
                    switch (operator) {
                        case "+":
                            c = a + b;
                            writer.println( userInput + " = " + c);
                            break;
                        case "-":
                            c = a - b;
                            writer.println(userInput + " = " + c);
                        default:
                            writer.println("Could not calculate");

                    }
                }else{
                    writer.println("Wrong syntax, try again");
                }
            }
            reader.close();
            writer.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
