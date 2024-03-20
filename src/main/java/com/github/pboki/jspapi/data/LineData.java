package com.github.pboki.jspapi.data;

/**
 * @author PBoki
 */
public record LineData(int id, String kind, String number, String name, boolean nightly, int[] routeIds, String type, String carrier) {}