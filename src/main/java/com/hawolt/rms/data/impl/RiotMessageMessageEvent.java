package com.hawolt.rms.data.impl;


import com.hawolt.rms.data.AbstractRiotMessagePayload;
import com.hawolt.rms.data.impl.payload.RiotMessageMessagePayload;
import org.json.JSONObject;

/**
 * Created: 22/01/2023 12:10
 * Author: Twitter @hawolt
 **/

public class RiotMessageMessageEvent extends AbstractRiotMessagePayload<RiotMessageMessagePayload> {
    public RiotMessageMessageEvent(JSONObject payload) {
        super(payload);
    }

    @Override
    protected RiotMessageMessagePayload convert() {
        return new RiotMessageMessagePayload(payload);
    }
}
