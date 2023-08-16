package com.hawolt.rms.data.impl;


import com.hawolt.rms.data.AbstractRiotMessagePayload;
import com.hawolt.rms.data.impl.payload.GenericRiotMessagePayload;
import org.json.JSONObject;

/**
 * Created: 22/01/2023 12:10
 * Author: Twitter @hawolt
 **/

public class UnknownMessageEvent extends AbstractRiotMessagePayload<GenericRiotMessagePayload> {
    public UnknownMessageEvent(JSONObject payload) {
        super(payload);
    }

    @Override
    protected GenericRiotMessagePayload convert() {
        return new GenericRiotMessagePayload(payload);
    }
}
