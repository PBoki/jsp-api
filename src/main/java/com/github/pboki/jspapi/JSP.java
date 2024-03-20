package com.github.pboki.jspapi;

import com.github.pboki.jspapi.cache.StopCache;
import com.github.pboki.jspapi.data.StopData;
import com.github.pboki.jspapi.requester.Requester;
import com.github.pboki.jspapi.cache.LineCache;
import com.github.pboki.jspapi.cache.RouteCache;
import com.github.pboki.jspapi.data.InfoData;
import com.github.pboki.jspapi.data.LineData;
import com.github.pboki.jspapi.data.RouteData;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author PBoki
 */
public class JSP {

    private final Requester requester;
    private final StopCache stops = new StopCache();
    private final LineCache lines = new LineCache();
    private final RouteCache routes = new RouteCache();

    public JSP(Requester requester) {
        this.requester = requester;
    }

    public JSP() {
        this.requester = new Requester();
    }

    public JSP init() {
        return refreshCaches();
    }

    public JSP refreshCaches() {
        stops.refresh(requester);
        lines.refresh(requester);
        routes.refresh(requester);
        return this;
    }

    public Optional<StopData> getStopByNumber(int number) {
        return getStops().stream().filter(data -> data.number() == number).findAny();
    }

    /**
     * Get {@link LineData} by a specific bus number.
     * Note that letters must be macedonian!
     *
     * @param number The bus number
     * @return The line data if present.
     */
    public Optional<LineData> getLineByNumber(String number) {
        return getLines().stream().filter(data -> data.number().equalsIgnoreCase(number)).findAny();
    }

    public Optional<LineData> getLineByNumber(int number) {
        return getLineByNumber(String.valueOf(number));
    }

    public CompletableFuture<List<InfoData>> fetchInfo() {
        return requester.getInfoNoCache();
    }

    public Collection<StopData> getStops() {
        return Collections.unmodifiableCollection(stops.getData().values());
    }

    public Collection<LineData> getLines() {
        return Collections.unmodifiableCollection(lines.getData().values());
    }

    public Collection<RouteData> getRoutes() {
        return Collections.unmodifiableCollection(routes.getData().values());
    }

    public StopCache getStopsCache() {
        return stops;
    }

    public LineCache getLinesCache() {
        return lines;
    }

    public RouteCache getRoutesCache() {
        return routes;
    }

    public Requester getRequester() {
        return requester;
    }

}