package com.github.pboki.jspapi.cache;

import com.github.pboki.jspapi.data.StopData;
import com.github.pboki.jspapi.requester.Requester;

/**
 * @author PBoki
 */
public class StopCache extends Cache<StopData> {

    @Override
    public void refresh(Requester requester) {
        requester.getStopsNoCache().thenAcceptAsync(stops -> stops.forEach(data -> set(data.id(), data)));
    }

}