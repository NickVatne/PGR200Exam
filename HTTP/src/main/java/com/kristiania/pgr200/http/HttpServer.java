package com.kristiania.pgr200.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    private ServerSocket serverSocket;
    private int actualPort;
    private String baseUrl;
    private Map<String, String> parameters = new HashMap<>();


    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.actualPort = serverSocket.getLocalPort();
    }


    public static void main(String[] args) throws IOException {
        Server server = new Server(12080);
        server.start();
    }

    private void start() {
        System.out.println("Waiting for the client!");

        new Thread(() -> {
            try {
                runServer();
            } catch (SQLException e) {
                System.out.println("Database error");
                e.printStackTrace();
            }
        }).start();
    }

    public void runServer() throws SQLException {
        // Establish database connection
        DatabaseConnector dbConnector = new DatabaseConnector();
        dbConnector.connect();


        while (true) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();

                    InputStream input = clientSocket.getInputStream();
                    OutputStream output = clientSocket.getOutputStream();

                    String headerLine = HttpResponse.readLine(input);
                    String[] headerElements = headerLine.split(" ");
                    String requestMethodString = headerElements[0];
                    String path = headerElements[1];

                    RequestType requestMethod = RequestType.fromString(requestMethodString);
                    if (requestMethod == RequestType.UNKNOWN) {

                        System.out.println("No Request Method found");
                        return;
                    }

                    if (requestMethod == RequestType.GET) {
                        String body = doGet(path);

                        writeResponse(body, output);

                    }
                    if (requestMethod == RequestType.POST) {
                        doPost(path);
                        writeResponse("Saved", output);
                    }
                    if (requestMethod == RequestType.PUT) {
                        doPut(path);
                        writeResponse("updated", output);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeResponse(String body, OutputStream output) throws IOException {
        output.write(("HTTP/1.1 200 OK\r\n").getBytes());
        output.write(("Content-length:" + body.length() + "\r\n").getBytes());
        output.write(("\r\n").getBytes());
        output.write(body.getBytes());
        output.write(("\r\n").getBytes());
        output.flush();
    }

    private String doGet(String path) throws SQLException {
        if (path.equals("db/talks")) {
            return new DatabaseHandler().listAllTalks();
        }
        return null;
    }

    private void doPost(String path) throws SQLException {
        if (path.contains("?")) {
            baseUrl = path.split("\\?")[0];
            parameters = getParameters(path);
        }
        if (baseUrl.equals("/db/talks")) {
            new DatabaseHandler().insertTalk(parameters);
        }

    }

    private void doPut(String path) throws SQLException {
        if (path.contains("?")) {
            baseUrl = path.split("\\?")[0];
            parameters = getParameters(path);
        }
        if (baseUrl.equals("/db/talk/title")) {
            new DatabaseHandler().updateTalkTitle(parameters);
        }
        if (baseUrl.equals("/db/talk/desc")) {
            new DatabaseHandler().updateTalkDesc(parameters);
        }
        if (baseUrl.equals("/db/talk/topic")) {
            new DatabaseHandler().updateTalkTopic(parameters);
        }

    }


    private Map<String, String> getParameters(String path) {
        int questionPos = path.indexOf('?');
        if (questionPos == -1) {
            return new HashMap<>();
        }


        String query = urlDecode(path.substring(questionPos + 1));


        Map<String, String> parameters = new HashMap<>();
        assert query != null;
        for (String parameter : query.split("&")) {
            int equalsPos = parameter.indexOf('=');

            String paramName;

            paramName = parameter.substring(0, equalsPos);
            String paramValue = parameter.substring(equalsPos + 1);
            parameters.put(paramName, paramValue);

        }
        return parameters;
    }


    private String urlDecode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Something happened while decoding");
        }
        return null;
    }
}
