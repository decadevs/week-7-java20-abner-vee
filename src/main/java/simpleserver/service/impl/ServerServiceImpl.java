package simpleserver.service.impl;

import simpleserver.service.ServerService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerServiceImpl implements ServerService{

    @Override
    public void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new HttpRequestHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
