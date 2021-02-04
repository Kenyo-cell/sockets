class Main {
    private static int PORT = 80;
    private static String HOST = "127.0.0.1";


    public static void main(String[] args) {
        Thread serverThread = new Thread(new Server(HOST, PORT));
        Thread clientThread = new Thread(new Client(HOST, PORT));

        serverThread.start();
        clientThread.run();
    }
}