package com.github.pboki.jspapi.data;

/**
 * @author PBoki
 */
public record StopData(
        int id,
        int areaId,
        int number, // Stop number
        String name,
        TranslationData translationData,
        double lat,
        double lon,
        String note
) {}