import simpleserver.service.impl.ServerServiceImpl;

public class Main {
    public static void main(String[] args) {
        int port = 8087;
        ServerServiceImpl server = new ServerServiceImpl();
        server.startServer(port);
    }
}
