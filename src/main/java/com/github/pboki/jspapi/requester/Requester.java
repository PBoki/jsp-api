package com.github.pboki.jspapi.requester;

import com.github.pboki.jspapi.data.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executors;

/**
 * @author PBoki
 */
public class Requester {

    private final HttpClient client;
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";

    public Requester(HttpClient client) {
        this.client = client;
    }

    public Requester() {
        this.client = HttpClient.newBuilder()
                .executor(Executors.newSingleThreadExecutor(r -> new Thread(r, "jsp-api-worker")))
                .build();
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public CompletableFuture<List<StopData>> getStopsNoCache() {
        return request(Endpoint.STOPS).thenApplyAsync(json -> {
            JsonArray main = JsonParser.parseString(json).getAsJsonArray();
            List<StopData> stops = new ArrayList<>();
            for (JsonElement entry : main) {
                JsonObject obj = entry.getAsJsonObject();
                stops.add(new StopData(
                        obj.get("id").getAsInt(),
                        obj.get("areaId").getAsInt(),
                        obj.get("number").getAsInt(),
                        obj.get("name").getAsString(),
                        TranslationData.of(obj.get("translations").getAsJsonObject()),
                        obj.get("lat").getAsDouble(),
                        obj.get("lon").getAsDouble(),
                        obj.get("note").getAsString()
                ));
            }

            return stops;
        });
    }

    public CompletableFuture<List<LineData>> getLinesNoCache() {
        return request(Endpoint.LINES).thenApplyAsync(json -> {
            JsonArray main = JsonParser.parseString(json).getAsJsonArray();
            List<LineData> lines = new ArrayList<>();
            for (JsonElement entry : main) {
                JsonObject obj = entry.getAsJsonObject();
                lines.add(new LineData(
                        obj.get("id").getAsInt(),
                        obj.get("kind").getAsString(),
                        obj.get("number").getAsString(),
                        obj.get("name").getAsString(),
                        obj.get("nightly").getAsBoolean(),
                        toIntArray(obj.get("routeIds").getAsJsonArray()),
                        obj.get("type").getAsString(),
                        obj.has("carrier") ? obj.get("carrier").getAsString() : null
                ));
            }

            return lines;
        });
    }

    public CompletableFuture<List<RouteData>> getRoutesNoCache() {
        return request(Endpoint.ROUTES).thenApplyAsync(json -> {
            JsonArray main = JsonParser.parseString(json).getAsJsonArray();
            List<RouteData> routes = new ArrayList<>();
            for (JsonElement entry : main) {
                JsonObject obj = entry.getAsJsonObject();
                routes.add(new RouteData(
                        obj.get("id").getAsInt(),
                        obj.get("lineId").getAsInt(),
                        obj.get("direction").getAsString(),
                        TranslationData.of(obj.get("directionTranslations").getAsJsonObject()),
                        obj.get("name").getAsString(),
                        TranslationData.of(obj.get("nameTranslations").getAsJsonObject()),
                        obj.get("begin").getAsLong(),
                        obj.get("end").getAsLong(),
                        obj.has("length") ? obj.get("length").getAsLong() : -1,
                        toIntArray(obj.get("stopIds").getAsJsonArray()),
                        toIntArray(obj.get("stopOffsets").getAsJsonArray())
                ));
            }

            return routes;
        });
    }

    public CompletableFuture<List<InfoData>> getInfoNoCache() {
        return request(Endpoint.INFO).thenApplyAsync(json -> {
            JsonArray main = JsonParser.parseString(json).getAsJsonArray();
            List<InfoData> infos = new ArrayList<>();
            for (JsonElement entry : main) {
                JsonObject obj = entry.getAsJsonObject();
                infos.add(new InfoData(
                        obj.get("stopId").getAsInt(),
                        obj.get("lineId").getAsInt(),
                        obj.get("stopId").getAsInt(),
                        toIntArray(obj.get("remainingTime").getAsJsonArray())
                ));
            }

            return infos;
        });
    }

    private CompletableFuture<String> request(Endpoint endpoint) {
        try {
            return client.sendAsync(HttpRequest.newBuilder()
                    .uri(new URL(endpoint.getUrl()).toURI())
                    .GET()
                    .header("User-Agent", userAgent)
                    .header("Eurogps.Eu.Sid", "28976dbba3e5db7aa3e998cb3da07d88ea15f722043824b3") // Required
                    .build(), HttpResponse.BodyHandlers.ofString()).thenApplyAsync(HttpResponse::body);
        } catch (URISyntaxException | MalformedURLException ex) {
            throw new CompletionException(ex);
        }
    }

    private int[] toIntArray(JsonArray array) {
        int[] ids = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            ids[i] = array.get(i).getAsInt();
        }
        return ids;
    }

}
