import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class Main {
    private static int PORT = 80;
    private static String IP = "127.0.0.1";

    private static Runnable clientRun = () -> {
        String[] replies = {
                "Garry Dubua",
                "19",
                "end"
        };

        try (Socket clientSocket = new Socket(IP, PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream()))) {


            int index = 0;
            while (index < replies.length) {
                String msg = in.readLine();
                System.out.println(msg);
                out.println(replies[index++]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private static Runnable serverRun = () -> {
        String[] replies = {
                "Hello, what is your name?",
                "How old are you, %s?",
                "Glad to see you in our %s"
        };

        try (ServerSocket server = new ServerSocket(PORT);
             Socket client = server.accept();
             PrintWriter out = new PrintWriter(client.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(client.getInputStream()))) {

            int index = 0;
            String msg = "";
            while (!client.isClosed()) {
                out.println(String.format(replies[index++], msg));
                msg = in.readLine();
                System.out.println(msg);

                if (msg.equals("end")) {
                    client.close();
                    break;
                }

                if (index == 2) {
                    int age = Integer.parseInt(msg);
                    if (age >= 18) {
                        msg = "adult zone! Please feel yourself like in own home and have fun or good rest!";
                    } else {
                        msg = "kids zone! You can play games, eat cookies and have mush fun here!";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public static void main(String[] args) {
        Thread serverThread = new Thread(serverRun);
        Thread clientThread = new Thread(clientRun);

        serverThread.start();
        clientThread.run();
    }
}