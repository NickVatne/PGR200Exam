package com.kristiania.pgr200.http;

public enum RequestType {
    POST,
    PUT,
    GET,
    UNKNOWN;

    public static RequestType fromString(String requestMethodString) {
        for (RequestType httpRequestMethod : RequestType.values()) {
            if (httpRequestMethod.name().equalsIgnoreCase(requestMethodString)) {
                return httpRequestMethod;
            }
        }
        return UNKNOWN;
    }

}
