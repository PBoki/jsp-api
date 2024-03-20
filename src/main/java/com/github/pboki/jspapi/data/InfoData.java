package com.github.pboki.jspapi.data;

import com.github.pboki.jspapi.JSP;

/**
 * Information data.
 * <bold>Note: These are IDS not the actual numbers of stops,lines,routes!</bold>
 *
 * @param stopId
 * @param lineId
 * @param routeId
 * @param remainingTime The time in seconds.
 */
public record InfoData(int stopId, int lineId, int routeId, int[] remainingTime) {

    public ParsedInfo parse(JSP jsp) {
        return new ParsedInfo(
                jsp.getStopsCache().get(stopId).orElseThrow(() -> new NullPointerException("Invalid or missing STOP id!")),
                jsp.getLinesCache().get(lineId).orElseThrow(() -> new NullPointerException("Invalid or missing LINE id!")),
                jsp.getRoutesCache().get(routeId).orElseThrow(() -> new NullPointerException("Invalid or missing ROUTE id!")),
                remainingTime
        );
    }

}