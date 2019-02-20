package com.kristiania.pgr200.http;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;



public class HTTPClientTest {

    @Test
    public void shouldReadHeaderLines() throws IOException {
        byte[] headerLines = "Content-length: 10".getBytes();

        InputStream inputStream = new ByteArrayInputStream(headerLines);

        String expected = HttpResponse.readLine(inputStream);
        assertEquals("Content-length: 10", expected);
    }
    @Test
    public void shouldReadOtherStatusCodes() throws IOException {
        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo", "GET");
        HttpResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
    @Test
    public void shouldReadOtherStatusCode() throws IOException {
        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo?", "GET");
        HttpResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
    @Test
    public void shouldHandlePostRequest() throws IOException {
        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo", "POST");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(411);
    }
    @Test
    public void shouldReadResponseHeaders() throws IOException {
        HttpRequest request = new HttpRequest("urlecho.appspot.com",
                80, "/echo?status=307&Location=http%3A%2F%2Fwww.google.com", "GET");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.google.com");
    }
    @Test
    public void shouldReadResponseBody() throws IOException {
        HttpRequest request = new HttpRequest("urlecho.appspot.com",
                80, "/echo?body=Hello+world!", "GET");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello world!");
    }
    @Test
    public void shouldCreatePath() {
        String baseUrl = "db/talks";

        Map<String, String> taskParameters = new HashMap<>();
        taskParameters.put("id", "1");
        taskParameters.put("title", "javazone project");

        String expected = "db/talks?id=1&title=javazone+project";

        String actual = HttpRequest.createPath(baseUrl, taskParameters);

        assertEquals(expected, actual);
    }

}
