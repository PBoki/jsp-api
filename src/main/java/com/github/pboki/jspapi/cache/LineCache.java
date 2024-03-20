package com.github.pboki.jspapi.cache;

import com.github.pboki.jspapi.data.LineData;
import com.github.pboki.jspapi.requester.Requester;

/**
 * @author PBoki
 */
public class LineCache extends Cache<LineData> {

    @Override
    public void refresh(Requester requester) {
        requester.getLinesNoCache().thenAcceptAsync(lines -> lines.forEach(data -> set(data.id(), data)));
    }

}