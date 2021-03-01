import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static int PORT = 2021;
    private static String HOST = "127.0.0.1";


    public static void main(String[] args) {
        try (Socket server = new Socket(HOST, PORT);
             BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(server.getInputStream()));
             PrintWriter out = new PrintWriter(server.getOutputStream())
        ) {

            String msg = "";

            System.out.println("Connected ro server and got IO streams");

            while (true) {
                System.out.println(in.readLine());
                msg = console.readLine();
                out.println(msg);
                out.flush();
                if (msg.equalsIgnoreCase("end")) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Disconnected");
        }
    }
}
