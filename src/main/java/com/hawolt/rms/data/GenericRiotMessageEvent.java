package com.hawolt.rms.data;

import org.json.JSONObject;

/**
 * Created: 22/01/2023 11:51
 * Author: Twitter @hawolt
 **/

public class GenericRiotMessageEvent {
    private final String type, subject;
    private final JSONObject payload;
    private final long timestamp;

    public GenericRiotMessageEvent(JSONObject object) {
        this.payload = object.getJSONObject("payload");
        this.subject = object.getString("subject");
        this.timestamp = object.getLong("ts");
        this.type = object.getString("type");
    }

    public JSONObject getPayload() {
        return payload;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "RiotMessageEvent{" +
                "payload=" + payload +
                ", type='" + type + '\'' +
                ", subject='" + subject + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
