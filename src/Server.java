import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static int PORT = 2021;
    private static String HOST = "127.0.0.1";
    private static List<String> replies = List.of(
            "Hello, what is your name?",
            "Are you adult?"
    );

    private static List<String> answers = new ArrayList<>();
    private static boolean lastMessage = false;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT);
             Socket client = server.accept();
             PrintWriter out = new PrintWriter(client.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(
                     client.getInputStream()
             ))
        ) {
            System.out.println("Server accepted incoming connection");

            int index = 0;

            while (client.isConnected()) {
                if (lastMessage) {
                    out.println("Server has no replies for you. " +
                            "Please write \"end\"");
                }

                else if (index < replies.size()) {
                    out.println(replies.get(index++));
                } else {
                    out.println(String.format("Hello, %s! Welcome to our %s",
                            answers.get(0),
                            answers.get(1).startsWith("y") ?
                                    "adult zone!" : "kids room!"));
                            lastMessage = true;
                }
                 out.flush();

                String clientReply = in.readLine();

                if (clientReply.equalsIgnoreCase("end")) {
                    out.println("End work with client");
                    out.flush();
                    break;
                }
                answers.add(clientReply);
                System.out.println(clientReply);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Server work done");
        }
    }
}
