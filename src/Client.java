import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private String host;
    private int port;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void reply(String msg) {
        out.println(msg);
    }

    private void closeAll() {
        out.close();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (Socket server = new Socket(host, port);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            out = new PrintWriter(server.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            String msg = "";

            System.out.println("Connected ro server and got IO streams");

            while (true) {
                System.out.println(in.readLine());
                msg = console.readLine();
                out.println(msg);
                if (msg.equals("end")) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeAll();
            System.out.println("Disconnected");
        }
    }
}
