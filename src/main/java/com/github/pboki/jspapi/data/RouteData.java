package com.github.pboki.jspapi.data;

/**
 * @author PBoki
 */
public record RouteData(
        int id,
        int lineId,
        String direction,
        TranslationData directionTranslations,
        String name,
        TranslationData nameTranslations,
        long begin,
        long end,
        long length,
        int[] stopIds, // stop ids from StopData
        int[] stopOffsets // idk
) {}