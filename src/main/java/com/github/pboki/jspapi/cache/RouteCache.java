package com.github.pboki.jspapi.cache;

import com.github.pboki.jspapi.requester.Requester;
import com.github.pboki.jspapi.data.RouteData;

/**
 * @author PBoki
 */
public class RouteCache extends Cache<RouteData> {

    @Override
    public void refresh(Requester requester) {
        requester.getRoutesNoCache().thenAcceptAsync(route -> route.forEach(data -> set(data.id(), data)));
    }

}