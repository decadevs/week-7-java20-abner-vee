package simpleserver.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestHandler implements Runnable {
    private final Socket clientSocket;

    public HttpRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream()
        ) {
            String request = in.readLine();
            System.out.println(request);
            if (request != null && request.startsWith("GET")) {
                String path = request.split(" ")[1];
                System.out.println(path);
                if (path.equals("/")) {
                    handleGetIndex(out);
                    return;
                }
                handleGetJson(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetIndex(OutputStream out) throws IOException {
        List<String> readAllLines = new ArrayList<>(Files
                .readAllLines(Paths
                        .get("src/main/resources/templates/index.html")));
        StringBuilder stringBuilder = new StringBuilder();
        String header = "HTTP/1.1 200 OK\r\n\r\n";
        stringBuilder.append(header);
        for (String line: readAllLines){
            stringBuilder.append(line);
        }

        String response = stringBuilder.toString();

        out.write(response.getBytes());
    }

    private void handleGetJson(OutputStream out) throws IOException {
        List<String> readAllLines = Files.readAllLines(Paths.get("src/main/resources/static/response.json"));
        String header = "HTTP/1.1 200 OK\r\n\r\n";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header);
        for (String s : readAllLines) {
            stringBuilder.append(s);
        }
        String response = stringBuilder.toString();
        out.write(response.getBytes());
    }

}