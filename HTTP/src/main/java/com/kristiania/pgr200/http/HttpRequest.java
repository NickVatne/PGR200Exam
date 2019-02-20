package com.kristiania.pgr200.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    private String path;
    private String method;
    private Socket socket;
    private OutputStream outputStream;
    private Map<String, String> headers = new HashMap<>();
    private String body;

    public HttpRequest(String method, String hostname, int port, String path) throws IOException {
        setRequestHeader("Host", hostname);
        this.path = path;
        this.method = method;

        socket = new Socket(hostname, port);
        outputStream = socket.getOutputStream();
    }

    public HttpRequest(String hostname, int port, String path, String method) throws IOException {
        setRequestHeader("Host", hostname);
        this.path = path;
        this.method = method;

        socket = new Socket(hostname, port);
        outputStream = socket.getOutputStream();
    }

    public HttpResponse execute() throws IOException {
        writeLine(method + " " + path + " HTTP/1.1");
        writeLine("Connection: close");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            writeLine(entry.getKey() + ": " + entry.getValue());
        }
        writeLine("");
        if (body != null) writeLine(body);
        outputStream.flush();

        return new HttpResponse(socket.getInputStream());
    }

    private void writeLine(String line) throws IOException {
        outputStream.write((line + "\r\n").getBytes());
    }

    public void setRequestHeader(String rule, String type) {
        this.headers.put(rule, type);
    }

    public static String createPath(String baseURL, Map<String, String> talkParameters) {
        if (talkParameters.isEmpty()) {
            return baseURL;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(baseURL);
        sb.append("?");
        List<String> keyValueStrings = new ArrayList<>();
        for (Map.Entry<String, String> entry : talkParameters.entrySet()) {
            keyValueStrings.add(urlEncode(entry.getKey()) + "=" + urlEncode(entry.getValue()));
        }
        sb.append(String.join("&", keyValueStrings));
        return sb.toString();
    }

    private static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported ", e);
        }
    }

}

