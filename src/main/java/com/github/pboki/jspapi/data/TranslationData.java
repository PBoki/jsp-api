package com.github.pboki.jspapi.data;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PBoki
 */
public record TranslationData(Map<String, String> translations) { // lang, text

    public static TranslationData of(JsonObject obj) {
        Map<String, String> map = new HashMap<>();
        for (String lang : obj.keySet()) {
            map.put(lang, obj.get(lang).getAsString());
        }

        return new TranslationData(map);
    }

}
