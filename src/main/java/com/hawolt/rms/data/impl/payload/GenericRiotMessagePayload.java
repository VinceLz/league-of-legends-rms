package com.hawolt.rms.data.impl.payload;

import org.json.JSONObject;

/**
 * Created: 22/01/2023 12:15
 * Author: Twitter @hawolt
 **/

public class GenericRiotMessagePayload {

    protected final JSONObject raw;

    public GenericRiotMessagePayload(JSONObject raw) {
        this.raw = raw;
    }

    public JSONObject getRAW() {
        return raw;
    }

    @Override
    public String toString() {
        return "GenericRiotMessagePayload{" +
                "payload=" + raw +
                '}';
    }
}
