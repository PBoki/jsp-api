package com.github.pboki.jspapi.requester;

/**
 * @author PBoki
 */
public enum Endpoint {

    BASE("http://info.skopska.mk:8080/rest-its/scheme/"), // base url for all endpoints
    ROUTES(BASE.url + "routes"),
    LINES(BASE.url + "lines"),
    STOPS(BASE.url + "stops?filter=true"),
    INFO(BASE.url + "stop-lines"); // info for bus times based on ids

    private final String url;

    Endpoint(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}