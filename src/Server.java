import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements Runnable{
    private String host;
    private int port;
    private List<String> replies = List.of(
            "Hello, what is your name?",
            "Are you adult?",
            "Great, %s! Welcome to our %s"
    );

    private PrintWriter out;
    private BufferedReader in;


    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private String getReplyFor(String phrase) throws IOException{
        String msg = "";
        out.println(phrase);

        msg = in.readLine();
        while (true) {
            if (msg != null) break;
            msg = in.readLine();
        }
        return msg;
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
        try (ServerSocket server = new ServerSocket(port);
             Socket client = server.accept()) {
            System.out.println("Server accepted incoming connection");

            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            int index = 0;


            String name = getReplyFor(replies.get(index++));
            boolean isAdult = "yes".equals(getReplyFor(replies.get(index++)));
            out.println(String.format(replies.get(index++), name,
                    isAdult? "adult zone!" : "kid zone!"));

            while (true) {
                String msg = in.readLine();
                if (msg.equals("end")) break;
                out.println("Server has no replies for you, write \"end\" to disconnect");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeAll();
            System.out.println("Server work done");
        }
    }
}
