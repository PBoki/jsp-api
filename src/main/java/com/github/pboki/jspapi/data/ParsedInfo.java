package com.github.pboki.jspapi.data;

/**
 * @author PBoki
 */
public record ParsedInfo(StopData stop, LineData line, RouteData route, int[] remainingTime) {}