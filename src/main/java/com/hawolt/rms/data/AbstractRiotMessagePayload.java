package com.hawolt.rms.data;

import com.hawolt.rms.data.impl.payload.GenericRiotMessagePayload;
import org.json.JSONObject;

/**
 * Created: 22/01/2023 12:05
 * Author: Twitter @hawolt
 **/

public abstract class AbstractRiotMessagePayload<T extends GenericRiotMessagePayload> {
    protected final JSONObject payload;
    protected final T type;

    public AbstractRiotMessagePayload(JSONObject payload) {
        this.payload = payload;
        this.type = convert();
    }

    public JSONObject getRAW() {
        return payload;
    }

    protected abstract T convert();

    public T getPayload() {
        return type;
    }

    @Override
    public String toString() {
        return "AbstractRiotMessagePayload{" +
                "payload=" + payload +
                ", type=" + type +
                '}';
    }
}
