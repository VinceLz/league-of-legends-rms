package com.hawolt.rms.data.impl.payload;

import org.json.JSONObject;

/**
 * Created: 22/01/2023 12:16
 * Author: Twitter @hawolt
 **/

public class RiotMessageMessagePayload extends GenericRiotMessagePayload {
    private final String resource, service;
    private final JSONObject payload;
    private final long timestamp;

    public RiotMessageMessagePayload(JSONObject payload) {
        super(payload);
        this.payload = new JSONObject(payload.getString("payload"));
        this.resource = payload.getString("resource");
        this.timestamp = payload.getLong("timestamp");
        this.service = payload.getString("service");
    }

    public JSONObject getPayload() {
        return payload;
    }

    public String getResource() {
        return resource;
    }

    public String getService() {
        return service;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
