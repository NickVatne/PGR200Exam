package com.kristiania.pgr200.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private int statusCode;
    private Map<String, String> headers = new HashMap<>();
    private String body;
    private InputStream input;

    public HttpResponse(InputStream input) throws IOException {
        this.input = input;
        String statusLine = readLine(input);
        String[] parts = statusLine.split(" ");
        this.statusCode = Integer.parseInt(parts[1]);
        readHeaderLines();
        this.body = readLine(input, Integer.parseInt(getHeader("Content-length")));
    }

    private void readHeaderLines() throws IOException {
        String headerLine;
        while ((headerLine = readLine(input)) != null) {
            if (headerLine.isEmpty()) {
                break;
            }
            int colonPos = headerLine.indexOf(':');
            String headerName = headerLine.substring(0, colonPos).trim().toLowerCase();
            String headerValue = headerLine.substring(colonPos + 1).trim();
            headers.put(headerName, headerValue);
        }
    }

    public static String readLine(InputStream input) throws IOException {
        StringBuilder line = new StringBuilder();
        int character;
        while ((character = input.read()) != -1) {
            if (character == '\r') {
                character = input.read();
                assert character == '\n';
                break;
            }
            line.append((char) character);
        }
        return line.toString();
    }

    private String readLine(InputStream input, int contentLength) throws IOException {
        StringBuilder line = new StringBuilder();
        int character;
        while ((character = input.read()) != -1) {
            line.append((char) character);
            if (line.length() >= contentLength) {
                break;
            }
        }
        return line.toString();
    }

    public String getBody() {
        try {
            return URLDecoder.decode(body, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Error while decoding");
        }
        return null;
    }

    public String getHeader(String headerName) {
        return headers.get(headerName.toLowerCase());
    }
    public int getStatusCode (){
        return statusCode;
    }

}




